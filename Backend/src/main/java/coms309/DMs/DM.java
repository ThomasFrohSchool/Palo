package coms309.DMs;

import coms309.*;
import coms309.Users.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

	@ManyToMany
	@JoinColumn(name = "from_id")
	private User from_user;
	private User to_user;

	public DM(String message, int from_id, int to_id){
		this.to_id = to_id;
		this.from_id = from_id;
		this.message = message;
	}

	public DM(){
	}

	public int getId(){
		return this.id;
	}
	public User getToUser(){
		return this.to_user;
	}
	public User getFromUser(){
		return this.from_user;
	}
	public String getCreateDate(){
		return this.createDate;
	}
	public String getMessage(){
		return this.message;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setToUser(User user){
		this.to_user = user;
	}
	public void setFromUser(User user){
		this.from_user = user;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public void setCreateDate(String thedate){
		this.createDate = thedate;
	}
}
