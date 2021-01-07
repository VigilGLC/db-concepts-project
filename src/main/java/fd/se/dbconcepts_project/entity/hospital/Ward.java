package fd.se.dbconcepts_project.entity.hospital;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fd.se.dbconcepts_project.entity.consts.Region;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Region region;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WardBed> wardBeds;


}
