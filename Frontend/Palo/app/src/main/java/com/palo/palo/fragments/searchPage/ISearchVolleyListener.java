package com.palo.palo.fragments.searchPage;

import org.json.JSONArray;
import org.json.JSONException;

public interface ISearchVolleyListener {
    public void onSpotifySearchSuccess(String response) throws JSONException;
    public void onUserSearchSuccess(JSONArray response, String username) throws JSONException;
    public void onSearchError(String message);
}
