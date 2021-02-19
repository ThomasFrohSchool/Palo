package com.palo.palo.model;

public class User {
    private int id;
    private String username;
    private String email;
    
    /*
        Future things user could have:
         - groups
         - liked songs
         - info for needed by spotify?
     */

    public User(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
