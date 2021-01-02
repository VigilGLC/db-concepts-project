package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum Gender {
    FEMALE("female"),
    MALE("male");

    public final String value;

    Gender(String value) {
        this.value = value;
    }

    private final static HashMap<String, Gender> valueMap;

    static {
        valueMap = new HashMap<>(Gender.values().length);
        for (Gender value : Gender.values()) {
            valueMap.put(value.value, value);
        }
    }

    @JsonCreator
    public static Gender of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
