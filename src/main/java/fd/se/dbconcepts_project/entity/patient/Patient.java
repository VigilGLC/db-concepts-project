package fd.se.dbconcepts_project.entity.patient;


import com.fasterxml.jackson.annotation.JsonBackReference;
import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Gender;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.hospital.WardBed;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.listener.PatientAuditListener;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EntityListeners(PatientAuditListener.class)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private Gender gender;
    private String address;

    private Region region;
    private Condition condition;
    private State state;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE)
    private WardNurse wardNurse;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE)
    private WardBed wardBed;

    @OneToMany(cascade = CascadeType.ALL)
    private List<NucleicAcidTest> nucleicAcidTests;
    @OneToMany(cascade = CascadeType.ALL)
    private List<InfoRegistration> infoRegistrations;

}
