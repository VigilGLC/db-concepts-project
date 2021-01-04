package fd.se.dbconcepts_project.controller;


import fd.se.dbconcepts_project.entity.hospital.Ward;
import fd.se.dbconcepts_project.entity.medic.MedicBase;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.MereIdRequest;
import fd.se.dbconcepts_project.pojo.request.admin.ProfessionAssignRequest;
import fd.se.dbconcepts_project.pojo.request.admin.WardBuildRequest;
import fd.se.dbconcepts_project.pojo.response.UsersResponse;
import fd.se.dbconcepts_project.pojo.response.WardsResponse;
import fd.se.dbconcepts_project.service.UserService;
import fd.se.dbconcepts_project.service.WardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fd.se.dbconcepts_project.entity.consts.Role.ADMIN;

@RestController
@RequestMapping("/admin")
@Slf4j
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final WardService wardService;

    @Authorize(role = ADMIN)
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        log.info("Admin get all users.");
        return ResponseEntity.ok(new UsersResponse(userService.getAllUsers()));
    }

    @Authorize(role = ADMIN)
    @PostMapping("/user/assign")
    public ResponseEntity<?> assignProfession(@RequestBody ProfessionAssignRequest request) {
        final MedicBase medic;
        if (null != (medic = userService.assignProfession(request))) {
            log.info("Admin assign profession for User {} Success.", medic.getUser().getUsername());
            return ResponseEntity.ok().build();
        }
        log.warn("Admin assign profession for User {} Fail.", request.getId());
        return ResponseEntity.badRequest().build();
    }


    @Authorize(role = ADMIN)
    @GetMapping("/wards")
    public ResponseEntity<?> getWards() {
        log.info("Admin get all wards.");
        return ResponseEntity.ok(new WardsResponse(wardService.getAllWards()));
    }

    @Authorize(role = ADMIN)
    @PostMapping("/ward/build")
    public ResponseEntity<?> buildWard(@RequestBody WardBuildRequest request) {
        final Ward ward = wardService.addWard(request.getRegion());
        log.info("Admin add Ward {} in Region {}.", ward.getId(), request.getRegion());
        return ResponseEntity.ok().build();
    }

    @Authorize(role = ADMIN)
    @PostMapping("/ward/wardbed/build")
    public ResponseEntity<?> buildWardBed(@RequestBody MereIdRequest request) {
        final Ward ward = wardService.addWardBed(request.getId());
        if (ward == null) {
            log.warn("Target Ward {} is full, build Fail.", request.getId());
            return ResponseEntity.badRequest().build();
        }
        log.info("Admin add WardBed in Ward {}.", ward.getId());
        return ResponseEntity.ok().build();
    }

}
