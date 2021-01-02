package fd.se.dbconcepts_project.utils;


import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {


    final UserRepository userRepository;

    @Autowired
    public TokenUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String generateToken(User user) {
        return user.getUsername() + ' ' + user.getPassword();
    }

    public User extractUser(String token) {
        final String[] segments = token.split(" ");
        if (segments.length != 2) {
            return null;
        }
        final String username = segments[0];
        final String password = segments[1];
        final User User = userRepository.findByUsername(username);
        if (User != null && User.getPassword().equals(password)) {
            return User;
        }
        return null;
    }


}
