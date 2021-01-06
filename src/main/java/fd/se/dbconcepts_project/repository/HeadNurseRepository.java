package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.medic.HeadNurse;
import fd.se.dbconcepts_project.entity.usr.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadNurseRepository extends CrudRepository<HeadNurse, Integer> {

    public HeadNurse findByUser(User user);

}
