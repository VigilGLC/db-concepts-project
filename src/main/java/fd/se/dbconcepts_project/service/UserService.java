package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.consts.Role;
import fd.se.dbconcepts_project.entity.medic.*;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.pojo.request.account.ProfileChangeRequest;
import fd.se.dbconcepts_project.pojo.request.account.SignInRequest;
import fd.se.dbconcepts_project.pojo.request.account.SignUpRequest;
import fd.se.dbconcepts_project.pojo.request.admin.ProfessionAssignRequest;
import fd.se.dbconcepts_project.repository.UserRepository;
import fd.se.dbconcepts_project.utils.EncryptUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EncryptUtils encryptUtils;

    @Transactional
    public boolean checkUnique(String username) {
        return null == userRepository.findByUsername(username);
    }

    @Transactional
    public User createUser(SignUpRequest request) {
        final User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(encryptUtils.encrypt(request.getPassword()));
        user.setGender(request.getGender());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User validateUser(SignInRequest request) {
        final User user = userRepository.findByUsername(request.getUsername());
        if (user == null) return null;
        if (encryptUtils.validate(request.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

    @Transactional
    public User changeProfile(User user, ProfileChangeRequest request) {
        if (request.getPassword() != null) {
            user.setPassword(encryptUtils.encrypt(request.getPassword()));
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        return userRepository.save(user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public MedicBase assignProfession(ProfessionAssignRequest request) {
        User user = userRepository.findById(request.getId());
        if (user == null) return null;
        final MedicBase medic;
        switch (request.getProfession()) {
            case DOCTOR:
                medic = new Doctor();
                break;
            case HEAD_NURSE:
                medic = new HeadNurse();
                break;
            case WARD_NURSE:
                medic = new WardNurse();
                break;
            case EMERGENCY_NURSE:
                medic = new EmergencyNurse();
                break;
            default:
                medic = new MedicBase();
        }
        medic.setId(user.getId());
        medic.setUser(user);
        medic.setRegion(request.getRegion());
        medic.setProfession(request.getProfession());

        user.setMedic(medic);
        user = userRepository.save(user);
        return user.getMedic();
    }

    public User getUserByRegionAndProfession(Region region, Profession profession) {
        return userRepository.findByMedicRegionAndMedicProfession(region, profession);
    }


}
