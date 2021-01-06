package fd.se.dbconcepts_project.pojo.request.wardnurse;


import fd.se.dbconcepts_project.entity.consts.Result;
import fd.se.dbconcepts_project.entity.consts.State;
import fd.se.dbconcepts_project.entity.patient.InfoRegistration;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationReportRequest {

    private int id; // patient id

    private LocalDate date;
    private double temperature;
    private String symptom;
    private State state;
    private Result result;


    public InfoRegistration toInfoRegistration() {
        final InfoRegistration registration = new InfoRegistration();
        registration.setDate(date);
        registration.setTemperature(temperature);
        registration.setSymptom(symptom);
        registration.setState(state);
        registration.setResult(result);
        return registration;
    }

}
