package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardNurseRepository extends CrudRepository<WardNurse, Integer> {

    @Query(value = "select wardnurse from WardNurse wardnurse " +
            "where wardnurse.region =:region and  wardnurse.patients.size < :patientsMax")
    List<WardNurse> findAvailableWardNursesByRegion(Region region, int patientsMax);


}
