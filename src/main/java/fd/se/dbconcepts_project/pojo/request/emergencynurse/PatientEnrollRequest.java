package fd.se.dbconcepts_project.pojo.request.emergencynurse;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientEnrollRequest {
    @Data
    public static class PatientInfo {
        private String name;
        private String gender;
        private String address;
        private String region;
    }

    @Data
    public static class TestInfo {
        public LocalDate date;
        public double temperature;
        public String symptom;
        public String state;
    }


    private PatientInfo patientInfo;
    private TestInfo nucleicAcidRTest;
}
