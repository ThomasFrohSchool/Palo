package coms309.tests;

import coms309.Posts.*;
import coms309.Users.*;
import coms309.Users.UserController;
import coms309.Users.UserTable;

import org.springframework.boot.test.context.SpringBootTest;  

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@SpringBootTest  
public class JUnitControllerTest {

    @InjectMocks
	UserController userEndpoints;
    PostsController postsController;

    @Mock
	UserTable users;

    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
	public void findByIdTest() {
		when(users.findById(1)).thenReturn(new User("jDoe", "123456", "jDoe@gmail.com"));

		User acct = users.findById(1);

		assertEquals("jDoe", acct.getId());
		assertEquals("123456", acct.getPassword());
		assertEquals("jDoe@gmail.com", acct.getEmail());
	}


    @Test
	public void getUserPostTest() {
		when(postsController.getUserPosts(1)).thenReturn((List<Posts>) new Posts("test description", 0, "https://open.spotify.com/track/5d8sUjf50rOL5fyjEqXIHb?si=4veOfQULRDu0S6tZArFtSQ"));

		List<Posts> posts = postsController.getUserPosts(1);

		assertEquals("test description", posts.get(0).getDescription());
		assertEquals("0", posts.get(0).getType());
		assertEquals("https://open.spotify.com/track/5d8sUjf50rOL5fyjEqXIHb?si=4veOfQULRDu0S6tZArFtSQ", posts.get(0).getSpot_link());
	}

}