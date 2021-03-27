package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Attatchment implements Parcelable {
    private String spotifyId;
    private String title;
    private String artist;
    private String albumCover;
    private String spotifyLink;

    public Attatchment(){}
    public Attatchment(String spotifyId){
        this.spotifyId = spotifyId;
    }
    public Attatchment(String spotifyId, String spotifyLink){
        this.spotifyId = spotifyId;
        this.spotifyLink = spotifyLink;
    }
    
    public Attatchment(Parcel parcel){
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

    public static final Parcelable.Creator<Attatchment> CREATOR = new Parcelable.Creator<Attatchment>() {

        @Override
        public Attatchment createFromParcel(Parcel parcel) {
            return new Attatchment(parcel);
        }

        @Override
        public Attatchment[] newArray(int size) {
            return new Attatchment[0];
        }
    };
}
