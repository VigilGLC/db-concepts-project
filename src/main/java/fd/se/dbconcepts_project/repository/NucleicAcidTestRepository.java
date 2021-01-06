package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface NucleicAcidTestRepository extends CrudRepository<NucleicAcidTest, Integer> {

    public NucleicAcidTest findByPatientIdAndDate(int id, LocalDate date);

}
