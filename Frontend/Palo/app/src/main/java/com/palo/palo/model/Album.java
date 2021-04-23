package com.palo.palo.model;

import android.os.Parcel;

/**
 * Model for album attachment recieved from spotify.
 */
import org.json.JSONException;
import org.json.JSONObject;

public class Album extends Attachment {
    String albumCover;
    String artist;
    String title;

    public Album(){}
    public Album(String spotifyId){
        super(spotifyId);
    }
    public Album(Parcel parcel){
        //TODO
    }

    public Album(JSONObject json) throws JSONException {
        super(json.getString("id"), json.getString("link"));
        title = json.getString("name");
        artist = json.getString("artist");
        albumCover = json.getString("imageUrl");
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
