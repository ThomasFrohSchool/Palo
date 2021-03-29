package com.palo.palo.model;

public class Album extends Attatchment{
    String albumCover;
    String artist;
    String title;

    public Album(){}
    public Album(String spotifyId){
        super(spotifyId);
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
    public int getType(){
        return 0;
    }
}
