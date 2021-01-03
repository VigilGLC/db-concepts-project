package fd.se.dbconcepts_project.conifgs;


import fd.se.dbconcepts_project.entity.consts.Role;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.UserRepository;
import fd.se.dbconcepts_project.utils.EncryptUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@AllArgsConstructor
@Slf4j
public class DataBaseConfig {


    private final UserRepository userRepository;
    private final EncryptUtils encryptUtils;

    private static final String ADMIN = "admin";
    private static final String PASSWORD = "password";

    @PostConstruct
    public void init() {
        final User admin;
        if (!checkAdminExist()) {
            admin = createAdmin();
            log.info("Admin {} Created.", admin.getUsername());
        }
    }

    private boolean checkAdminExist() {
        return null != userRepository.findByUsername(ADMIN);
    }

    private User createAdmin() {
        final User admin = new User();
        admin.setUsername(ADMIN);
        admin.setName(ADMIN);
        admin.setPassword(encryptUtils.encrypt(PASSWORD));
        admin.setRole(Role.ADMIN);
        return userRepository.save(admin);
    }

}
