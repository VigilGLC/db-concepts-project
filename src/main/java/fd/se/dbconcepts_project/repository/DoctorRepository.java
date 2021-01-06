package fd.se.dbconcepts_project.repository;


import fd.se.dbconcepts_project.entity.medic.Doctor;
import fd.se.dbconcepts_project.entity.usr.User;
import org.omg.CORBA.INTERNAL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    Doctor findByUser(User user);

}
