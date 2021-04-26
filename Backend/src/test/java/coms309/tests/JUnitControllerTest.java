package coms309.tests;

import coms309.DMs.Message;
import coms309.DMs.MessageController;
import coms309.DMs.MessageTable;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JUnitControllerTest {



	@Mock
    SpotifyController spotifyController;

    @Mock
	UserTable users;

	@Mock
	PostsTable posts;

	@Mock
	MessageController messageController;

	@Mock
	MessageTable messages;

    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	//David Test
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
	//David Test
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
	//David Test
	@Test
	public void addLikePostTest() {
		//Mock 3 user posts to be liked
		when(posts.findById(1)).thenReturn(new Posts("This is a good song", 0, "1eYTN19ZXz0i9iuIX2TD5U"));
		when(posts.findById(2)).thenReturn(new Posts("Great album", 1, "0ada5XsQGLPUVbmTCkAP49"));
		when(posts.findById(3)).thenReturn(new Posts("a new summer jam :)", 1, "3BZEcbdtXQSo7OrvKRJ6mb"));

		//3 different user like objects
		Likes l = new Likes(1);
		Likes l1 = new Likes(2);
		Likes l2 = new Likes(3);

		//Have each user like one post
		Posts userPost1 = posts.findById(1);
		userPost1.setLikeList(new ArrayList<Likes>());
		userPost1.addLikeList(l);
		Posts userPost2 = posts.findById(2);
		userPost2.setLikeList(new ArrayList<Likes>());
		userPost2.addLikeList(l1);
		Posts userPost3 = posts.findById(3);
		userPost3.setLikeList(new ArrayList<Likes>());
		userPost3.addLikeList(l2);

		//Get the first like and check if the correct user liked the post
		assertEquals(1,userPost1.getLikeList().get(0).getUser_id());
		assertEquals(2,userPost2.getLikeList().get(0).getUser_id());
		assertEquals(3,userPost3.getLikeList().get(0).getUser_id());
	}
	//David Test
	@Test
	public void makeCommentTest() {
		//Mock 3 user posts to be commented on
		when(posts.findById(1)).thenReturn(new Posts("~~UNI WOW~~", 0, "1eYTN19ZXz0i9iuIX2TD5U"));
		when(posts.findById(2)).thenReturn(new Posts("Next big thing!", 1, "0ada5XsQGLPUVbmTCkAP49"));
		when(posts.findById(3)).thenReturn(new Posts("Can't wait to see then live <3", 1, "3BZEcbdtXQSo7OrvKRJ6mb"));

		//Create 3 comment's from different users
		Comments c1 = new Comments("Thanks for the suggestion!",55);
		Comments c2 = new Comments("I've never heard this before",66);
		Comments c3 = new Comments("Added it to my playlist :eyes:",77);

		//Comment once on posts 1 and 2 and twice on post 3
		Posts userPost1 = posts.findById(1);
		userPost1.setComments(new ArrayList<Comments>());
		userPost1.addComment(c1);
		Posts userPost2 = posts.findById(2);
		userPost2.setComments(new ArrayList<Comments>());
		userPost2.addComment(c2);
		Posts userPost3 = posts.findById(3);
		userPost3.setComments(new ArrayList<Comments>());
		userPost3.addComment(c3);
		userPost3.addComment(c1);

		assertEquals("Thanks for the suggestion!",userPost1.getComments().get(0).getBody());
		assertEquals("I've never heard this before",userPost2.getComments().get(0).getBody());
		assertEquals("Added it to my playlist :eyes:",userPost3.getComments().get(0).getBody());
		assertEquals("Thanks for the suggestion!",userPost3.getComments().get(1).getBody());
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

	@Test
	public void getDmListTest(){
		List<String> marshallList = new ArrayList<String>();
		List<String> davidList = new ArrayList<String>();
		marshallList.add("david");
		marshallList.add("tiffany");
		marshallList.add("thomas");

		davidList.add("marshall");
		davidList.add("john");
		davidList.add("steve");
		when(messageController.getDmList("marshall")).thenReturn(marshallList);
		when(messageController.getDmList("david")).thenReturn(davidList);

		List<String> marshallObj = messageController.getDmList("marshall");
		List<String> davidObj = messageController.getDmList("david");

		for(int i = 0; i < marshallList.size();i++){
			assertEquals(marshallList.get(i), marshallObj.get(i));
		}
		for(int i = 0; i < davidList.size();i++){
			assertEquals(davidList.get(i), davidObj.get(i));
		}

	}

	@Test
	public void messageFindByFromUserTest(){
		List<Message> fromMarshall = new ArrayList<>();
		fromMarshall.add(new Message("marshall", "david", "yoooo dave whats up!"));
		fromMarshall.add(new Message("marshall", "thomas", "Tommy you gotta check out this new song I found!"));

		List<Message> fromDavid = new ArrayList<>();
		fromDavid.add(new Message("david", "marshall", "Hey man, long time no see!"));
		fromDavid.add(new Message("david", "tiffany", "Yo tiff, check out my latest post, its an album from my favorite anime!!"));
		
		when(messages.findByfromUser("marshall")).thenReturn(fromMarshall);
		when(messages.findByfromUser("david")).thenReturn(fromDavid);

		List<Message> fromMarshallObj = messages.findByfromUser("marshall");
		List<Message> fromDavidObj = messages.findByfromUser("david");

		for(int i = 0; i < fromMarshall.size();i++){
			assertEquals(fromMarshall.get(i), fromMarshallObj.get(i));
		}

		for(int i = 0; i < fromDavid.size();i++){
			assertEquals(fromDavid.get(i), fromDavidObj.get(i));
		}
	}
}
