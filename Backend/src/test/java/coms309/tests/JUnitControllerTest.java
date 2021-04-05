package coms309.tests;

import coms309.Posts.*;
import coms309.Spotify.SpotifyController;
import coms309.Users.*;
import coms309.Users.UserController;
import coms309.Users.UserTable;
import top.jfunc.json.impl.JSONObject;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SpringBootTest  
public class JUnitControllerTest {



	@Mock
    SpotifyController spotifyController;

    @Mock
	UserTable users;

	@Mock
	PostsTable posts;

    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
	public void findByIdTest() {
		when(users.findById(1)).thenReturn(new User("jDoe", "123456", "jDoe@gmail.com"));

		User acct = users.findById(1);

		assertEquals("jDoe", acct.getUsername());
		assertEquals("123456", acct.getPassword());
		assertEquals("jDoe@gmail.com", acct.getEmail());
	}

	@Test
	public void findPostsByIdTest() {
		when(posts.findById(1)).thenReturn(new Posts("description", 0, "1eYTN19ZXz0i9iuIX2TD5U"));

		Posts post = posts.findById(1);

		assertEquals("description", post.getDescription());
		assertEquals(0, post.getType());
		assertEquals("1eYTN19ZXz0i9iuIX2TD5U", post.getSpot_id());
	}

	@Test
	public void spotifyGetAlbumTest() {
		when(spotifyController.album("1rzDtYMpZDhRgKNigB467r")).thenReturn(new JSONObject("{\"artist\":\"Imagine Dragons\",\"imageUrl\":\"https://i.scdn.co/image/ab67616d00001e02a8f95e7f840c11edfa6cc3bd\",\"name\":\"Night Visions (Deluxe)\",\"link\":\"https://open.spotify.com/album/1rzDtYMpZDhRgKNigB467r\",\"id\":\"1rzDtYMpZDhRgKNigB467r\"}"));

		JSONObject obj = spotifyController.album("1rzDtYMpZDhRgKNigB467r");

		assertEquals("Imagine Dragons", obj.getString("artist"));
		assertEquals("https://i.scdn.co/image/ab67616d00001e02a8f95e7f840c11edfa6cc3bd", obj.getString("imageUrl"));
		assertEquals("Night Visions (Deluxe)", obj.getString("name"));
		assertEquals("https://open.spotify.com/album/1rzDtYMpZDhRgKNigB467r", obj.getString("link"));
		assertEquals("1rzDtYMpZDhRgKNigB467r", obj.getString("id"));
	}
}