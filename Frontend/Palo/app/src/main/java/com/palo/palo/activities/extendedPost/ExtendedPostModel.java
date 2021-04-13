package com.palo.palo.activities.extendedPost;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;


import static com.palo.palo.volley.ServerURLs.CREATE_COMMENT;
import static com.palo.palo.volley.ServerURLs.GET_COMMENTS;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

public class ExtendedPostModel {
    private Context context;

    public ExtendedPostModel(Context context){
        this.context = context;
    }

    public void postComment(JSONObject commentJson, int postId,  IExtendedPostVolleyListener volleyListener){
        String url = CREATE_COMMENT + postId;
        JsonObjectRequest request = new JsonObjectRequest(url, commentJson,
                json -> {
                    try {
                        if(json.getString("message").equals("success"))
                            volleyListener.onPostSuccess("successfully posted.");
                        else
                            volleyListener.onPostSuccess("error posting comment. try again.");
                    } catch (JSONException e) {
                        volleyListener.onError(e.getMessage());
                    }
                },
                error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getComments(int postId, IExtendedPostVolleyListener volleyListener){
        String url = GET_COMMENTS + postId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    if (response.length() == 0){
                        volleyListener.onError("NO COMMENTS FOR POST");
                    } else {
                        try {
                            volleyListener.onCommentLoadSuccess(response);
                        } catch (JSONException e) {
                            volleyListener.onError(e.getMessage());
                        }
                    }
                },
                error -> volleyListener.onError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void updateUserInfo(int commentIndex, int userId, IExtendedPostVolleyListener volleyListener){
        String url = USER_BY_ID + userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null,
                response -> {
                    try {
                        volleyListener.onUserSuccess(response, commentIndex);
                    } catch (JSONException e) {
                        volleyListener.onError(e.getMessage());
                    }
                 },
                error -> volleyListener.onError(error.getMessage()));

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
