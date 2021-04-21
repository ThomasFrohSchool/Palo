package coms309.DMs;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTable extends JpaRepository<Message, Long>{
    List<Message> findByfromUser(String username);
    List<Message> findBytoUser(String username);
}
