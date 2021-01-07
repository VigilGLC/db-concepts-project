package fd.se.dbconcepts_project.entity.medic;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.usr.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MedicBase {
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Enumerated(EnumType.STRING)
    private Profession profession;


    @ToString.Exclude
    @JsonIgnore
    @OneToOne
    private User user;
}
