package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for users.
 */
public class User implements Parcelable {
    private int id;
    private String username;
    public String email;
    private String profileImage;

    public User(){}

    public User(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }
    
    public User(Parcel parcel){
        id = parcel.readInt();
        username = parcel.readString();
        email = parcel.readString();
        profileImage = parcel.readString();
    }
    public User(int id){
        this.id = id;
        this.username = "TEMP_USER";
        this.profileImage = "https://img.apmcdn.org/4f25ecdbbd7af5fed833153302515a94c990de11/square/7aacc5-20130508-favorite-album-covers.jpg";
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
