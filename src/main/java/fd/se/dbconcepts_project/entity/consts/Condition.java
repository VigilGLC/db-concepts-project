package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum Condition {
    MILD("mild"),
    SEVERE("severe"),
    CRITICAL("critical");

    public final String value;

    Condition(String value) {
        this.value = value;
    }

    private final static HashMap<String, Condition> valueMap;

    static {
        valueMap = new HashMap<>(Condition.values().length);
        for (Condition value : Condition.values()) {
            valueMap.put(value.value, value);
        }
    }

    @JsonCreator
    public static Condition of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
