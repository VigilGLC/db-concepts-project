package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface NucleicAcidTestRepository extends CrudRepository<NucleicAcidTest, Integer> {

    public NucleicAcidTest findTop1ByPatientIdAndDate(int id, LocalDate date);


    @Query(nativeQuery = true,
            value = "" +
                    "select count(*) " +
                    "from " +
                    "   (select * from NUCLEIC_ACID_TEST" +
                    "       where NUCLEIC_ACID_TEST.PATIENT_ID=?1 " +
                    "       order by DATE desc limit ?2)" +
                    "where upper(RESULT)=upper(?3)"
    )
    int countByPatientOrderByDateTop(int patientId, int limit, String result);

}
