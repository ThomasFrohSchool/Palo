package coms309.test;

import coms309.Posts.*;
import org.junit.jupiter.api.Test;  
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;  

@SpringBootTest  
public class JUnitControllerTest {

    @Test
    public void testWelcomeController() {
        //WelcomeController controller = new WelcomeController();
        //String result = controller.welcome();
        Posts post = new Posts(51, "Big ups my dude!", "Spotify.link.ru.cn.k12.biz");
        Assert.assertEquals(post.getownerID(), 51);
    }
}