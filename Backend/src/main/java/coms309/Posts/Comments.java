package coms309.Posts;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


import coms309.Users.User;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String body;
	private String createDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

    @ManyToOne
	@JoinColumn(name = "posts_id")
	@JsonIgnore
	private Posts post;


	public Comments(String body){
		this.body = body;
	}

	public Comments(){
	}

	public int getId(){
		return this.id;
	}
	public String getBody(){
		return this.body;
	}
	public User getUser(){
		return this.user;
	}
    public Posts getPosts(){
		return this.post;
	}
	public int getUser_id(){
		return this.user.getId();
	}
    public int getPosts_id(){
		return this.post.getId();
	}
	public String getCreateDate(){
		return this.createDate;
	}
	public void setBody(String body){
		this.body = body;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setUser(User user){
		this.user = user;
	}
    public void setPosts(Posts post){
		this.post = post;
	}
	public void setCreateDate(String thedate){
		this.createDate = thedate;
	}
}
