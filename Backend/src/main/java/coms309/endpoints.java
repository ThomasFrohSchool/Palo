package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import coms309.User;
import java.util.ArrayList;

@RestController
class WelcomeController {
    ArrayList<User> users = new ArrayList<User>();
    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/api/v1/getUsers")
    public String getUsers() {
        String userString = "";
        for(int i=0; i<users.size(); i++){
            userString = userString + users.get(i) + "<br>";
        }
        return userString;
    }
    @GetMapping("/exp1/wow")
    public String uniWow(HttpServletRequest req){
	return "~~~UNI-WOW~~~<br> You're accessing this site from: " + req.getRemoteAddr();
    }
    @RequestMapping(method=RequestMethod.POST, path="/api/v1/addUser")
    public String postUser(@RequestBody User user){
        users.add(user);
	    return "User: " + users.get(users.size()-1) + " Has been added";
    }
}
