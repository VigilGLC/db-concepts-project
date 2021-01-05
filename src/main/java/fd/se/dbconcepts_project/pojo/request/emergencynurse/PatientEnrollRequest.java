package fd.se.dbconcepts_project.pojo.request.emergencynurse;


import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Gender;
import fd.se.dbconcepts_project.entity.consts.Result;
import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import fd.se.dbconcepts_project.entity.patient.Patient;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientEnrollRequest {
    @Data
    public static class PatientInfo {
        private String name;
        private Gender gender;
        private String address;


        public Patient toPatient() {
            final Patient patient = new Patient();
            patient.setName(name);
            patient.setGender(gender);
            patient.setAddress(address);
            return patient;
        }

    }

    @Data
    public static class TestInfo {
        public LocalDate date;
        public Result result;
        public Condition condition;

        public NucleicAcidTest toNucleicAcidTest() {
            final NucleicAcidTest test = new NucleicAcidTest();
            test.setDate(date);
            test.setResult(result);
            test.setCondition(condition);
            return test;
        }

    }


    private PatientInfo patientInfo;
    private TestInfo testInfo;
}
