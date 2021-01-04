package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;
import static fd.se.dbconcepts_project.entity.consts.Constants.*;

public enum Profession {

    DOCTOR(STRING_DOCTOR),
    HEAD_NURSE(STRING_HEAD_NURSE),
    WARD_NURSE(STRING_WARD_NURSE),
    EMERGENCY_NURSE(STRING_EMERGENCY_NURSE);


    public final String value;

    Profession(String value) {
        this.value = value;
    }

    private final static HashMap<String, Profession> valueMap;

    static {
        valueMap = new HashMap<>(Profession.values().length);
        for (Profession value : Profession.values()) {
            valueMap.put(value.value, value);
        }
    }

    @JsonCreator(mode = DELEGATING)
    public static Profession of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
