package coms309.Uploads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import coms309.Users.User;
import coms309.Users.UserTable;
import java.io.IOException;

@RestController
public class UploadController {
 
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    UserTable userTable;
     
    @PostMapping("/profile/save/{userID}")
    public String saveProfile(@PathVariable int userID, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User user = userTable.findById(userID);
        String fileName = String.valueOf(user.getId());
        user.setProfile(fileName);
         
        User savedUser = userTable.save(user);
 
        String uploadDir = "/var/www/html/pics/" + savedUser.getId();
        try {
            uploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception e) {
            return e.toString();
        }
         
        return fileName;
    }
}