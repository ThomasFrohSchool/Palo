package com.palo.palo.fragments.feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IFeedVolleyListener {
    public void onEmptyResponse(String response);
    public void onSuccess(JSONArray response) throws JSONException;
    public void onError(String message);
    public void onUserRequestSuccess(int paloIndex, JSONObject response) throws JSONException;
    public void onAttachmentRequestSuccess(int paloIndex, int type, JSONObject response) throws JSONException;
    public void onLikeRequestSuccess(int position, boolean isLiked);
}
