package fd.se.dbconcepts_project.controller.medic;


import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.MereIdRequest;
import fd.se.dbconcepts_project.pojo.request.doctor.NucleicAcidTestRequest;
import fd.se.dbconcepts_project.pojo.request.doctor.PatientChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fd.se.dbconcepts_project.entity.consts.Profession.DOCTOR;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@RequestMapping("/user/medic/doctor")
public class DoctorController {


    @Authorize(role = USER, professions = {DOCTOR})
    @GetMapping("/headnurse")
    public ResponseEntity<?> getHeadNurse() {
        return ResponseEntity.ok().build();
    }


    @Authorize(role = USER, professions = {DOCTOR})
    @PostMapping("/patient/change")
    public ResponseEntity<?> changePatientInfo(@RequestBody PatientChangeRequest request) {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {DOCTOR})
    @PostMapping("/patient/test")
    public ResponseEntity<?> testNucleicAcid(@RequestBody NucleicAcidTestRequest request) {
        return ResponseEntity.ok().build();
    }

    @Authorize(role = USER, professions = {DOCTOR})
    @PostMapping("/patient/discharge")
    public ResponseEntity<?> dischargePatient(@RequestBody MereIdRequest request) {
        return ResponseEntity.ok().build();
    }


}
