package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.Result;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer>,
        JpaSpecificationExecutor<Patient> {
    List<Patient> findByWardNurseId(int id);

    Patient findById(int id);


    @Query(nativeQuery = true,
            value = "select * " +
                    "from PATIENT as patient " +
                    "where " +
                    "   patient.REGION = ?1 " +
                    "   and " +
                    "   ?2 = " +
                    "   ( select count(*) " +
                    "   from (select * from NUCLEIC_ACID_TEST join PATIENT P on P.ID = NUCLEIC_ACID_TEST.PATIENT_ID " +
                    "       where P.ID = patient.ID order by DATE limit ?2 )" +
                    "   where RESULT= ?3 ) " +
                    "   and " +
                    "   ?4 = " +
                    "   ( select count(*) " +
                    "    from (select * from INFO_REGISTRATION join PATIENT P2 on INFO_REGISTRATION.PATIENT_ID = P2.ID " +
                    "       where P2.ID=patient.ID order by DATE limit ?4)" +
                    "   where TEMPERATURE <= ?5 )"
    )
    List<Patient> findPatientsByTestsAndRegistrations(Region region, // ?1
                                                      int testLimit, Result testResult, // ?2, ?3
                                                      int registLimit, double temperature); // ?4, ?5

    @Query(nativeQuery = true,
            value = "select * " +
                    "from PATIENT as patient " +
                    "where " +
                    "   patient.WARD_NURSE_ID = ?1 " +
                    "   and " +
                    "   ?2 = " +
                    "   ( select count(*) " +
                    "   from (select * from NUCLEIC_ACID_TEST join PATIENT P on P.ID = NUCLEIC_ACID_TEST.PATIENT_ID " +
                    "       where P.ID = patient.ID order by DATE limit ?2 )" +
                    "   where RESULT= ?3 ) " +
                    "   and " +
                    "   ?4 = " +
                    "   ( select count(*) " +
                    "    from (select * from INFO_REGISTRATION join PATIENT P2 on INFO_REGISTRATION.PATIENT_ID = P2.ID " +
                    "       where P2.ID=patient.ID order by DATE limit ?4)" +
                    "   where TEMPERATURE <= ?5 )"
    )
    List<Patient> findWardNursePatientByTestsAndRegistrations(int wardNurseId,  // ?1
                                                              int testLimit, Result testResult, // ?2, ?3
                                                              int registLimit, double temperature); // ?4, ?5

    @Query(nativeQuery = true,
            value = "select * " +
                    "from PATIENT as patient " +
                    "where " +
                    "   patient.ID = ?1 " +
                    "   and " +
                    "   ?2 = " +
                    "   ( select count(*) " +
                    "   from (select * from NUCLEIC_ACID_TEST join PATIENT P on P.ID = NUCLEIC_ACID_TEST.PATIENT_ID " +
                    "       where P.ID = patient.ID order by DATE limit ?2 )" +
                    "   where RESULT= ?3 ) " +
                    "   and " +
                    "   ?4 = " +
                    "   ( select count(*) " +
                    "    from (select * from INFO_REGISTRATION join PATIENT P2 on INFO_REGISTRATION.PATIENT_ID = P2.ID " +
                    "       where P2.ID=patient.ID order by DATE limit ?4)" +
                    "   where TEMPERATURE <= ?5 )"
    )
    Patient testPatientByTestsAndRegistrations(int patientId,  // ?1
                                               int testLimit, Result testResult, // ?2, ?3
                                               int registLimit, double temperature); // ?4, ?5


    List<Patient> findPatientByWardNurseAndCondition(WardNurse wardNurse, Condition condition);

    List<Patient> findPatientsByRegionAndConditionNot(Region region, Condition condition);

    List<Patient> findPatientsByRegionAndConditionAndState(Region region, Condition condition, State state);

    List<Patient> findPatientsByRegionNotAndConditionAndState(Region region, Condition condition, State state);

}
