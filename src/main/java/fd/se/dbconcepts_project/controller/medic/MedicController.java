package fd.se.dbconcepts_project.controller.medic;


import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.medic.MedicBase;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.interceptor.Subject;
import fd.se.dbconcepts_project.interceptor.authorize.Authorize;
import fd.se.dbconcepts_project.pojo.request.MereIdRequest;
import fd.se.dbconcepts_project.pojo.response.PatientsResponse;
import fd.se.dbconcepts_project.pojo.response.UsersResponse;
import fd.se.dbconcepts_project.service.MessageService;
import fd.se.dbconcepts_project.service.PatientService;
import fd.se.dbconcepts_project.service.WardNurseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fd.se.dbconcepts_project.entity.consts.Profession.*;
import static fd.se.dbconcepts_project.entity.consts.Role.USER;

@RestController
@RequestMapping("/user/medic")
@AllArgsConstructor
@Slf4j
public class MedicController {

    private final MessageService messageService;
    private final WardNurseService wardNurseService;
    private final PatientService patientService;

    private final Subject subject;

    @Authorize(role = USER)
    @GetMapping("/messages")
    public ResponseEntity<?> checkMessages() {
        final User currUser = subject.getUser();
        log.info("User {} get all messages.", currUser.getUsername());
        return ResponseEntity.ok(messageService.getAllMessages(currUser));
    }

    @Authorize(role = USER)
    @PostMapping("/messages/delete")
    public ResponseEntity<?> deleteMessage(@RequestBody MereIdRequest request) {
        final User currUser = subject.getUser();
        messageService.deleteMessage(request.getId());
        log.info("User {} delete Message {}.", currUser.getUsername(), request.getId());
        return ResponseEntity.ok().build();
    }


    @Authorize(role = USER, professions = {DOCTOR, HEAD_NURSE})
    @GetMapping("/wardnurses")
    public ResponseEntity<?> getWardNurses() {
        final User currUser = subject.getUser();
        final MedicBase currMedic = currUser.getMedic();
        final List<User> wardNurseUsers = wardNurseService.getWardNurseUsers(currMedic.getRegion());
        log.info("User {} get wardnurses.", currUser.getUsername());
        return ResponseEntity.ok(new UsersResponse(wardNurseUsers));
    }

    @Authorize(role = USER, professions = {DOCTOR, HEAD_NURSE})
    @GetMapping("/wardnurse/patients")
    public ResponseEntity<?> getWardNursePatients(@RequestParam int id) {
        final List<Patient> wardNursePatients = patientService.getWardNursePatients(id);
        log.info("User {} get wardnurse's patients.", subject.getUser().getUsername());
        return ResponseEntity.ok(new PatientsResponse(wardNursePatients));
    }

    @Authorize(role = USER, professions = {DOCTOR, HEAD_NURSE})
    @GetMapping("/patients")
    public ResponseEntity<?> getPatients(@RequestParam State state) {
        final User currUser = subject.getUser();
        final List<Patient> statedPatients = patientService.
                getAllPatientsByState(currUser.getMedic().getRegion(), null, state);
        log.info("User {} get all patients.", currUser.getUsername());
        return ResponseEntity.ok(new PatientsResponse(statedPatients));
    }

    @Authorize(role = USER, professions = {DOCTOR, HEAD_NURSE, EMERGENCY_NURSE, WARD_NURSE})
    @GetMapping("/patient")
    public ResponseEntity<?> getPatient(@RequestParam int id) {
        final Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @Authorize(role = USER, professions = {DOCTOR, HEAD_NURSE})
    @GetMapping("/patients/canDischarge")
    public ResponseEntity<?> getAllPatientsCanDisCharge() {
        final User currUser = subject.getUser();
        final List<Patient> patients =
                patientService.getPatientsCanDischarge(
                        currUser.getMedic().getRegion());
        return ResponseEntity.ok(patients);
    }

    @Authorize(role = USER, professions = {DOCTOR, HEAD_NURSE})
    @GetMapping("/patients/canTransfer")
    public ResponseEntity<?> getAllPatientsCanTransfer() {
        final User currUser = subject.getUser();
        final List<Patient> patients =
                patientService.getPatientsRegionNotMatchCondition(
                        currUser.getMedic().getRegion());
        return ResponseEntity.ok(patients);
    }


}
