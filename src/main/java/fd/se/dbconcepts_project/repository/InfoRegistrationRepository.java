package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.patient.InfoRegistration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRegistrationRepository extends CrudRepository<InfoRegistration, Integer> {


    @Query(nativeQuery = true,
            value = "" +
                    "select count(*) " +
                    "from " +
                    "   (select * from INFO_REGISTRATION" +
                    "       where INFO_REGISTRATION.PATIENT_ID=?1 " +
                    "       order by DATE desc limit ?2)" +
                    "where TEMPERATURE <= ?3"
    )
    int countByPatientOrderByDateTop(int patientId, int limit, double temperature);


}
