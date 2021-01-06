package fd.se.dbconcepts_project.controller.medic;


import fd.se.dbconcepts_project.entity.hospital.Ward;
import fd.se.dbconcepts_project.entity.hospital.WardBed;
import fd.se.dbconcepts_project.entity.medic.HeadNurse;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.interceptor.Subject;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.MereIdRequest;
import fd.se.dbconcepts_project.pojo.response.UsersResponse;
import fd.se.dbconcepts_project.pojo.response.WardBedsResponse;
import fd.se.dbconcepts_project.pojo.response.WardsResponse;
import fd.se.dbconcepts_project.service.ProfessionService;
import fd.se.dbconcepts_project.service.WardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fd.se.dbconcepts_project.entity.consts.Profession.HEAD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user/medic/headnurse")
public class HeadNurseController {

    private final ProfessionService professionService;
    private final WardService wardService;

    private final Subject subject;

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @PostMapping("/wardnurse/delete")
    public ResponseEntity<?> deleteWardNurse(@RequestBody MereIdRequest request) {
        final User currUser = subject.getUser();
        final boolean result = professionService.deleteWardNurse(request.getId());
        if (result) {
            log.info("HeadNurse {} delete WardNurse {} Success. ",
                    currUser.getUsername(), request.getId());
            return ResponseEntity.ok().build();
        }
        log.warn("HeadNurse {} delete WardNurse {} Failed. ",
                currUser.getUsername(), request.getId());
        return ResponseEntity.noContent().build();
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @GetMapping("/wardnurses/spare")
    public ResponseEntity<?> getSpareWardNurse() {
        final User currUser = subject.getUser();
        final List<User> wardNurseUsers = professionService.getSpareWardNurseUsers();
        log.info("User {} get wardnurses.", currUser.getUsername());
        return ResponseEntity.ok(new UsersResponse(wardNurseUsers));
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @PostMapping("/wardnurse/add")
    public ResponseEntity<?> addWardNurse(@RequestBody MereIdRequest request) {
        final User currUser = subject.getUser();
        final HeadNurse headNurse = professionService.getHeadNurse(currUser);
        final boolean result = professionService.addWardNurse(headNurse, request.getId());
        if (result) {
            log.info("HeadNurse {} add WardNurse {} Success. ",
                    currUser.getUsername(), request.getId());
            return ResponseEntity.ok().build();
        }
        log.warn("HeadNurse {} add WardNurse {} Failed. ",
                currUser.getUsername(), request.getId());
        return ResponseEntity.noContent().build();
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @GetMapping("/wards")
    public ResponseEntity<?> getWards() {
        final User currUser = subject.getUser();
        log.info("HeadNurse {} get all wards in Region.", currUser.getUsername());
        final List<Ward> wards = wardService.getAllWardsByRegion(currUser.getMedic().getRegion());
        return ResponseEntity.ok(new WardsResponse(wards));
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @GetMapping("/ward/wardbeds")
    public ResponseEntity<?> getWardBeds(@RequestParam int id) {
        final User currUser = subject.getUser();
        log.info("HeadNurse {} get wardBeds in Ward {}.", currUser.getUsername(), id);
        final List<WardBed> wardBeds = wardService.getWardBeds(id);
        return ResponseEntity.ok(new WardBedsResponse(wardBeds));
    }

}
