package fd.se.dbconcepts_project.entity.medic;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class Doctor extends MedicBase {
}
