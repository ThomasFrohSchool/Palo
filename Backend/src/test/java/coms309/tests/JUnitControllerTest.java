package coms309.tests;

import coms309.Posts.*;
import coms309.Spotify.SpotifyController;
import coms309.Users.*;
import coms309.Users.UserController;
import coms309.Users.UserTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JUnitControllerTest {

	@Autowired
	private MockMvc controller;

	@MockBean 
	private CommentsTable commentsTable;

	@MockBean
	private PostsController postController;


	/*
	 * There are three steps here:
	 *   1 - set up mock database methods
	 *   2 - set up mock service methods
	 *   3 - call and test the results of the controller
	 */
	@Test
	public void testGetComments() throws Exception {
		/*
		// Set up MOCK methods for the REPO
	    List<Comments> l = new ArrayList<Comments>();

	    // mock the findAll method
		when(commentsTable.findAll()).thenReturn(l);

  		// mock the save() method to save argument to the list
	  	when(commentsTable.save((Comments)any(Comments.class)))
				.thenAnswer(x -> {
					  Comments r = x.getArgument(0);
					  l.add(r);
					  return null;
		  });


		// Set up MOCK methods for the SERVICE

	  	// mock the reverse method
	  	when(postController.getComments(10)).thenReturn("[{\"user_id\":41,\"body\":\"this is my comment on tommys post\",\"createDate\":\"2021/03/31 13:03:28\"},{\"user_id\":1,\"body\":\"This is David commenting on a post\",\"createDate\":\"2021/03/31 13:03:35\"},{\"user_id\":41,\"body\":\"test\",\"createDate\":\"2021/03/31 13:16:43\"}]");

		  */

	  // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
	  // we sent hello. we expect back a list with first object having "olleh" in it
		controller.perform(get("/posts/getcomments/10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].data", is("[{\"user_id\":41,\"body\":\"this is my comment on tommys post\",\"createDate\":\"2021/03/31 13:03:28\"},{\"user_id\":1,\"body\":\"This is David commenting on a post\",\"createDate\":\"2021/03/31 13:03:35\"},{\"user_id\":41,\"body\":\"test\",\"createDate\":\"2021/03/31 13:16:43\"}]")));
	}


}