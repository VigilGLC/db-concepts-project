package fd.se.dbconcepts_project.service;

import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.medic.Doctor;
import fd.se.dbconcepts_project.entity.medic.HeadNurse;
import fd.se.dbconcepts_project.entity.medic.WardNurse;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.DoctorRepository;
import fd.se.dbconcepts_project.repository.HeadNurseRepository;
import fd.se.dbconcepts_project.repository.UserRepository;
import fd.se.dbconcepts_project.repository.WardNurseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static fd.se.dbconcepts_project.entity.consts.Profession.WARD_NURSE;

@Slf4j
@AllArgsConstructor
@Service
public class ProfessionService {

    private final DoctorRepository doctorRepository;
    private final WardNurseRepository wardNurseRepository;
    private final HeadNurseRepository headNurseRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final PatientService patientService;


    public Doctor getDoctor(User user) {
        return doctorRepository.findByUser(user);
    }

    public HeadNurse getHeadNurse(User user) {
        return headNurseRepository.findByUser(user);
    }

    public WardNurse getWardNurse(User user) {
        return wardNurseRepository.findByUser(user);
    }

    public Doctor getRegionDoctor(Region region) {
        return doctorRepository.findByUser(
                userService.getUserByRegionAndProfession(region, Profession.DOCTOR));
    }

    public HeadNurse getRegionHeadNurse(Region region) {
        return headNurseRepository.
                findByUser(userService.getUserByRegionAndProfession(region, Profession.HEAD_NURSE));
    }

    public List<User> getWardNurseUsers(Region region) {
        return userRepository.findUserByMedicProfessionAndMedicRegion(WARD_NURSE, region);
    }

    public List<User> getSpareWardNurseUsers() {
        return getWardNurseUsers(null);
    }


    @Transactional
    public boolean deleteWardNurse(int id) {
        final WardNurse wardNurse = wardNurseRepository.findById(id);
        if (wardNurse.getPatients().isEmpty()) {
            wardNurse.setRegion(null);
            wardNurseRepository.save(wardNurse);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addWardNurse(HeadNurse headNurse, int id) {
        WardNurse wardNurse = wardNurseRepository.findById(id);
        if (wardNurse.getRegion() == null) {
            wardNurse.setRegion(headNurse.getRegion());
            wardNurse = wardNurseRepository.save(wardNurse);
            patientService.rearrangePatients(wardNurse.getRegion());
            return true;
        }
        return false;
    }


}
