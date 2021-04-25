package com.palo.palo.fragments.profile;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;


import static com.palo.palo.volley.ServerURLs.ATTACHMENT;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

public class ProfileModel {
    private Context context;
    
    public ProfileModel(Context context){
        this.context = context;
    }
    
    public void getProfile(int userId, IProfileVolleyListener volleyListener){
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, USER_BY_ID + userId, null,
                response -> {
                    try {
                        volleyListener.onProfileSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(j);
    }

    public void getUserPosts(String url, String username, IProfileVolleyListener volleyListener){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        volleyListener.onPostsSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getAttachmentRequest(int paloIndex, int type, String spotifyId, IProfileVolleyListener volleyListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,ATTACHMENT(type) + spotifyId, null, response -> {
            try {
                volleyListener.onAttachmentRequestSuccess(paloIndex, type, response);
            } catch (JSONException e) {
                volleyListener.onError(response.toString());
            }
        }, error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
