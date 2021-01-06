package fd.se.dbconcepts_project.entity.hospital;


import com.fasterxml.jackson.annotation.JsonBackReference;
import fd.se.dbconcepts_project.entity.patient.Patient;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class WardBed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    private Ward ward;

    @OneToOne
    private Patient patient;

}
