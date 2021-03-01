package com.palo.palo.model;

public class Song {
    private  String title;
    private String artist;
    private String albumCover;

    public Song(){}
    public Song(String title, String artist, String albumCover){
        this.title = title;
        this.artist = artist;
        this.albumCover = albumCover;
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
}