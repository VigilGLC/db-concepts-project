package fd.se.dbconcepts_project.entity.hospital;


import fd.se.dbconcepts_project.entity.hospital.listener.WardBedAuditListener;
import fd.se.dbconcepts_project.entity.patient.Patient;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(WardBedAuditListener.class)
public class WardBed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ward ward;

    @OneToOne
    private Patient patient;

}
