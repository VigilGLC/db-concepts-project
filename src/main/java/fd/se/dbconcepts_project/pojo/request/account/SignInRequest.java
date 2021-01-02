package fd.se.dbconcepts_project.pojo.request.account;


import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
