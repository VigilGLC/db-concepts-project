package fd.se.dbconcepts_project.entity.hospital;

import fd.se.dbconcepts_project.entity.consts.Region;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Region region;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<WardBed> wardBeds;


}
