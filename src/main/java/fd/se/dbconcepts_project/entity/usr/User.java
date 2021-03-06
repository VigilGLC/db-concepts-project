package fd.se.dbconcepts_project.entity.usr;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fd.se.dbconcepts_project.entity.consts.Gender;
import fd.se.dbconcepts_project.entity.consts.Role;
import fd.se.dbconcepts_project.entity.medic.MedicBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private MedicBase medic;

}



