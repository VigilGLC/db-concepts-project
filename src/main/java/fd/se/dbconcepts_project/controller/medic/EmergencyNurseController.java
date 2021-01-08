package fd.se.dbconcepts_project.controller.medic;


import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.hospital.WardBed;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.interceptor.Subject;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.emergencynurse.PatientEnrollRequest;
import fd.se.dbconcepts_project.pojo.request.patient.PatientFilterRequest;
import fd.se.dbconcepts_project.pojo.response.PatientsResponse;
import fd.se.dbconcepts_project.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fd.se.dbconcepts_project.entity.consts.Profession.EMERGENCY_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user/medic/emergencynurse")
public class EmergencyNurseController {

    private final PatientService patientService;
    private final Subject subject;

    @Authorize(role = USER, professions = {EMERGENCY_NURSE})
    @PostMapping("/patient/enroll")
    public ResponseEntity<?> enrollPatient(@RequestBody PatientEnrollRequest request) {
        final User currUser = subject.getUser();
        final Patient patient = patientService.createPatient(request);
        final WardBed wardBed = patientService.arrangePatient(patient);
        if (wardBed != null) {
            log.info("User {} arrange for Patient {} Success at {}",
                    currUser.getUsername(), patient.getId(), wardBed.getId());
            return ResponseEntity.ok().build();
        }
        log.info("User {} arrange for Patient {} Fail",
                currUser.getUsername(), patient.getId());
        return ResponseEntity.noContent().build();
    }

    @Authorize(role = USER, professions = {EMERGENCY_NURSE})
    @PostMapping("/patients")
    public ResponseEntity<?> getPatients(@RequestBody PatientFilterRequest request) {
        final Region region = request.getRegion();
        final Condition condition = request.getCondition();
        final State state = request.getState();
        final User currUser = subject.getUser();
        final List<Patient> statedPatients = patientService.
                getAllPatientsBy(region, condition, state);
        log.info("User {} get all patients.", currUser.getUsername());
        return ResponseEntity.ok(new PatientsResponse(statedPatients));
    }


}
