package fd.se.dbconcepts_project.controller.medic;

import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.wardnurse.RegistrationReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static fd.se.dbconcepts_project.entity.consts.Profession.WARD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@RequestMapping("/user/medic/wardnurse")
public class WardNurseController {

    @Authorize(role = USER, professions = {WARD_NURSE})
    @GetMapping("/patients/condition")
    public ResponseEntity<?> getAllPatients(@RequestParam Condition condition) {
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
                                                  @RequestParam LocalDate date) {
        return ResponseEntity.ok().build();
    }

}
