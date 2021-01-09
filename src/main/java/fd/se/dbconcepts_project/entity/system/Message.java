package fd.se.dbconcepts_project.entity.system;


import fd.se.dbconcepts_project.entity.medic.MedicBase;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;
    private LocalDateTime time;

    private int patientId;

    @ManyToOne
    private MedicBase receiver;
}
