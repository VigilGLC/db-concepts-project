package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer>,
        JpaSpecificationExecutor<Patient> {
    List<Patient> findByWardNurseId(int id);
}
