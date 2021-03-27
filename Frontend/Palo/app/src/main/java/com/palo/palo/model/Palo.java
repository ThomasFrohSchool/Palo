package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for posts.
 */
public class Palo implements Parcelable {
    private User author;
    private String postDate, caption;
    private Attatchment attatchment;

    public Palo(){}

    public Palo(Parcel parcel){
        author = parcel.readParcelable(User.class.getClassLoader());
        caption = parcel.readString();
        postDate = parcel.readString();
        attatchment = parcel.readParcelable(Attatchment.class.getClassLoader());
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Attatchment getAttatchment() {
        return attatchment;
    }

    public void setAttatchment(Attatchment attatchment) {
        this.attatchment = attatchment;
    }

    public String getProfileImage() {
        return this.author.getProfileImage();
    }

    public String getAuthorUsername() {
        return this.author.getUsername();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(author, flags);
        dest.writeString(caption);
        dest.writeString(postDate);
        dest.writeParcelable(attatchment, flags);
    }

    public static final Parcelable.Creator<Palo> CREATOR = new Parcelable.Creator<Palo>() {

        @Override
        public Palo createFromParcel(Parcel parcel) {
            return new Palo(parcel);
        }

        @Override
        public Palo[] newArray(int size) {
            return new Palo[0];
        }
    };
}

