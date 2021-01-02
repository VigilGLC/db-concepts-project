package fd.se.dbconcepts_project.pojo.request.admin;


import fd.se.dbconcepts_project.entity.consts.Region;
import lombok.Data;

@Data
public class WardBuildRequest {
    private Region region;
}
