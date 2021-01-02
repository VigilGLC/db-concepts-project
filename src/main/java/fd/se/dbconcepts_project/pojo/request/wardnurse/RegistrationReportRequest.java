package fd.se.dbconcepts_project.pojo.request.wardnurse;


import fd.se.dbconcepts_project.entity.consts.Condition;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationReportRequest {

    private int id; // patient id

    private LocalDateTime time;
    private String result;
    private String condition;

}
