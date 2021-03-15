package com.palo.palo.model;

public class Song extends Attatchment {
    public static final int TYPE = 2;
    private  String title;
    private String artist;
    private String albumCover;
//    private String spotifyId;

    public Song(){}
    public Song(String title, String artist, String albumCover, String spotifyId){
        super(spotifyId);
        this.title = title;
        this.artist = artist;
        this.albumCover = albumCover;
//        this.spotifyId = spotifyId;
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
    
//    public String getSpotifyId() {
//        return spotifyId;
//    }
//
//    public void setSpotifyId(String spotifyId) {
//        this.spotifyId = spotifyId;
//    }
}