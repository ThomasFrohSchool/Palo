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

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }
 
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userTable.findAll();
    }

    @GetMapping("/api/v1/getUsers")
    public String getUsers() {
        return "lOL";
    }
    @GetMapping("/exp1/wow")
    public String uniWow(HttpServletRequest req){
	return "~~~UNI-WOW~~~<br> You're accessing this site from: " + req.getRemoteAddr();
    }
    /*@RequestMapping(method=RequestMethod.POST, path="/api/v1/addUser")
    public String postUser(@RequestBody User user){
        users.add(user);
	    return "User: " + users.get(users.size()-1) + " Has been added";
    }*/
}
