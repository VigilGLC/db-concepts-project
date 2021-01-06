package fd.se.dbconcepts_project.pojo.response;


import fd.se.dbconcepts_project.entity.hospital.WardBed;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WardBedsResponse {

    private List<WardBed> wardBeds;

}
