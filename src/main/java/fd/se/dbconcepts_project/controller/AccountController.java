package fd.se.dbconcepts_project.controller;

import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.pojo.request.account.SignInRequest;
import fd.se.dbconcepts_project.pojo.request.account.SignUpRequest;
import fd.se.dbconcepts_project.pojo.response.TokenResponse;
import fd.se.dbconcepts_project.service.UserService;
import fd.se.dbconcepts_project.utils.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final UserService userService;
    private final TokenUtils tokenUtils;

    @Transactional
    @PostMapping(value = "/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        final User user;
        if (userService.checkUnique(request.getUsername()) &&
                (user = userService.createUser(request)) != null) {
            log.info("User {} created.", request.getUsername());
            return ResponseEntity.ok(user);
        }
        log.warn("User {} already exists.", request.getUsername());
        return ResponseEntity.badRequest().build();
    }


    @PostMapping(value = "/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        final User user;
        if (null != (user = userService.validateUser(request))) {
            final String token = tokenUtils.generateToken(user);
            log.info("User {} sign in Success.", user.getUsername());
            return ResponseEntity.ok(new TokenResponse(token));
        }
        log.warn("User {} sign in Fail.", request.getUsername());
        return ResponseEntity.badRequest().build();
    }


}
