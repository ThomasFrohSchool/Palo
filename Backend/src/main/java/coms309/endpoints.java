package coms309;

import java.sql.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import coms309.User;
import coms309.Posts;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
class WelcomeController {

    @Autowired
    UserTable userTable;
    @Autowired
    PostsTable postsTable;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }
 
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userTable.findAll();
    }

    @PostMapping(path = "/addUser")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        userTable.save(user);
        return success;
    }

    @PostMapping(path = "/login")
    User login(@RequestBody User request){
        List<User> myuser = userTable.findByUsername(request.getUsername());
        if (myuser.size() == 0 || !(myuser.get(0).getPassword().equals(request.getPassword())))
            return null;
        return myuser.get(0);
    }


    //FOR POSTS
    @PostMapping(path = "/createPost")
    String createPost(@RequestBody Posts post){
        if (post == null)
            return failure;
    postsTable.save(post);
        return success;
    }



    @GetMapping("/exp1/wow")
    public String uniWow(HttpServletRequest req){
	return "~~~UNI-WOW~~~<br> You're accessing this site from: " + req.getRemoteAddr();
    }
}
