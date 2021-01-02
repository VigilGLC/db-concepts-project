package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.system.Message;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public List<Message> getAllMessages(User user) {
        return messageRepository.findByReceiverIdOrderByTimeAsc(user.getId());
    }

    @Transactional
    public void deleteMessage(int id) {
        messageRepository.deleteById(id);
    }


}
