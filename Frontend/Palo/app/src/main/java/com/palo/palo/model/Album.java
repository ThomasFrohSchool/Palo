package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

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
        setSpotifyLink(parcel.readString());
        title = parcel.readString();
        artist = parcel.readString();
        albumCover = parcel.readString();
        setSpotifyLink(parcel.readString());
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
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getSpotifyId());
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(albumCover);
        dest.writeString(getSpotifyLink());
    }

    @Override
    public int getType(){
        return 0;
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {

        @Override
        public Album createFromParcel(Parcel parcel) {
            return new Album(parcel);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[0];
        }
    };
}
