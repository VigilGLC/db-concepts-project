package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.hospital.Ward;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends CrudRepository<Ward, Integer> {
    List<Ward> findAll();

    List<Ward> findAllByRegion(Region region);


    Ward findById(int id);

    @Query(value = "select ward from Ward ward " +
            "where " +
            "   ward.region=:region " +
            "   and " +
            "   exists " +
            "   (select count(wardbed) from ward.wardBeds wardbed where wardbed.patient is null)")
    List<Ward> findAvailableWardByRegion(Region region);

}
