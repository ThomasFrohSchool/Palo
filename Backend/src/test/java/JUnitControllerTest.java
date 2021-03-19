package coms309.test;

import coms309.Posts.*;
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

    @InjectMocks
	UserController userEndpoints;

    @Mock
	UserTable users;

    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
	public void getAccountByIdTest() {
		when(repo.getAccountByID(1)).thenReturn(new Account(1, "jDoe", "123456", "jDoe@gmail.com"));

		Account acct = acctService.getAccountByID(1);

		assertEquals("jDoe", acct.getUserID());
		assertEquals("123456", acct.getPassword());
		assertEquals("jDoe@gmail.com", acct.getEmail());
	}

}