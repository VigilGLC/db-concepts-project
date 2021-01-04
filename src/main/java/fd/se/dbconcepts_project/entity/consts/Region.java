package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;
import static fd.se.dbconcepts_project.entity.consts.Constants.*;

public enum Region {
    MILD(STRING_MILD, MILD_PATIENTS_MAX, MILD_WARDBEDS_MAX),
    SEVERE(STRING_SEVERE, SEVERE_PATIENTS_MAX, SEVERE_WARDBEDS_MAX),
    CRITICAL(STRING_CRITICAL, CRITICAL_PATIENTS_MAX, CRITICAL_WARDBEDS_MAX);

    public final String value;

    public final int patientsMax;
    public final int wardBedsMax;


    Region(String value, int patientsMax, int wardBedsMax) {
        this.value = value;
        this.patientsMax = patientsMax;
        this.wardBedsMax = wardBedsMax;
    }


    private final static HashMap<String, Region> valueMap;

    static {
        valueMap = new HashMap<>(Region.values().length);
        for (Region value : Region.values()) {
            valueMap.put(value.value, value);
        }
    }

    @JsonCreator(mode = DELEGATING)
    public static Region of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
