package com.palo.palo.model;

/**
 * Model for song attachment.
 */
public class Song {
    private  String title;
    private String artist;
    private String albumCover;
    private String id;

    public Song(){}
    public Song(String title, String artist, String albumCover, String id){
        this.title = title;
        this.artist = artist;
        this.albumCover = albumCover;
        this.id = id;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
