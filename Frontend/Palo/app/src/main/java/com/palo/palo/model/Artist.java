package com.palo.palo.model;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class Artist extends Attachment {

    public Artist(){}
    public Artist(String spotifyId){
        super(spotifyId);
    }

    public Artist(Parcel parcel){
        //TODO
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
}
