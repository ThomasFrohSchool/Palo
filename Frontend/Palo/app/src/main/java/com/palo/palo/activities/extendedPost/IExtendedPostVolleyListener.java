package com.palo.palo.activities.extendedPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IExtendedPostVolleyListener {
    public void onCommentLoadSuccess(JSONArray response) throws JSONException;
    public void onPostSuccess(String message);
    public void onUserSuccess(JSONObject jsonObject, int commentIndex) throws JSONException;
    public void onError(String message);
}
