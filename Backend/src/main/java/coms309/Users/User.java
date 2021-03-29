package coms309.Users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import coms309.Posts.Posts;

@Entity
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;

	@OneToMany
	private List<Posts> posts;
	
	@ManyToMany
	private List<User> followers;

	@ManyToMany
	private List<User> following;

	public User(String username, String password, String email){
		this.username = username;
		this.password = password;
		this.email = email;
	}
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	public User(){
	}
	public String toString(){
		return "{\"username\":\""+this.getUsername()+"\", \"email\":\"" + this.getEmail()+"\",\"id\":\""+this.getId()+"\"}";
	}
	public int getId(){
		return this.id;
	}
	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
	public String getEmail(){
		return this.email;
	}
	public List<Posts> getPosts(){
		return this.posts;
	}
	public List<Integer> getFollowers(){
		List<Integer> followerIDs = new ArrayList<Integer>();
		for(int i=0;i<this.followers.size();i++){
			followerIDs.add(this.followers.get(i).getId());
		}
		return followerIDs;
	}
	public List<Integer> getFollowing(){
		List<Integer> followingIDs = new ArrayList<Integer>();
		for(int i=0;i<this.following.size();i++){
			followingIDs.add(this.following.get(i).getId());
		}
		return followingIDs;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setPosts(List<Posts> posts){
		this.posts = posts;
	}
	public void addPosts(Posts post){
		this.posts.add(post);
	}
	public void setFollowers(List<User> followers){
		this.followers = followers;
	}
	public void setFollowing(List<User> following){
		this.following = following;
	}
	public void addFollowers(User follower){
		this.followers.add(follower);
	}
	public void addFollowing(User following){
		this.following.add(following);
	}
}
