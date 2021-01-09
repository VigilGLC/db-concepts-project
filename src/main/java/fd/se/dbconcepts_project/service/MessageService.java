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
        message.setPatientId(patient.getId());

        String content = "";
        if (type == MessageType.DISCHARGEABLE_NOTIFY) {
            content = "Message: Patient " + patient.getId() + " int Region " + region + " Dischargeable. ";
        } else if (type == MessageType.TRANSFERRED_NOTIFY) {
            content = "Message: Patient " + patient.getId() + " already Transferred to Region " + region + ". ";
        }
        message.setContent(content);

        message.setTime(LocalDateTime.now());
        message = messageRepository.save(message);
        log.info("Message {} send to User {}", message.getId(), receiver.getUser().getUsername());
    }


}
