package fd.se.dbconcepts_project.entity.medic;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fd.se.dbconcepts_project.entity.patient.Patient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Getter
@Setter
public class WardNurse extends MedicBase {

    @JsonIgnore
    @OneToMany
    private Set<Patient> patients;

}
