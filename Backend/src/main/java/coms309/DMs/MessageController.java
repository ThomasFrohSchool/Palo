package coms309.DMs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MessageController {
    @Autowired
    MessageTable msgTable;

    @GetMapping(path = "/dms/{username}")
    public List<String> getDmList(@PathVariable("username") String username){
        List<Message> fromMessages = msgTable.findByfromUser(username);
        List<Message> toMessages = msgTable.findBytoUser(username);
        List<String> contacted = new ArrayList<String>();

        for(int i = 0; i<fromMessages.size();i++){
            String toAdd = fromMessages.get(i).gettoUser();
            if(!(contacted.contains(toAdd))){
                contacted.add(toAdd);
            }
        }
        for(int i = 0; i<toMessages.size();i++){
            String toAdd = toMessages.get(i).getfromUser();
            if(!(contacted.contains(toAdd))){
                contacted.add(toAdd);
            }
        }
        return contacted;
    }
}
