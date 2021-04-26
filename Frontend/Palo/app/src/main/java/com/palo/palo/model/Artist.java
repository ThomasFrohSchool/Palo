package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for artist attachment recieved from spotify.
 */
import org.json.JSONException;
import org.json.JSONObject;

public class Artist extends Attachment {

    public Artist(){}
    public Artist(String spotifyId){
        super(spotifyId);
    }

    public Artist(Parcel parcel){
        setSpotifyId(parcel.readString());
        setTitle(parcel.readString());
        setArtist(parcel.readString());
        setAlbumCover(parcel.readString());
        setSpotifyLink(parcel.readString());
    }

    public Artist(JSONObject json) throws JSONException {
        super(json.getString("id"), json.getString("link"));
        setTitle(json.getString("artist"));
        setAlbumCover(json.getString("imageUrl"));
    }
    
    @Override
    public int getType(){
        return 1;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getSpotifyId());
        dest.writeString(getTitle());
        dest.writeString(getArtist());
        dest.writeString(getAlbumCover());
        dest.writeString(getSpotifyLink());
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {

        @Override
        public Artist createFromParcel(Parcel parcel) {
            return new Artist(parcel);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[0];
        }
    };
}
