package coms309.test;

import coms309.Posts.*;
import org.junit.jupiter.api.Test;  
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;  

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@SpringBootTest  
public class JUnitControllerTest {

    @Test
    public void testWelcomeController() {
        //WelcomeController controller = new WelcomeController();
        //String result = controller.welcome();
        Posts post = new Posts("String description", 0, "Spotiffy.string.ru.biz.k12.ia.us");
        Assert.assertEquals(post.getDescription(), "String description");
    }
}