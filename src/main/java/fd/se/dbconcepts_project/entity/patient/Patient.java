package fd.se.dbconcepts_project.entity.patient;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Gender;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.hospital.WardBed;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;

    @Enumerated(EnumType.STRING)
    private Region region;
    @Enumerated(EnumType.STRING)
    private Condition condition;
    @Enumerated(EnumType.STRING)
    private State state;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE)
    private WardNurse wardNurse;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE)
    private WardBed wardBed;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<NucleicAcidTest> nucleicAcidTests = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<InfoRegistration> infoRegistrations = new ArrayList<>();

}
