package coms309.DMs;


import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTable extends JpaRepository<Message, Long>{

}
