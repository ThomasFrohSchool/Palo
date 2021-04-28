package coms309.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTable extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    //@Query("SELECT * FROM user WHERE username LIKE ")
    List<User> findByUsernameLike(String username);
    User findById(int id);
    void deleteById(int id);
}
