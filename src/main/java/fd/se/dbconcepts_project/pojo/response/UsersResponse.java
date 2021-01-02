package fd.se.dbconcepts_project.pojo.response;


import fd.se.dbconcepts_project.entity.usr.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsersResponse {

    private List<User> users;

}
