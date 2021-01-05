package fd.se.dbconcepts_project.entity.consts;

import static fd.se.dbconcepts_project.entity.consts.Constants.DISCHARGEABLE_NOTIFY_FORMAT;
import static fd.se.dbconcepts_project.entity.consts.Constants.TRANSFERRED_NOTIFY_FORMAT;

public enum MessageType {

    DISCHARGEABLE_NOTIFY(DISCHARGEABLE_NOTIFY_FORMAT),
    TRANSFERRED_NOTIFY(TRANSFERRED_NOTIFY_FORMAT);

    public final String format;


    MessageType(String format) {
        this.format = format;
    }
}
