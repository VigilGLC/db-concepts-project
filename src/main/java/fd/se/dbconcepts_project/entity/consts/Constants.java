package fd.se.dbconcepts_project.entity.consts;

public class Constants {

    private Constants() {
    }


    public static final String STRING_MILD = "mild";
    public static final String STRING_SEVERE = "severe";
    public static final String STRING_CRITICAL = "critical";
    public static final String STRING_MALE = "male";
    public static final String STRING_FEMALE = "female";
    public static final String STRING_DOCTOR = "doctor";
    public static final String STRING_HEAD_NURSE = "headnurse";
    public static final String STRING_WARD_NURSE = "wardnurse";
    public static final String STRING_EMERGENCY_NURSE = "emergencynurse";
    public static final String STRING_ADMIN = "admin";
    public static final String STRING_USER = "user";
    public static final String STRING_ANY = "null";
    public static final String STRING_CURED = "cured";
    public static final String STRING_TREATED = "treated";
    public static final String STRING_DIED = "died";

    public static final int MILD_PATIENTS_MAX = 3;
    public static final int SEVERE_PATIENTS_MAX = 2;
    public static final int CRITICAL_PATIENTS_MAX = 1;


    public static final int MILD_WARDBEDS_MAX = 4;
    public static final int SEVERE_WARDBEDS_MAX = 2;
    public static final int CRITICAL_WARDBEDS_MAX = 1;

    public static final String STRING_POSITIVE = "positive";
    public static final String STRING_NEGATIVE = "negative";

    public static final int TEST_CHECK_LIMIT = 2;
    public static final int REGISTER_CHECK_LIMIT = 3;
    public static final double TEMPERATURE_BORDER = 37.3;


    public static final String DISCHARGEABLE_NOTIFY_FORMAT =
            "Message: Patient {} int Region {} Dischargeable. ";

    public static final String TRANSFERRED_NOTIFY_FORMAT =
            "Message: Patient {} already Transferred to Region {}. ";
}
