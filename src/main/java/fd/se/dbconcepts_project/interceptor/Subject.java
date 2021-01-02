package fd.se.dbconcepts_project.interceptor;


import fd.se.dbconcepts_project.entity.usr.User;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@RequestScope
@Data
public class Subject {

    private User user;
}
