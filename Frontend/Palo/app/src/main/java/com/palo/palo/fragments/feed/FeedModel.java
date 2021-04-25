package com.palo.palo.fragments.feed;

import android.content.Context;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;

import static com.palo.palo.volley.ServerURLs.ADD_LIKE;
import static com.palo.palo.volley.ServerURLs.ATTACHMENT;
import static com.palo.palo.volley.ServerURLs.FEED;
import static com.palo.palo.volley.ServerURLs.REMOVE_LIKE;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

public class FeedModel {
    private Context context;

    public FeedModel(Context context){
        this.context = context;
    }


    public void getFeed(int currentUserId, IFeedVolleyListener volleyListener){
        JsonArrayRequest feedRequest = new JsonArrayRequest(Request.Method.GET, FEED + currentUserId, null, response -> {
            if (response.length() == 0 ){
                volleyListener.onEmptyResponse(response.toString());
            } else {
                try {
                    volleyListener.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(feedRequest);
    }

    public void getUserRequest(int paloIndex, int userId, IFeedVolleyListener volleyListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,USER_BY_ID + userId, null, response -> {
            try {
                volleyListener.onUserRequestSuccess(paloIndex, response);
            } catch (JSONException e) {
                volleyListener.onError(response.toString());
            }
        }, error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getAttachmentRequest(int paloIndex, int type, String spotifyId, IFeedVolleyListener volleyListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,ATTACHMENT(type) + spotifyId, null, response -> {
            try {
                volleyListener.onAttachmentRequestSuccess(paloIndex, type, response);
            } catch (JSONException e) {
                volleyListener.onError(response.toString());
            }
        }, error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void addPaloLike(int position, int paloId, int userId, IFeedVolleyListener volleyListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ADD_LIKE(paloId, userId), null, response -> {
            try {
                if(response.getString("message").equals("success"))
                    volleyListener.onLikeRequestSuccess(position, true);
            } catch (JSONException e) {
                volleyListener.onError(response.toString());
            }
        }, error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


    public void removePaloLike(int position, int paloId, int userId, IFeedVolleyListener volleyListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, REMOVE_LIKE(paloId, userId), null, response -> {
            try {
                if(response.getString("message").equals("success"))
                    volleyListener.onLikeRequestSuccess(position, false);
            } catch (JSONException e) {
                volleyListener.onError(response.toString());
            }
        }, error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
