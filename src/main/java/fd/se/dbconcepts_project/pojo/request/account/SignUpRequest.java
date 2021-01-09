package fd.se.dbconcepts_project.pojo.request.account;

import fd.se.dbconcepts_project.entity.consts.Gender;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String name;
    private String password;
    private Gender gender;
}
