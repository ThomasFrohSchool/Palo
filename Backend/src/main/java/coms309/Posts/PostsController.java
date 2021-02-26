package coms309.Posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {

    private String success = "\"message\":\"success\"";
    private String failure = "\"message\":\"failure\"";

    @Autowired
    PostsTable postsTable;

    //FOR POSTS
    @PostMapping(path = "/createPost")
    String createPost(@RequestBody Posts post){
        if (post == null)
            return failure;
    postsTable.save(post);
        return success;
    }
}