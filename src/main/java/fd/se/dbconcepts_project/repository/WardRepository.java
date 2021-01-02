package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.hospital.Ward;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WardRepository extends CrudRepository<Ward, Integer> {
    List<Ward> findAll();
    Ward findById(int id);
}
