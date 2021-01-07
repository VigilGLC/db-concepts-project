package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer>,
        JpaSpecificationExecutor<Patient> {
    List<Patient> findByWardNurseId(int id);

    Patient findById(int id);

    List<Patient> findByRegion(Region region);

    List<Patient> findPatientByWardNurseAndCondition(WardNurse wardNurse, Condition condition);

    List<Patient> findPatientsByRegionAndConditionNot(Region region, Condition condition);

    List<Patient> findPatientsByRegionAndConditionAndState(Region region, Condition condition, State state);

    List<Patient> findPatientsByRegionNotAndConditionAndState(Region region, Condition condition, State state);

}
