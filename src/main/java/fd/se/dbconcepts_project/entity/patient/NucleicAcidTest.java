package fd.se.dbconcepts_project.entity.patient;

import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Result;
import fd.se.dbconcepts_project.entity.medic.Doctor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class NucleicAcidTest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Result result;
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
}
