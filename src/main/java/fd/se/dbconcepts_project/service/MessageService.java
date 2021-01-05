package fd.se.dbconcepts_project.service;


import fd.se.dbconcepts_project.entity.consts.MessageType;
import fd.se.dbconcepts_project.entity.consts.Region;
import fd.se.dbconcepts_project.entity.medic.MedicBase;
import fd.se.dbconcepts_project.entity.patient.Patient;
import fd.se.dbconcepts_project.entity.system.Message;
import fd.se.dbconcepts_project.entity.usr.User;
import fd.se.dbconcepts_project.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.LocalDateTime;
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


    @Transactional
    public void createMessage(MessageType type, MedicBase receiver, Patient patient, Region region) {
        Message message = new Message();
        message.setReceiver(receiver);
        message.setContent(MessageFormat.format(type.format, patient.getId(), region));
        message.setTime(LocalDateTime.now());
        message = messageRepository.save(message);
        log.info("Message {} send to User {}", message.getId(), receiver.getUser().getUsername());
    }


}
