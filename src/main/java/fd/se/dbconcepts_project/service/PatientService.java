package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.*;
import fd.se.dbconcepts_project.entity.hospital.Ward;
import fd.se.dbconcepts_project.entity.hospital.WardBed;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.pojo.request.emergencynurse.PatientEnrollRequest;
import fd.se.dbconcepts_project.repository.PatientRepository;
import fd.se.dbconcepts_project.repository.WardNurseRepository;
import fd.se.dbconcepts_project.repository.WardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fd.se.dbconcepts_project.entity.consts.Constants.*;
import static fd.se.dbconcepts_project.entity.consts.Result.POSITIVE;
import static fd.se.dbconcepts_project.entity.consts.State.*;

@Service
@AllArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final WardNurseRepository wardNurseRepository;
    private final WardRepository wardRepository;

    private final MessageService messageService;
    private final UserService userService;

    public List<Patient> getWardNursePatients(int id) {
        return patientRepository.findByWardNurseId(id);
    }

    public List<Patient> getAllPatientsByState(Region region, Condition condition, State state) {
        final Specification<Patient> spec = (root, query, builder) -> {
            List<Predicate> preds = new ArrayList<>(4);
            preds.add(builder.equal(root.get("region"), region));
            if (condition != null) {
                preds.add(builder.equal(root.get("condition"), condition));
            }
            if (state != null) {
                preds.add(builder.equal(root.get("state"), state));
            }
            return builder.and(preds.toArray(new Predicate[0]));
        };
        return patientRepository.findAll(spec);
    }


    public Patient getPatientById(int id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getPatientsCanDischarge(Region region) {
        return patientRepository.findPatientsByTestsAndRegistrations(region,
                TEST_CHECK_LIMIT, POSITIVE,
                REGISTER_CHECK_LIMIT, TEMPERATURE_BORDER);
    }


    public List<Patient> getPatientsRegionNotMatchCondition(Region region) {
        return patientRepository.
                findPatientsByRegionAndConditionNot(region, Condition.of(region.value));
    }


    @Transactional
    public Patient createPatient(PatientEnrollRequest request) {
        final Patient patient = request.getPatientInfo().toPatient();
        final NucleicAcidTest test = request.getTestInfo().toNucleicAcidTest();
        patient.setState(TREATED);
        patient.setCondition(test.getCondition());
        patient.getNucleicAcidTests().add(test);
        return patientRepository.save(patient);
    }

    @Transactional
    public WardBed arrangePatient(Patient patient) {
        Region old = patient.getRegion();
        Region expected = Region.of(patient.getCondition().value);
        final List<Ward> wards =
                wardRepository.findAvailableWardByRegion(expected);
        final List<WardNurse> wardNurses = wardNurseRepository.
                findAvailableWardNursesByRegion(expected, expected.patientsMax);
        if (wards.isEmpty() || wardNurses.isEmpty()) {
            return null;
        }
        Ward ward = wards.get(0);
        WardNurse wardNurse = wardNurses.get(0);

        Optional<WardBed> wardBedOptional = ward.getWardBeds().stream().
                filter(wardbed -> wardbed.getPatient() == null).findFirst();
        if (!wardBedOptional.isPresent()) return null;
        WardBed wardBed = wardBedOptional.get();

        final boolean hasEmptyBed = unbindPatient(patient);
        if (hasEmptyBed) {
            log.info("Patient {} transferred to Region {}", patient.getId(), expected);
        }

        patient.setRegion(expected);
        patient.setWardNurse(wardNurse);
        patient.setWardBed(wardBed);
        patient = patientRepository.save(patient);
        messageService.createMessage(
                MessageType.TRANSFERRED_NOTIFY,
                userService.getUserByRegionAndProfession(expected,
                        Profession.HEAD_NURSE).getMedic(),
                patient,
                expected);
        if (hasEmptyBed) {
            rearrangePatients(old);
        }
        return wardBed;
    }

    @Transactional
    public void rearrangePatients(Region regionHasBed) {
        if (regionHasBed == null) return;
        Condition targetCondition = Condition.of(regionHasBed.value);
        final List<Patient> patientsIsolated = patientRepository.
                findPatientsByRegionAndCondition(null, targetCondition);
        for (Patient patient : patientsIsolated) {
            if (arrangePatient(patient) != null) {
                return;
            }
        }
        final List<Patient> patientsNeedTransfer = patientRepository.
                findPatientsByRegionNotAndCondition(regionHasBed, targetCondition);
        for (Patient patient : patientsNeedTransfer) {
            if (arrangePatient(patient) != null) {
                return;
            }
        }

    }


    private boolean unbindPatient(Patient patient) {
        if (patient.getRegion() == null) return false;
        patient.setWardNurse(null);
        patient.setWardBed(null);
        patientRepository.save(patient);
        log.info("Patient {} left original Ward. ",
                patient.getId());
        return true;
    }

    @Transactional
    public Patient changePatientInfo(int id, Condition condition, State state) {
        Patient patient = getPatientById(id);
        if (condition != null) {
            patient.setCondition(condition);
        }
        if (state != null) {
            patient.setState(state);
        }
        patient = patientRepository.save(patient);
        if (state == CURED || state == DIED) {
            final Region oldRegion = patient.getRegion();
            unbindPatient(patient);
            rearrangePatients(oldRegion);
        }else if (state == TREATED && condition != null) {
            if (arrangePatient(patient) != null) {
                log.info("Patient {} new Condition {}, Success to according Region",
                        patient.getId(), condition);
            } else {
                log.warn("Patient {} new Condition {}, Failed to according Region",
                        patient.getId(), condition);
            }

        }
        return patient;
    }


}



