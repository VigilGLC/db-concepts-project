package fd.se.dbconcepts_project.interceptor.authorize;


import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Role;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorize {

    Role role() default Role.ANY;

    Profession[] professions() default {};
}
