package fd.se.dbconcepts_project.service;

import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.medic.Doctor;
import fd.se.dbconcepts_project.entity.medic.HeadNurse;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.DoctorRepository;
import fd.se.dbconcepts_project.repository.HeadNurseRepository;
import fd.se.dbconcepts_project.repository.WardNurseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ProfessionService {

    private final DoctorRepository doctorRepository;
    private final WardNurseRepository wardNurseRepository;

    private final HeadNurseRepository headNurseRepository;

    private final UserService userService;


    public Doctor getDoctor(User user) {
        return doctorRepository.findByUser(user);
    }

    public HeadNurse getHeadNurse(User user) {
        return headNurseRepository.findByUser(user);
    }


    public Doctor getRegionDoctor(Region region) {
        return doctorRepository.findByUser(
                userService.getUserByRegionAndProfession(region, Profession.DOCTOR));
    }

    public HeadNurse getRegionHeadNurse(Region region) {
        return headNurseRepository.
                findByUser(userService.getUserByRegionAndProfession(region, Profession.HEAD_NURSE));
    }


}
