package coms309.DMs;

import coms309.*;
import coms309.Users.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class DM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String createDate;
    private int from_id;
    private int to_id;
    private String message;

	//@ManyToMany
	//@JoinColumn(name = "user_id")
	private User user;


	public DM(String message, int from_id, int to_id){
		this.to_id = to_id;
		this.from_id = from_id;
		this.message = message;
	}

	public DM){
	}

	public int getId(){
		return this.id;
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
	public void setId(int id){
		this.id = id;
	}
	public void setUser(User user){
		this.user = user;
	}
	public void setCreateDate(String thedate){
		this.createDate = thedate;
	}
}
