package coms309.Posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsTable extends JpaRepository<Posts, Long> {
    Posts findById(int id);
}
