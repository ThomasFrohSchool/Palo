package com.palo.palo.model;

public class Attatchment {
    private String spotifyId;
    private String title;
    private String artist;
    private String albumCover;


    public Attatchment(){}
    public Attatchment(String spotifyId){
        this.spotifyId = spotifyId;
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

    public int getType(){
        return -1;
    }
}
