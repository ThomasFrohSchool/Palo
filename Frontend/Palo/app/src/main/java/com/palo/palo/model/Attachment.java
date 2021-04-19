package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for an attachment recieved from spotify.
 */
public class Attachment implements Parcelable {
    private String spotifyId;
    private String title;
    private String artist;
    private String albumCover;
    private String spotifyLink;

    public Attachment(){}
    public Attachment(String spotifyId){
        this.spotifyId = spotifyId;
        title = "test_title";
        artist = "fake_artist";
        albumCover = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTiUa1gUnZi98GtcAT8-VePbLkB7Mt1LR9OOQ&usqp=CAU";
    }
    public Attachment(String spotifyId, String spotifyLink){
        this.spotifyId = spotifyId;
        this.spotifyLink = spotifyLink;
    }
    
    public Attachment(Parcel parcel){
        spotifyId = parcel.readString();
        title = parcel.readString();
        artist = parcel.readString();
        albumCover = parcel.readString();
        spotifyLink = parcel.readString();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }
    
    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getSpotifyLink() {
        return spotifyLink;
    }

    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }

    public int getType(){
        return -1;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spotifyId);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(albumCover);
        dest.writeString(spotifyLink);
    }

    public static final Parcelable.Creator<Attachment> CREATOR = new Parcelable.Creator<Attachment>() {

        @Override
        public Attachment createFromParcel(Parcel parcel) {
            return new Attachment(parcel);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[0];
        }
    };
}
