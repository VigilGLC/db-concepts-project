package fd.se.dbconcepts_project.pojo.response;

import fd.se.dbconcepts_project.entity.system.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessagesResponse {

    private List<Message> messages;

}


