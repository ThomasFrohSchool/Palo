package com.palo.palo.model;

public class User {
    private int id;
    private String username;
    public String email;
    private String profileImage;

    /*
        Future things user could have:
         - groups
         - liked songs
         - info for needed by spotify?
     */
    public User(){}
    public User(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) { this.username = username; }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
