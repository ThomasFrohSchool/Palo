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
        Posts post = new Posts(51, "String description", 0, "Spotiffy.string.ru.biz.k12.ia.us");
        Assert.assertEquals(post.getownerID(), 51);
    }
}