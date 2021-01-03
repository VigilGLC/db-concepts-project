package fd.se.dbconcepts_project.controller.medic;

import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.wardnurse.RegistrationReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static fd.se.dbconcepts_project.entity.consts.Profession.WARD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@RequestMapping("/user/medic/wardnurse")
public class WardNurseController {

    @Authorize(role = USER, professions = {WARD_NURSE})
    @GetMapping("/patients/state")
    public ResponseEntity<?> getAllPatients(@RequestParam String state) {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {WARD_NURSE})
    @GetMapping("/patients/canDischarge")
    public ResponseEntity<?> getAllPatientsCanDisCharge() {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {WARD_NURSE})
    @PostMapping("/patient/register")
    public ResponseEntity<?> reportRegistration(
            @RequestBody RegistrationReportRequest request) {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {WARD_NURSE})
    @GetMapping("/patient/test")
    public ResponseEntity<?> checkNucleicAcidTest(@RequestParam int id,
                                                  @RequestParam LocalDateTime time) {
        return ResponseEntity.ok().build();
    }

}
