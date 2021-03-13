package coms309.Posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiParam;

@Api(value = "PostsController", description = "REST API containing endpoints for CRUDing posts")
@RestController
public class PostsController {

    private String success = "\"message\":\"success\"";
    private String failure = "\"message\":\"failure\"";

    @Autowired
    PostsTable postsTable;

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
    postsTable.save(post);
        return success;
    }
}