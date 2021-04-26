package com.palo.palo.fragments.directMessage;

import org.json.JSONArray;
import org.json.JSONException;

public interface IDirectMessageVolleyListener {
    public void onUserSearchSuccess(JSONArray response) throws JSONException;
    public void onUserWithMessagesSuccess(JSONArray response) throws JSONException;
    public void onSearchError(String message);
}
