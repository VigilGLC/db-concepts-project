package fd.se.dbconcepts_project.entity.patient;

import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.Doctor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class NucleicAcidTest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate date;
    private double temperature;
    private String symptom;
    private State state;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
}
