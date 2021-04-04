package coms309.Posts;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String body;
	private String createDate;
	private int user_id;

	//@ManyToOne
	//@JoinColumn(name = "user_id")
	//@JsonIgnore
	//private User user;

    @ManyToOne
	@JoinColumn(name = "posts_id")
	@JsonIgnore
	private Posts post;


	public Comments(String body, int user_id){
		this.body = body;
		this.user_id = user_id;
	}

	public Comments(){
	}

	public int getId(){
		return this.id;
	}
	public String getBody(){
		return this.body;
	}

	public int getUser_id(){
		return this.user_id;
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
	public void setUser_id(int user_id){
		this.user_id = user_id;
	}

    public void setPosts(Posts post){
		this.post = post;
	}
	public void setCreateDate(String thedate){
		this.createDate = thedate;
	}
}
