package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import coms309.Apple;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }
    @GetMapping("/exp1/wow")
    public String uniWow(HttpServletRequest req){
	return "~~~UNI-WOW~~~<br> You're accessing this site from: " + req.getRemoteAddr();
    }
    @RequestMapping(method=RequestMethod.POST, path="/exp1/post")
    public String myPost(@RequestBody Apple apple){
	    return "Apple color: " + apple.getColor() + "<br>Apple size: " + apple.getSize();
    }
}
