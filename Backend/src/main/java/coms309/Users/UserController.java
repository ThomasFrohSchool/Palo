package coms309.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private String success = "\"message\":\"success\"";
    private String failure = "\"message\":\"failure\"";

    @Autowired
    UserTable userTable;

    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userTable.findAll();
    }

    @PostMapping(path = "/register")
    String createUser(@RequestBody User user){
        if (user == null)
            return "{ \"error\":\"true\","+failure+",\"user\":"+user+"}";
        try {
            userTable.save(user);
        } catch (Exception e){
            return "{ \"error\":\"true\","+failure+",\"user\":"+user+"}";
        }
        return "{ \"error\":\"false\","+success+",\"user\":"+user+"}";
    }

    @PostMapping(path = "/login")
    String login(@RequestBody User request){
        List<User> myuser = userTable.findByUsername(request.getUsername());
        if (myuser.size() == 0 || !(myuser.get(0).getPassword().equals(request.getPassword())))
            return null;
        return "{ \"error\":\"false\","+success+",\"user\":"+myuser.get(0)+"}";
    }

}