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

import java.util.ArrayList;
import java.util.List;

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
		when(users.findById(2)).thenReturn(new User("test", "zxcv", "test@zxc.com"));

		User acct = users.findById(1);
		User acct2 = users.findById(2);
		assertEquals("jDoe", acct.getUsername());
		assertEquals("123456", acct.getPassword());
		assertEquals("jDoe@gmail.com", acct.getEmail());

		assertEquals("test", acct2.getUsername());
		assertEquals("zxcv", acct2.getPassword());
		assertEquals("test@zxc.com", acct2.getEmail());
	}
	@Test
	public void getFollowersTest() {
		when(users.findById(1)).thenReturn(new User("jDoe", "123456", "jDoe@gmail.com"));
		when(users.findById(2)).thenReturn(new User("tSmoke", "654321", "tSmokes@gmail.com"));

		User acct = users.findById(1);
		acct.setFollowers(new ArrayList<User>());
		User follower = users.findById(2);
		follower.setId(2);
		acct.addFollowers(follower);
		List<Integer> i = new ArrayList<Integer>();
		i.add(2);
		assertEquals(i, acct.getFollowers());
	}

	@Test
	public void findPostsByIdTest() {
		when(posts.findById(1)).thenReturn(new Posts("description", 0, "1eYTN19ZXz0i9iuIX2TD5U"));
		when(posts.findById(2)).thenReturn(new Posts("fun post", 1, "0ada5XsQGLPUVbmTCkAP49"));
		when(posts.findById(3)).thenReturn(new Posts("another post", 1, "3BZEcbdtXQSo7OrvKRJ6mb"));

		Posts post1 = posts.findById(1);
		Posts post2 = posts.findById(2);
		Posts post3 = posts.findById(3);

		assertEquals("description", post1.getDescription());
		assertEquals(0, post1.getType());
		assertEquals("1eYTN19ZXz0i9iuIX2TD5U", post1.getSpot_id());

		assertEquals("fun post", post2.getDescription());
		assertEquals(1, post2.getType());
		assertEquals("0ada5XsQGLPUVbmTCkAP49", post2.getSpot_id());

		assertEquals("another post", post3.getDescription());
		assertEquals(1, post3.getType());
		assertEquals("3BZEcbdtXQSo7OrvKRJ6mb", post3.getSpot_id());
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