package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;

public enum Region {
    MILD("mild"),
    SEVERE("severe"),
    CRITICAL("critical");

    public final String value;

    Region(String value) {
        this.value = value;
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
