package fd.se.dbconcepts_project.pojo.request.doctor;


import lombok.Data;

@Data
public class PatientChangeRequest {

    private int id;

    private String condition;
    private String state;

}
