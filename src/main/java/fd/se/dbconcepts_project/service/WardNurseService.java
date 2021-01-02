package fd.se.dbconcepts_project.service;

import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static fd.se.dbconcepts_project.entity.consts.Profession.WARD_NURSE;

@Service
@AllArgsConstructor
public class WardNurseService {

    private final UserRepository userRepository;

    @Transactional
    public List<User> getWardNurseUsers(Region region) {
        return userRepository.findUserByMedicProfessionAndMedicRegion(WARD_NURSE, region);
    }


}
