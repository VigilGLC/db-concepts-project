package fd.se.dbconcepts_project.controller.medic;


import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.MereIdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fd.se.dbconcepts_project.entity.consts.Profession.HEAD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@RequestMapping("/user/medic/headnurse")
public class HeadNurseController {


    @Authorize(role = USER, professions = {HEAD_NURSE})
    @PostMapping("/wardnurse/delete")
    public ResponseEntity<?> deleteWardNurse(@RequestBody MereIdRequest request) {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @GetMapping("/wardnurses/spare")
    public ResponseEntity<?> getSpareWardNurse() {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @PostMapping("/wardnurse/add")
    public ResponseEntity<?> addWardNurse(@RequestBody MereIdRequest request) {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @GetMapping("/wards")
    public ResponseEntity<?> getWards() {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {HEAD_NURSE})
    @GetMapping("/ward/wardbeds")
    public ResponseEntity<?> getWardBeds(@RequestBody MereIdRequest request) {
        return ResponseEntity.ok().build();
    }

}
