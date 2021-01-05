package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fd.se.dbconcepts_project.entity.consts.Constants.*;
import static fd.se.dbconcepts_project.entity.consts.Result.POSITIVE;
import static fd.se.dbconcepts_project.entity.consts.State.TREATED;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final WardNurseRepository wardNurseRepository;
    private final WardRepository wardRepository;

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
        return patientRepository.findPatientsCanDisCharge(region,
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
        patient.setRegion(expected);
        patient.setWardNurse(wardNurse);
        patient = patientRepository.save(patient);
        wardBed.setPatient(patient);
        wardRepository.save(ward);
        return wardBed;
    }


}



