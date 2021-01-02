package fd.se.dbconcepts_project.conifgs;


import fd.se.dbconcepts_project.interceptor.AuthenticationInterceptor;
import fd.se.dbconcepts_project.interceptor.authorize.AuthorizationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class DbconceptsConfig implements WebMvcConfigurer {

    final AuthenticationInterceptor authenticationInterceptor;
    final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).
                excludePathPatterns("/signUp", "/signIn");
        registry.addInterceptor(authorizationInterceptor).
                excludePathPatterns("/signUp", "/signIn");
    }


}
