package fd.se.dbconcepts_project.pojo.request.doctor;


import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.Doctor;
import fd.se.dbconcepts_project.entity.patient.Patient;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
public class NucleicAcidTestRequest {

    private int id; // patient id

    private LocalDate date;
    private double temperature;
    private String symptom;
    private String state;


}
