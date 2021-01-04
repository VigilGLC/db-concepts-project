package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;
import static fd.se.dbconcepts_project.entity.consts.Constants.STRING_NEGATIVE;
import static fd.se.dbconcepts_project.entity.consts.Constants.STRING_POSITIVE;

public enum Result {

    POSITIVE(STRING_POSITIVE),
    NEGATIVE(STRING_NEGATIVE);

    public final String value;


    Result(String value) {
        this.value = value;
    }


    private final static HashMap<String, Result> valueMap;

    static {
        valueMap = new HashMap<>(Result.values().length);
        for (Result value : Result.values()) {
            valueMap.put(value.value, value);
        }
    }

    @JsonCreator(mode = DELEGATING)
    public static Result of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
