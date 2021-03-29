package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song extends Attatchment {
    public static final int TYPE = 2;
    private  String title;
    private String artist;
    private String albumCover;
    private String playbackLink;

    public Song(){}
    public Song(String title, String artist, String albumCover, String spotifyId, String spotifyLink, String playbackLink){
        super(spotifyId, spotifyLink);
        this.title = title;
        this.artist = artist;
        this.albumCover = albumCover;
        this.playbackLink = playbackLink;
    }
    public Song(Parcel parcel){
        playbackLink = parcel.readString();
        setSpotifyId(parcel.readString());
        title = parcel.readString();
        artist = parcel.readString();
        albumCover = parcel.readString();
        setSpotifyLink(parcel.readString());
    }
    public Song(String spot_link){
        super(spot_link);
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

    @Override
    public int getType() {
        return 2;
    }
    
    public String getPlaybackLink() {
        return playbackLink;
    }

    public void setPlaybackLink(String playbackLink) {
        this.playbackLink = playbackLink;
    }
    
    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playbackLink);
        dest.writeString(getSpotifyId());
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(albumCover);
        dest.writeString(getSpotifyLink());
    }

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {

        @Override
        public Song createFromParcel(Parcel parcel) {
            return new Song(parcel);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[0];
        }
    };
}