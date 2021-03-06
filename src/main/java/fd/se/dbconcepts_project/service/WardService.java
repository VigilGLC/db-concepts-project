package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.hospital.Ward;
import fd.se.dbconcepts_project.entity.hospital.WardBed;
import fd.se.dbconcepts_project.repository.WardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class WardService {

    private final WardRepository wardRepository;
    private final PatientService patientService;

    @Transactional
    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    @Transactional
    public List<Ward> getAllWardsByRegion(Region region) {
        return wardRepository.findAllByRegion(region);
    }

    @Transactional
    public Ward addWard(Region region) {
        final Ward ward = new Ward();
        ward.setRegion(region);
        return wardRepository.save(ward);
    }


    @Transactional
    public Ward addWardBed(int wardId) {
        final WardBed wardBed = new WardBed();
        Ward ward = wardRepository.findById(wardId);
        if (ward.getWardBeds().size() >= ward.getRegion().wardBedsMax) {
            return null;
        }
        ward.getWardBeds().add(wardBed);
        wardBed.setWard(ward);
        ward = wardRepository.save(ward);
        patientService.rearrangePatients(ward.getRegion());
        return ward;
    }

    @Transactional
    public List<WardBed> getWardBeds(int wardId) {
        return wardRepository.findById(wardId).getWardBeds();
    }

}
