package fd.se.dbconcepts_project.controller.medic;


import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.Doctor;
import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.interceptor.Subject;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.MereIdRequest;
import fd.se.dbconcepts_project.pojo.request.doctor.NucleicAcidTestRequest;
import fd.se.dbconcepts_project.pojo.request.doctor.PatientChangeRequest;
import fd.se.dbconcepts_project.service.PatientService;
import fd.se.dbconcepts_project.service.ProfessionService;
import fd.se.dbconcepts_project.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fd.se.dbconcepts_project.entity.consts.Profession.DOCTOR;
import static fd.se.dbconcepts_project.entity.consts.Profession.HEAD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/user/medic/doctor")
public class DoctorController {

    private final UserService userService;
    private final PatientService patientService;
    private final Subject subject;

    private final ProfessionService professionService;

    @Authorize(role = USER, professions = {DOCTOR})
    @GetMapping("/headnurse")
    public ResponseEntity<?> getHeadNurse() {
        final User currUser = subject.getUser();
        Region region = currUser.getMedic().getRegion();
        log.info("User {} get region's head nurse", currUser.getUsername());
        return ResponseEntity.ok(userService.getUserByRegionAndProfession(region, HEAD_NURSE));
    }


    @Authorize(role = USER, professions = {DOCTOR})
    @PostMapping("/patient/change")
    public ResponseEntity<?> changePatientInfo(@RequestBody PatientChangeRequest request) {
        final User currUser = subject.getUser();
        final Patient patient = patientService.
                changePatientInfo(request.getId(), request.getCondition(), request.getState());
        log.info("User {} change info of Patient {}", currUser.getUsername(), request.getId());
        return ResponseEntity.ok(patient);
    }

    @Authorize(role = USER, professions = {DOCTOR})
    @PostMapping("/patient/test")
    public ResponseEntity<?> testNucleicAcid(@RequestBody NucleicAcidTestRequest request) {
        final User currUser = subject.getUser();
        final Doctor doctor = professionService.getDoctor(currUser);
        final NucleicAcidTest test = patientService.testPatient(doctor, request);
        if (test != null) {
            log.info("Doctor {} test Patient {}",
                    subject.getUser().getUsername(), request.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Authorize(role = USER, professions = {DOCTOR})
    @PostMapping("/patient/discharge")
    public ResponseEntity<?> dischargePatient(@RequestBody MereIdRequest request) {
        final PatientChangeRequest changeRequest = new PatientChangeRequest();
        changeRequest.setId(request.getId());
        changeRequest.setState(State.CURED);
        log.info("User {} discharge Patient {}",
                subject.getUser().getUsername(), request.getId());
        return changePatientInfo(changeRequest);
    }


}
