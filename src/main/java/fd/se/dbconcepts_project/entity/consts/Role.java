package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonValue;

import static fd.se.dbconcepts_project.entity.consts.Constants.*;

public enum Role {

    ANY(STRING_ANY),
    USER(STRING_USER),
    ADMIN(STRING_ADMIN);

    public final String value;

    Role(String value) {
        this.value = value;
    }


    @JsonValue
    @Override
    public String toString() {
        return super.toString();
    }
}
