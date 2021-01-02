package fd.se.dbconcepts_project.repository;

import fd.se.dbconcepts_project.entity.system.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findByReceiverIdOrderByTimeAsc(int id);

    void deleteById(int id);
}
