package com.palo.palo.fragments.createPalo.search;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;

import static com.palo.palo.volley.ServerURLs.SEARCH;

public class SearchModel {
    private Context context;

    public SearchModel(Context context){ this.context = context; }

    public void getSpotifySearchResults(String seachMessage, ISpotifySearchVolleyListener volleyListener){
        String url = SEARCH + seachMessage;
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    try {
                        volleyListener.onSearchSuccess(response);
                    } catch (JSONException e) {
                        volleyListener.onSearchError(e.getMessage());
                    }
                }, error -> volleyListener.onSearchError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
