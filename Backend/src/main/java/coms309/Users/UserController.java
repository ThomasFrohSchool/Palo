package coms309.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "UserController", description = "REST API containing endpoints for CRUDing users")
@RestController
public class UserController {

    private String success = "\"message\":\"success\"";
    private String failure = "\"message\":\"failure\"";

    @Autowired
    UserTable userTable;

    /**
     * 
     * @return Returns a JSON list containing all registered users
     */
    @ApiOperation(value = "Get list of all users")
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userTable.findAll();
    }
    /**
     * 
     * @param user JSON object representation of a user to be registered
     * @return JSON response body containing information about the status and user
     */
    @ApiOperation(value = "Create and register a new user")
    @PostMapping(path = "/register")
    String createUser(@ApiParam(value="JSON user object",required=true) @RequestBody User user){
        if (user == null)
            return "{ \"error\":\"true\","+failure+",\"user\":"+user+"}";
        try {
            userTable.save(user);
        } catch (Exception e){
            return "{ \"error\":\"true\","+failure+",\"user\":"+user+"}";
        }
        return "{ \"error\":\"false\","+success+",\"user\":"+user+"}";
    }
    /**
     * 
     * @param request JSON user object conatining just the username and password of a user
     * @return JSON response body containing information about the status and user
     */
    @ApiOperation(value = "Check if a provided username and password is a valid user")
    @PostMapping(path = "/login")
    String login(@ApiParam(value="JSON user object",required=true) @RequestBody User request){
        List<User> myuser = userTable.findByUsername(request.getUsername());
        if (myuser.size() == 0 || !(myuser.get(0).getPassword().equals(request.getPassword())))
            return null;
        return "{ \"error\":\"false\","+success+",\"user\":"+myuser.get(0)+"}";
    }
    @ApiOperation(value = "Get user with associated ID")
    @GetMapping(path = "/user/{userID}")
    User getUserById(@PathVariable("userID") int userID){
        User myuser = userTable.findById(userID);
        if (myuser == null)
            return null;
        return myuser;
    }

    @ApiOperation(value = "Add user1ID as a follower of user2ID")
    @GetMapping(path = "/add/{user1ID}/{user2ID}")
    String addFollower(@PathVariable("user1ID") int user1ID, @PathVariable("user2ID") int user2ID){
        User user1 = userTable.findById(user1ID);
        User user2 = userTable.findById(user2ID);
        if (user1 == null || user2 == null)
            return null;
        user1.addFollowing(user2);
        user2.addFollowers(user1);
        userTable.save(user1);
        userTable.save(user2);
        return "{ \"error\":\"false\","+success+"}";
    }

    @ApiOperation(value = "Add user1ID as a follower of user2ID")
    @GetMapping(path = "/searchUsername")
    List<User> searchUsername(@ApiParam(value = "Name that will be searched", required = true) @RequestParam("name") String name){
        if(name == null){
            return null;
        }
        List<User> results = userTable.findByUsername(name);
        if (results.size() == 0)
            return null;
        return results;
    }
}