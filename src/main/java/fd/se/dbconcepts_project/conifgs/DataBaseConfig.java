package fd.se.dbconcepts_project.conifgs;


import fd.se.dbconcepts_project.entity.consts.Role;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.UserRepository;
import fd.se.dbconcepts_project.utils.EncryptUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Slf4j
public class DataBaseConfig {


    private final UserRepository userRepository;
    private final EncryptUtils encryptUtils;

    private static final String ADMIN = "admin";
    private static final String PASSWORD = "password";

    @Bean
    public void initDatabase() {
        if (!checkAdminExist()) {
            createAdmin();
        }
    }

    private boolean checkAdminExist() {
        return null != userRepository.findByUsername(ADMIN);
    }

    private void createAdmin() {
        final User admin = new User();
        admin.setUsername(ADMIN);
        admin.setName(ADMIN);
        admin.setPassword(encryptUtils.encrypt(PASSWORD));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
    }

}
