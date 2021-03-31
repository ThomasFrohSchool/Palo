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
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String spot_id;
	private String description;
	private int type;
	private int likes;
	private String createDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;


	public Posts(String description, int type, String spot_id){
		this.spot_id = spot_id;
		this.description = description;
		this.type = type;
		this.likes = 0;
	}

	public Posts(){
	}

	public int getId(){
		return this.id;
	}
	public String getDescription(){
		return this.description;
	}
	public String getSpot_id(){
		return this.spot_id;
	}
	public User getUser(){
		return this.user;
	}
	public int getType(){
		return this.type;
	}
	public int getUser_id(){
		return this.user.getId();
	}
	public int getLikes(){
		return this.likes;
	}
	public String getCreateDate(){
		return this.createDate;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setSpot_id(String spot_id){
		this.spot_id = spot_id;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setUser(User user){
		this.user = user;
	}
	public void setType(int type){
		this.type = type;
	}
	public void setCreateDate(String thedate){
		this.createDate = thedate;
	}
	public void setLikes(int likes){
		this.likes = likes;
	}
}
