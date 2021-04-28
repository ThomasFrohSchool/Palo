package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.toolbox.JsonObjectRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.palo.palo.volley.ServerURLs.PICS;

/**
 * Model for users.
 */
public class User implements Parcelable {
    private int id;
    private String username;
    public String email;
    private String profileImage;
    private String bio;
    private List<Integer> userFollowing;
    private List<Integer> userFollowers;
    private boolean isFollower;

    public User(){}

    public User(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
        this.userFollowing = new ArrayList<>();
        this.userFollowers = new ArrayList<>();
        this.bio = "";
        this.profileImage = "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
    }
    
    public User(Parcel parcel){
        id = parcel.readInt();
        username = parcel.readString();
        email = parcel.readString();
        profileImage = parcel.readString();
        bio = parcel.readString();
        this.userFollowing = new ArrayList<>();
        this.userFollowers = new ArrayList<>();
    }
    public User(int id){
        this.id = id;
        this.username = "";
        this.bio = "";
        this.profileImage = "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
        this.userFollowing = new ArrayList<>();
        this.userFollowers = new ArrayList<>();
    }

    public User(JSONObject userJson) throws JSONException {
        username = userJson.getString("username");
        id = userJson.getInt("id");
        profileImage = PICS + userJson.getInt("id") + "/" + userJson.getInt("id");
        bio = userJson.getString("bio");
    }

    public User(JSONObject userJson, int currentUserID) throws JSONException {
        username = userJson.getString("username");
        id = userJson.getInt("id");
        profileImage = PICS + userJson.getInt("id") + "/" + userJson.getInt("id");
        bio = userJson.getString("bio");
        userFollowing = new ArrayList<>();
        JSONArray j = userJson.getJSONArray("followers");
        isFollower = false;
        for(int i = 0; i < j.length(); i++) {
            if(currentUserID == j.getInt(i));
                isFollower = true;
        }
    }

    public User(int userId, String username) {
        id = userId;
        this.username = username;
        this.profileImage = "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
        this.bio = "";
    }

    public void setIsFollower() {
        this.isFollower = !isFollower;
    }

    public boolean getIsFollower() { return isFollower; }

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

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() { return bio; }

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
