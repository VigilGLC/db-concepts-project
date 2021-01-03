package fd.se.dbconcepts_project.interceptor.authorize;

import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Role;
import fd.se.dbconcepts_project.entity.medic.MedicBase;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.interceptor.Subject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final Subject subject;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        Authorize annotation;
        if ((annotation = method.getAnnotation(Authorize.class)) != null ||
                (annotation = handlerMethod.getBeanType().getAnnotation(Authorize.class)) != null) {
            boolean authorized = true;
            final Role role = annotation.role();
            final List<Profession> professions = Arrays.stream(annotation.professions()).
                    collect(Collectors.toList());

            final User currUser = subject.getUser();
            if (role != Role.ANY && currUser.getRole() != role) {
                authorized = false;
            }

            if (professions.size() != 0) {
                final MedicBase currMedic = currUser.getMedic();
                authorized = currMedic != null && professions.contains(currMedic.getProfession());
            }
            if (!authorized) {
                log.warn("User {} authority Intercepted.", currUser.getUsername());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }
}
