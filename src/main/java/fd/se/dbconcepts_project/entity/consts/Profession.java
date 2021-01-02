package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum Profession {


    DOCTOR("doctor"),
    HEAD_NURSE("headnurse"),
    WARD_NURSE("wardnurse"),
    EMERGENCY_NURSE("emergencynurse");


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

    @JsonCreator
    public static Profession of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
