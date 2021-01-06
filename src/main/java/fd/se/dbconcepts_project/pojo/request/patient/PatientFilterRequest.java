package fd.se.dbconcepts_project.pojo.request.patient;

import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import lombok.Data;

@Data
public class PatientFilterRequest {

    private State state;
    private Condition condition;
    private Region region;
}
