package coms309;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserTable extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    List<User> findById(int id);
    void deleteById(int id);
}
