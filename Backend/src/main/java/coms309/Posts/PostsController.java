package coms309.Posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiParam;
import coms309.Users.UserTable;

@Api(value = "PostsController", description = "REST API containing endpoints for CRUDing posts")
@RestController
public class PostsController {

    private String success = "\"message\":\"success\"";
    private String failure = "\"message\":\"failure\"";

    @Autowired
    PostsTable postsTable;
    @Autowired
    UserTable userTable;

    /**
     * 
     * @param post JSON object representation of a post
     * @return Failure or success message
     */
    @ApiOperation(value = "Create a new post")
    @PostMapping(path = "/createPost")
    String createPost(@ApiParam(value="JSON post object",required=true) @RequestBody Posts post){
        if (post == null)
            return failure;
        Posts toSave = new Posts(post.getDescription(),post.getType(),post.getSpot_link());
        toSave.setUser(userTable.findById(post.gettempID()).get(0));
        postsTable.save(toSave);
        return success;
    }
    @ApiOperation(value = "List of all posts by all users")
    @GetMapping(path = "/posts")
    List<Posts> getAllPosts(){
        return postsTable.findAll();
    }
}