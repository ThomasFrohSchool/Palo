package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.toolbox.JsonObjectRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for users.
 */
public class User implements Parcelable {
    private int id;
    private String username;
    public String email;
    private String profileImage;
    private List<Integer> userFollowing;
    private List<Integer> userFollowers;

    public User(){}

    public User(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
        this.userFollowing = getUserFollowing();
        this.userFollowers = getUserFollowers();
    }
    
    public User(Parcel parcel){
        id = parcel.readInt();
        username = parcel.readString();
        email = parcel.readString();
        profileImage = parcel.readString();
        this.userFollowing = getUserFollowing();
        this.userFollowers = getUserFollowers();
    }
    public User(int id){
        this.id = id;
        this.username = "TEMP_USER";
        this.profileImage = "https://img.apmcdn.org/4f25ecdbbd7af5fed833153302515a94c990de11/square/7aacc5-20130508-favorite-album-covers.jpg";
        this.userFollowing = getUserFollowing();
        this.userFollowers = getUserFollowers();
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

    public List<Integer> getUserFollowing() { return userFollowing; }

    public List<Integer> getUserFollowers() { return userFollowers; }

    public void setUserFollowing(List<Integer> userFollowing) {
        this.userFollowing = userFollowing;
    }

    public void setUserFollowers(List<Integer> userFollowers) {
        this.userFollowers = userFollowers;
    }

    /*public boolean getUserByIdFollowing(Integer id) {
        for(int i = 0; i < userFollowing.size(); i++) {
            if(id == userFollowing.get(i))
                return true;
        }
        return false;
    }

    public void addFollowing(Integer id) {
        userFollowing.add(id);
    }

    public boolean toggleIsFollowing(int id) {
        //int pos;
        for(int i = 0; i < userFollowing.size(); i++) {
            if(userFollowing.get(i) == id) {
                userFollowing.remove(i);

                return false;
            }
        }
        userFollowing.add(id);
        return true;
    }*/

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(profileImage);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };
}
