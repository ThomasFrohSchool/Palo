package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for posts.
 */
public class Palo implements Parcelable {
    private User author;
    private String postDate, caption;
    private Attachment attachment;
    private Boolean isLiked;

    public Palo(){
        isLiked = false; //todo remove this
    }

    public Palo(Parcel parcel){
        author = parcel.readParcelable(User.class.getClassLoader());
        caption = parcel.readString();
        postDate = parcel.readString();
        attachment = parcel.readParcelable(Attachment.class.getClassLoader());
        isLiked = parcel.readBoolean();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
    
    public void setAuthorUsername(String username) {
        this.author.setUsername(username);
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

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean getIsLiked() {
        return this.isLiked;
    }
    
    public boolean toggleIsLiked(){
        this.isLiked = ! this.isLiked;
        return isLiked;
    }

    public String getProfileImage() {
        return this.author.getProfileImage();
    }

    public String getAuthorUsername() {
        return this.author.getUsername();
    }

    public void updateAttachment(String name, String artist, String albumCover, String id ){
        attachment.setTitle(name);
        attachment.setArtist(artist);
        attachment.setAlbumCover(albumCover);
        attachment.setSpotifyId(id);
    }
    
    @Override
    public int describeContents() {
        return hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(author, flags);
        dest.writeString(caption);
        dest.writeString(postDate);
        dest.writeParcelable(attachment, flags);
        dest.writeBoolean(isLiked);
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

