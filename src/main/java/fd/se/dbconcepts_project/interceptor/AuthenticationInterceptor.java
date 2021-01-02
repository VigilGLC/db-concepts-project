package fd.se.dbconcepts_project.interceptor;

import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final Subject subject;
    private final TokenUtils tokenUtils;


    private final static String AUTHENTICATION_HEADER = "Authentication";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader(AUTHENTICATION_HEADER);
        if (header == null) return false;
        header = header.trim();
        final User user = tokenUtils.extractUser(header);
        if (user != null) {
            subject.setUser(user);
            return true;
        }
        return false;
    }
}
