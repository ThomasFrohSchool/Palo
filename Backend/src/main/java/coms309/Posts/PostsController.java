package coms309.Posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import top.jfunc.json.impl.JSONArray;
import top.jfunc.json.impl.JSONObject;
import coms309.Users.User;
import coms309.Users.UserTable;

import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;  

@Api(value = "PostsController", description = "REST API containing endpoints for CRUDing posts")
@RestController
public class PostsController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    PostsTable postsTable;
    @Autowired
    UserTable userTable;

    @Autowired
    CommentsTable commentsTable;

    /**
     * 
     * @param post JSON object representation of a post
     * @return Failure or success message
     */
    @ApiOperation(value = "Create a new post")
    @PostMapping(path = "/createPost/{userID}")
    String createPost(@PathVariable int userID, @ApiParam(value="JSON post object",required=true) @RequestBody Posts post){
        if (post == null)
            return failure;
        post.setUser(userTable.findById(userID));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        post.setCreateDate(dtf.format(now));
        post.getUser().addPosts(post);
        postsTable.save(post);
        userTable.save(post.getUser());
        return success;
    }
    @ApiOperation(value = "List of all posts by all users")
    @GetMapping(path = "/posts")
    List<Posts> getAllPosts(){
        return postsTable.findAll();
    }
    @ApiOperation(value = "List posts for a specific user")
    @GetMapping(path = "/posts/{userID}")
    public List<Posts> getUserPosts(@PathVariable("userID") int userID){
        return userTable.findById(userID).getPosts();
    }
    
    @ApiOperation(value = "Get posts of a users following list")
    @GetMapping(path = "/feed/{userID}")
    public List<Posts> getFeedPosts(@PathVariable("userID") int userID){
        User user = userTable.findById(userID);
        if(user == null){
            return null;
        }
        List<Integer>following = user.getFollowing();
        List<Posts> p = new ArrayList<Posts>();
        for(Integer i : following){
            User f = userTable.findById(i);
            for(Posts ps : f.getPosts()){
                p.add(ps);
            }
        }
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        p.sort((e1, e2) -> {
            try {
                return formatter1.parse(e1.getCreateDate()).compareTo(formatter1.parse(e2.getCreateDate()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return 1;
            }
        });
        return p;
    }



    //COMMENTS
    @ApiOperation(value = "Create a new comment")
    @PostMapping(path = "/createComment/{postID}")
    String createComment(@PathVariable int postID, @ApiParam(value="JSON comment object",required=true) @RequestBody Comments comment){
        if (comment == null)
            return failure;
        comment.setPosts(postsTable.findById(postID));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        comment.setCreateDate(dtf.format(now));
        comment.getUser().addComment(comment);
        commentsTable.save(comment);
        postsTable.save(comment.getPosts());
        return success;
    }


    @ApiOperation(value = "List comments for a specific post")
    @GetMapping(path = "/posts/getcomments/{postID}")
    public String getComments(@PathVariable("postID") int postID){
        List<Comments> comList = postsTable.findById(postID).getComments();

        JSONArray arr = new JSONArray();

        
        for(int i = 0; i < comList.size(); i++){
            JSONObject toAdd = new JSONObject();
            toAdd.put("body", comList.get(i).getBody());
            toAdd.put("user_id", comList.get(i).getUser_id());
            toAdd.put("createDate", comList.get(i).getCreateDate());

            arr.put(toAdd);
        }


        return arr.toString();
    }
}