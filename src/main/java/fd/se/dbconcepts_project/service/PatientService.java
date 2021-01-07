package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.*;
import fd.se.dbconcepts_project.entity.hospital.Ward;
import fd.se.dbconcepts_project.entity.hospital.WardBed;
import fd.se.dbconcepts_project.entity.medic.Doctor;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.InfoRegistration;
import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.pojo.request.doctor.NucleicAcidTestRequest;
import fd.se.dbconcepts_project.pojo.request.emergencynurse.PatientEnrollRequest;
import fd.se.dbconcepts_project.pojo.request.wardnurse.RegistrationReportRequest;
import fd.se.dbconcepts_project.repository.NucleicAcidTestRepository;
import fd.se.dbconcepts_project.repository.PatientRepository;
import fd.se.dbconcepts_project.repository.WardNurseRepository;
import fd.se.dbconcepts_project.repository.WardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fd.se.dbconcepts_project.entity.consts.Constants.*;
import static fd.se.dbconcepts_project.entity.consts.Profession.DOCTOR;
import static fd.se.dbconcepts_project.entity.consts.Profession.HEAD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Result.NEGATIVE;
import static fd.se.dbconcepts_project.entity.consts.State.*;

@Service
@AllArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final WardNurseRepository wardNurseRepository;
    private final WardRepository wardRepository;
    private final NucleicAcidTestRepository nucleicAcidTestRepository;

    private final MessageService messageService;
    private final UserService userService;

    public List<Patient> getWardNursePatients(int id) {
        return patientRepository.findByWardNurseId(id);
    }

    public List<Patient> getAllPatientsBy(Region region, Condition condition, State state) {
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
        return patientRepository.findByRegion(region).stream().
                filter(canPatientDischarge).collect(Collectors.toList());
    }

    public List<Patient> getPatientsRegionNotMatchCondition(Region region) {
        return patientRepository.
                findPatientsByRegionAndConditionNot(region, Condition.of(region.value));
    }

    public List<Patient> getWardNursePatientsByCondition(WardNurse wardNurse, Condition condition) {
        return patientRepository.
                findPatientByWardNurseAndCondition(wardNurse, condition);
    }

    public List<Patient> getWardNursePatientsCanDischarge(int wardNurseId) {
        return patientRepository.findByWardNurseId(wardNurseId).stream().
                filter(canPatientDischarge).collect(Collectors.toList());
    }

    public NucleicAcidTest getTestForPatientByDate(int patientId, LocalDate date) {
        return nucleicAcidTestRepository.findByPatientIdAndDate(patientId, date);
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
        if (old == expected) return patient.getWardBed();

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
        if (hasEmptyBed) {
            rearrangePatients(old);
        }
        notifyTransferred(patient);

        return wardBed;
    }

    @Transactional
    public void rearrangePatients(Region regionHasBed) {
        if (regionHasBed == null) return;
        Condition targetCondition = Condition.of(regionHasBed.value);
        final List<Patient> patientsIsolated = patientRepository.
                findPatientsByRegionAndConditionAndState(null, targetCondition, TREATED);
        for (Patient patient : patientsIsolated) {
            if (arrangePatient(patient) != null) {
                return;
            }
        }
        final List<Patient> patientsNeedTransfer = patientRepository.
                findPatientsByRegionNotAndConditionAndState(regionHasBed, targetCondition, TREATED);
        for (Patient patient : patientsNeedTransfer) {
            if (arrangePatient(patient) != null) {
                return;
            }
        }

    }


    private boolean unbindPatient(Patient patient) {
        if (patient.getRegion() == null) return false;
        patient.setRegion(null);
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
        } else if (state == TREATED && condition != null) {
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

    private User getUserOf(Region region, Profession profession) {
        return userService.getUserByRegionAndProfession(region, profession);
    }

    private void tryNotifyDischarge(Patient patient) {
        if (canPatientDischarge.test(patient)) {
            messageService.createMessage(MessageType.TRANSFERRED_NOTIFY,
                    getUserOf(patient.getRegion(), DOCTOR).getMedic(),
                    patient, patient.getRegion());
        }
    }

    private void notifyTransferred(Patient patient) {
        messageService.createMessage(
                MessageType.TRANSFERRED_NOTIFY,
                getUserOf(patient.getRegion(), HEAD_NURSE).getMedic(),
                patient,
                patient.getRegion());
    }

    @Transactional
    public NucleicAcidTest testPatient(Doctor doctor, NucleicAcidTestRequest request) {
        final int patientId = request.getId();
        final NucleicAcidTest test = request.toNucleicAcidTest();
        Patient patient = getPatientById(patientId);
        test.setDoctor(doctor);
        patient.getNucleicAcidTests().add(test);
        patient = patientRepository.save(patient);

        tryNotifyDischarge(patient);

        return test;
    }

    @Transactional
    public InfoRegistration registerPatient(WardNurse wardNurse, RegistrationReportRequest request) {
        final int patientId = request.getId();
        Patient patient = getPatientById(patientId);
        final InfoRegistration registration = request.toInfoRegistration();

        registration.setWardNurse(wardNurse);
        patient.getInfoRegistrations().add(registration);
        patientRepository.save(patient);

        tryNotifyDischarge(patient);

        return registration;
    }


    private static final java.util.function.Predicate<Patient> canPatientDischarge =
            patient -> {
                List<NucleicAcidTest> tests = patient.getNucleicAcidTests();
                List<InfoRegistration> registrations = patient.getInfoRegistrations();
                if (tests == null || registrations == null) {
                    return false;
                }
                return TEST_CHECK_LIMIT ==
                        tests.stream().sorted(Comparator.comparing(NucleicAcidTest::getDate).reversed()).limit(TEST_CHECK_LIMIT).
                                filter(test -> test.getResult() == NEGATIVE).count() &&
                        REGISTER_CHECK_LIMIT ==
                                registrations.stream().sorted(Comparator.comparing(InfoRegistration::getDate).reversed()).limit(REGISTER_CHECK_LIMIT).
                                        filter(registration -> registration.getTemperature() <= TEMPERATURE_BORDER).count();

            };


}



