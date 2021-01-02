package fd.se.dbconcepts_project.pojo.response;

import fd.se.dbconcepts_project.entity.hospital.Ward;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class WardsResponse {
    List<Ward> wards;
}
