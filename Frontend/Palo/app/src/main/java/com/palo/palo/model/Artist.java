package com.palo.palo.model;

import android.os.Parcel;

/**
 * Model for artist attachment recieved from spotify.
 */
public class Artist extends Attachment {

    public Artist(){}
    public Artist(String spotifyId){
        super(spotifyId);
    }

    public Artist(Parcel parcel){
        //TODO
    }
    
    @Override
    public int getType(){
        return 1;
    }
}
