package coms309.Users;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
class User {

	@Id
    	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;

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
}
