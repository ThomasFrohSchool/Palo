package com.palo.palo.fragments.createPalo.search;

import org.json.JSONException;

public interface ISpotifySearchVolleyListener {
    public void onSearchSuccess(String response) throws JSONException;
    public void onSearchError(String message);
}
