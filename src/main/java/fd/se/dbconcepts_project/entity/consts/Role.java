package fd.se.dbconcepts_project.entity.consts;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

    ANY("null"),
    USER("user"),
    ADMIN("admin");

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
