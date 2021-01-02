package fd.se.dbconcepts_project.entity.medic;


import com.fasterxml.jackson.annotation.JsonBackReference;
import fd.se.dbconcepts_project.entity.patient.Patient;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class WardNurse extends MedicBase {

    @JsonBackReference
    @OneToMany
    private Set<Patient> patients;

}
