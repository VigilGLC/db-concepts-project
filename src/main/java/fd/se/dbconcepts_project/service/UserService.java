package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.Profession;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.medic.MedicBase;
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
        user.setPassword(encryptUtils.encrypt(request.getPassword()));
        user.setGender(request.getGender());
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
        final MedicBase medic = new MedicBase();
        medic.setId(user.getId());
        user.setMedic(medic);
        medic.setRegion(request.getRegion());
        medic.setProfession(request.getProfession());
        user = userRepository.save(user);
        return user.getMedic();
    }

    public User getUserByRegionAndProfession(Region region, Profession profession) {
        return userRepository.findByMedicRegionAndMedicProfession(region, profession);
    }


}
