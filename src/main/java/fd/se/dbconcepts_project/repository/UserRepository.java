package fd.se.dbconcepts_project.repository;


import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.usr.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    List<User> findAll();

    User findById(int id);

    List<User> findUserByMedicProfessionAndMedicRegion(Profession profession, Region region);

    User findByMedicRegionAndMedicProfession(Region region, Profession profession);

}
