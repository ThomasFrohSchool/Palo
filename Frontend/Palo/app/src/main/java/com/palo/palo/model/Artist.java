package com.palo.palo.model;

public class Artist extends Attatchment{

    public Artist(){}
    public Artist(String spotifyId){
        super(spotifyId);
    }
    
    @Override
    public int getType(){
        return 1;
    }
}
