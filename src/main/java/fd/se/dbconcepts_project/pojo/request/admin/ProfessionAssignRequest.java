package fd.se.dbconcepts_project.pojo.request.admin;


import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Region;
import lombok.Data;

@Data
public class ProfessionAssignRequest {

    private int id; // user ID

    private Region region;
    private Profession profession;
}
