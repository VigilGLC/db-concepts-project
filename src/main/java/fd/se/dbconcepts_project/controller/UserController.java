package fd.se.dbconcepts_project.controller;


import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.interceptor.Subject;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.account.ProfileChangeRequest;
import fd.se.dbconcepts_project.pojo.response.TokenResponse;
import fd.se.dbconcepts_project.service.UserService;
import fd.se.dbconcepts_project.utils.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

import static fd.se.dbconcepts_project.entity.consts.Role.ADMIN;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final Subject subject;
    private final UserService userService;
    private final TokenUtils tokenUtils;

    @Authorize(role = USER)
    @GetMapping(value = "/profession")
    public ResponseEntity<?> getProfession() {
        final User currUser = subject.getUser();
        log.info("User {} get profession.", currUser.getUsername());
        return ResponseEntity.ok(currUser.getMedic());
    }

    @Authorize(role = USER)
    @GetMapping(value = "/profile")
    public ResponseEntity<?> getProfile() {
        final User currUser = subject.getUser();
        log.info("User {} get profile.", currUser.getUsername());
        return ResponseEntity.ok(currUser);
    }

    @Authorize(role = USER)
    @GetMapping(value = "/profile/change")
    public ResponseEntity<?> changeProfile(@RequestBody ProfileChangeRequest request) {
        final User currUser = subject.getUser();
        final User user = userService.changeProfile(currUser, request);
        final String token = tokenUtils.generateToken(user);
        log.info("User {} change profile Success.", currUser.getUsername());
        return ResponseEntity.ok(new TokenResponse(token));
    }

}
