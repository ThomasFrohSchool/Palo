package coms309;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsTable extends JpaRepository<Posts, Long> {

}
