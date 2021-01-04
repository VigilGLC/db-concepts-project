package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;
import static fd.se.dbconcepts_project.entity.consts.Constants.*;
import static fd.se.dbconcepts_project.entity.consts.Constants.STRING_MALE;

public enum Gender {
    FEMALE(STRING_FEMALE),
    MALE(STRING_MALE);

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

    @JsonCreator(mode = DELEGATING)
    public static Gender of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
