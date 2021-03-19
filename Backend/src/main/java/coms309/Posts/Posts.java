package coms309.Posts;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.annotation.JsonIgnore;


import coms309.Users.User;
import coms309.Users.UserTable;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String spot_link;
	private String description;
	private int type;
	private int likes;
	private String createDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;


	public Posts(String description, int type, String spot_link){
		this.spot_link = spot_link;
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
	public String getSpot_link(){
		return this.spot_link;
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
	public String getCreateDate(){
		return this.createDate;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setSpot_link(String spot_link){
		this.spot_link = spot_link;
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
}
