package fd.se.dbconcepts_project.entity.patient;

import fd.se.dbconcepts_project.entity.consts.Result;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class InfoRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate date;
    private double temperature;
    private String symptom;
    private State state;
    private Result result;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private WardNurse wardNurse;

}
