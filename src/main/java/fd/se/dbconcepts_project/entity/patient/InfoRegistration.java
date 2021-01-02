package fd.se.dbconcepts_project.entity.patient;

import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.listener.InfoRegistrationAuditListener;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(InfoRegistrationAuditListener.class)
public class InfoRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime time;
    private String result;
    private Condition condition;

    @ManyToOne
    private Patient patient;
    @OneToOne
    private NucleicAcidTest nucleicAcidTest;
    @ManyToOne
    private WardNurse wardNurse;

}
