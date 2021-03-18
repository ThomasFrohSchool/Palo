package com.palo.palo.model;

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
}