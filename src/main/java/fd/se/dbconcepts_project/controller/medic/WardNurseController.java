package fd.se.dbconcepts_project.controller.medic;

import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.patient.InfoRegistration;
import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.interceptor.Subject;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.patient.PatientFilterRequest;
import fd.se.dbconcepts_project.pojo.request.wardnurse.RegistrationReportRequest;
import fd.se.dbconcepts_project.pojo.response.PatientsResponse;
import fd.se.dbconcepts_project.service.PatientService;
import fd.se.dbconcepts_project.service.ProfessionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static fd.se.dbconcepts_project.entity.consts.Profession.WARD_NURSE;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user/medic/wardnurse")
public class WardNurseController {

    private final PatientService patientService;
    private final ProfessionService professionService;
    private final Subject subject;


    @Authorize(role = USER, professions = {WARD_NURSE})
    @PostMapping("/patients/condition")
    public ResponseEntity<?> getAllPatients(@RequestBody PatientFilterRequest request) {
        final Condition condition = request.getCondition();
        final User currUser = subject.getUser();
        final WardNurse wardNurse = professionService.getWardNurse(currUser);
        final List<Patient> statedPatients = patientService.
                getWardNursePatientsByCondition(wardNurse, condition);
        log.info("WardNurse {} get all patients.", currUser.getUsername());
        return ResponseEntity.ok(new PatientsResponse(statedPatients));
    }

    @Authorize(role = USER, professions = {WARD_NURSE})
    @GetMapping("/patients/canDischarge")
    public ResponseEntity<?> getAllPatientsCanDisCharge() {
        final User currUser = subject.getUser();
        final List<Patient> patients =
                patientService.getWardNursePatientsCanDischarge(
                        currUser.getId());
        log.info("WardNurse {} get patients can discharge.", currUser.getUsername());
        return ResponseEntity.ok(new PatientsResponse(patients));
    }

    @Authorize(role = USER, professions = {WARD_NURSE})
    @PostMapping("/patient/register")
    public ResponseEntity<?> reportRegistration(
            @RequestBody RegistrationReportRequest request) {
        final User currUser = subject.getUser();
        final WardNurse wardNurse = professionService.getWardNurse(currUser);
        final InfoRegistration registration = patientService.registerPatient(wardNurse, request);
        if (registration != null) {
            log.info("WardNurse {} register Patient {}",
                    subject.getUser().getUsername(), request.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Authorize(role = USER, professions = {WARD_NURSE})
    @GetMapping("/patient/test")
    public ResponseEntity<?> checkNucleicAcidTest(@RequestParam int id,
                                                  @RequestParam
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        final User currUser = subject.getUser();
        final NucleicAcidTest test = patientService.getTestForPatientByDate(id, date);
        if (test != null) {
            log.info("WardNurse {} get date's {} test for Patient {} Success.",
                    currUser.getUsername(), id, date);
            return ResponseEntity.ok(test);
        }
        log.info("WardNurse {} get date's {} test for Patient {} Failed.",
                currUser.getUsername(), id, date);
        return ResponseEntity.noContent().build();
    }

}
