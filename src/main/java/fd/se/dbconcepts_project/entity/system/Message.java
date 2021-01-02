package fd.se.dbconcepts_project.entity.system;


import fd.se.dbconcepts_project.entity.medic.MedicBase;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;
    private LocalDateTime time;

    @ManyToOne
    private MedicBase receiver;
}
