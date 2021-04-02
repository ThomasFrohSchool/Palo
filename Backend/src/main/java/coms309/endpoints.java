package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
class WelcomeController {



    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/exp1/wow")
    public String uniWow(HttpServletRequest req){
	return "~~~UNI-WOW~~~<br> You're accessing this site from: " + req.getRemoteAddr();
    }
}
