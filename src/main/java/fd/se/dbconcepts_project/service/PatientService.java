package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static fd.se.dbconcepts_project.entity.consts.Constants.*;
import static fd.se.dbconcepts_project.entity.consts.Result.POSITIVE;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getWardNursePatients(int id) {
        return patientRepository.findByWardNurseId(id);
    }

    public List<Patient> getAllPatientsByState(Region region, State state) {
        final Specification<Patient> spec = (root, query, builder) -> {
            List<Predicate> preds = new ArrayList<>(2);
            preds.add(builder.equal(root.get("region"), region));
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


}



