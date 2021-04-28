package com.palo.palo.fragments.searchPage;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;

import static com.palo.palo.volley.ServerURLs.SEARCH;
import static com.palo.palo.volley.ServerURLs.SEARCH_BY_USERNAME;
import static com.palo.palo.volley.ServerURLs.USERS;

public class SearchModel {
    private Context context;

    public SearchModel(Context context){ this.context = context; }

    /**
     * Makes StringRequest to SERVER/search?q=[TEXT], where TEXT is a string (searchMessage) to what the user is searching.
     * Adds request to VolleySingleton request queue. Server responds with a string containing a list of albums, songs, and artist from Spotify.
     */
    public void getSpotifySearchResults(String searchMessage, ISearchVolleyListener volleyListener){
        String url = SEARCH + searchMessage;
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    try {
                        volleyListener.onSpotifySearchSuccess(response);
                    } catch (JSONException e) {
                        volleyListener.onSearchError(e.getMessage());
                    }
                }, error -> volleyListener.onSearchError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getUserSearchResults(String searchMessage, ISearchVolleyListener volleyListener){
//        String url = USERS + searchMessage;
        String url = SEARCH_BY_USERNAME + searchMessage;
        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    try {
                        volleyListener.onUserSearchSuccess(response);
                    } catch (JSONException e) {
                        volleyListener.onSearchError(e.getMessage());
                    }
                }, error -> volleyListener.onSearchError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
