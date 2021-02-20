package coms309;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTable extends JpaRepository<User, Long> {
    User findById(int id);
    void deleteById(int id);
}
