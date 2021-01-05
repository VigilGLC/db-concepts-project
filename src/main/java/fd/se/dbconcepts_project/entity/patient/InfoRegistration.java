package fd.se.dbconcepts_project.entity.patient;

import fd.se.dbconcepts_project.entity.consts.Result;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.listener.InfoRegistrationAuditListener;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EntityListeners(InfoRegistrationAuditListener.class)
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
    @OneToOne
    private NucleicAcidTest nucleicAcidTest;

}
