package coms309;

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

	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	public User(){
	}
	public String toString(){
		return this.getUsername() + ":" + this.getPassword();
	}
	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setUsername(String username){
		this.username = username;
	}

}
