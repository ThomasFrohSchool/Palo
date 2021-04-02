package coms309.tests;

import coms309.Posts.*;
import coms309.Users.*;
import coms309.Users.UserController;
import coms309.Users.UserTable;

import org.springframework.boot.test.context.SpringBootTest;  

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;

@SpringBootTest  
public class JUnitControllerTest {

    @InjectMocks
	UserController userEndpoints;
	@InjectMocks
    PostsController postsController;

    @Mock
	UserTable users;

    @Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
	public void findByIdTest() {
		when(users.findById(1)).thenReturn(new User("jDoe", "123456", "jDoe@gmail.com"));

		User acct = users.findById(1);

		assertEquals("jDoe", acct.getUsername());
		assertEquals("123456", acct.getPassword());
		assertEquals("jDoe@gmail.com", acct.getEmail());
	}

}