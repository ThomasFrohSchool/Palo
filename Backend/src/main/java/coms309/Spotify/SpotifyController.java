package coms309.Spotify;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SpotifyController {

    private String success = "\"message\":\"success\"";
    private String failure = "\"message\":\"failure\"";

    @GetMapping(path = "/search")
    public String search(@RequestParam("q") String q){
        return q;
    }
}