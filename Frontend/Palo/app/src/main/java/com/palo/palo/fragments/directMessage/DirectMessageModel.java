package com.palo.palo.fragments.directMessage;

import android.content.Context;
import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.volley.VolleySingleton;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;

import java.net.URI;
import java.net.URISyntaxException;

import static com.palo.palo.volley.ServerURLs.DM_LIST;
import static com.palo.palo.volley.ServerURLs.SEARCH_BY_USERNAME;

public class DirectMessageModel {
    private Context context;

    public DirectMessageModel(Context context){
        this.context = context;
    }

    public void getUsersRequest( IDirectMessageVolleyListener volleyListener){
        //todo fix end point
        String url = DM_LIST + SharedPrefManager.getInstance(context).getUser().getUsername();
        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    try {
                        volleyListener.onUserWithMessagesSuccess(response);
                    } catch (JSONException e) {
                        volleyListener.onSearchError(e.getMessage());
                    }
                }, error -> volleyListener.onSearchError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getUsersSearchRequest(String searchMessage, IDirectMessageVolleyListener volleyListener){
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
