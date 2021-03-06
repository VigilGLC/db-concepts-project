package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;
import static fd.se.dbconcepts_project.entity.consts.Constants.*;

public enum State {
    CURED(STRING_CURED),
    TREATED(STRING_TREATED),
    DIED(STRING_DIED);

    public final String value;

    State(String value) {
        this.value = value;
    }

    private final static HashMap<String, State> valueMap;

    static {
        valueMap = new HashMap<>(State.values().length);
        for (State value : State.values()) {
            valueMap.put(value.value, value);
        }
    }

    @JsonCreator(mode = DELEGATING)
    public static State of(String value) {
        return valueMap.getOrDefault(value, null);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
