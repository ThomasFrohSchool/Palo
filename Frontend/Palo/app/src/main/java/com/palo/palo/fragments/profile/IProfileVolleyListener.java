package com.palo.palo.fragments.profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IProfileVolleyListener {
    public void onEmptyResponse(String response);
    public void onProfileSuccess(JSONObject response) throws JSONException;
    public void onPostsSuccess(JSONArray response) throws JSONException;

    public void onError(String message);
    public void onAttachmentRequestSuccess(int paloIndex, int type, JSONObject response) throws JSONException;

    public void onLikeRequestSuccess(int position, boolean isLiked);
}
