package fd.se.dbconcepts_project.controller.medic;


import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.emergencynurse.PatientEnrollRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fd.se.dbconcepts_project.entity.consts.Profession.EMERGENCY_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Profession.HEAD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@RequestMapping("/user/medic/emergencynurse")
public class EmergencyNurseController {


    @Authorize(role = USER, professions = {EMERGENCY_NURSE})
    @PostMapping("/patient/enroll")
    public ResponseEntity<?> enrollPatient(@RequestBody PatientEnrollRequest request) {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {EMERGENCY_NURSE})
    @GetMapping("/patients")
    public ResponseEntity<?> getPatients(@RequestParam String region, @RequestParam String condition,
                                         @RequestParam String state) {
        return ResponseEntity.ok().build();
    }


}
