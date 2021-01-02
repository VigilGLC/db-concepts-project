package fd.se.dbconcepts_project.pojo.request.account;


import fd.se.dbconcepts_project.entity.consts.Gender;
import lombok.Data;

@Data
public class ProfileChangeRequest {
    private String password;

    private String name;
    private Gender gender;
}
