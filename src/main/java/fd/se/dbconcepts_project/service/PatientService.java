package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public List<Patient> getWardNursePatients(int id) {
        return patientRepository.findByWardNurseId(id);
    }

    @Transactional
    public List<Patient> getAllPatientsByState(Region region, State state) {

        final Specification<Patient> specRegion =
                (root, query, builder) -> builder.equal(root.get("region"), region);
        final Specification<Patient> specState =
                (root, query, builder) -> {
                    if (state == null) return null;
                    else {
                        return builder.equal(root.get("state"), state);
                    }
                };
        return patientRepository.findAll(specRegion.and(specRegion));
    }

}



