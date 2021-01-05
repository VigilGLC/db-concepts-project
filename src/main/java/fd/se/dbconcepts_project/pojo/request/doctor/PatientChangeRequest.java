package fd.se.dbconcepts_project.pojo.request.doctor;


import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.State;
import lombok.Data;

@Data
public class PatientChangeRequest {

    private int id;

    private Condition condition;
    private State state;

}
