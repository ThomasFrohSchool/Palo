package coms309;

class User {
	private String username;
	private String password;

	public User(String username, String password){
		this.username = username;
		this.password = password;
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

}
