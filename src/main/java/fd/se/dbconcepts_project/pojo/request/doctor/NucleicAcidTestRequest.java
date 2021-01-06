package fd.se.dbconcepts_project.pojo.request.doctor;


import fd.se.dbconcepts_project.entity.consts.Condition;
import fd.se.dbconcepts_project.entity.consts.Result;
import fd.se.dbconcepts_project.entity.patient.NucleicAcidTest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NucleicAcidTestRequest {

    private int id; // patient id

    private LocalDate date;
    private Condition condition;
    private Result result;


    public NucleicAcidTest toNucleicAcidTest() {
        final NucleicAcidTest test = new NucleicAcidTest();
        test.setDate(date);
        test.setResult(result);
        test.setCondition(condition);
        return test;
    }

}
