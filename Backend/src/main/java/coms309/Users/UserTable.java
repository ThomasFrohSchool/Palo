package coms309.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserTable extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    User findById(int id);
    void deleteById(int id);
}
