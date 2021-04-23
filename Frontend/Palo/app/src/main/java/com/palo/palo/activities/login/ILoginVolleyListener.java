package com.palo.palo.activities.login;

import org.json.JSONException;
import org.json.JSONObject;

public interface ILoginVolleyListener {
    public void onSuccess(JSONObject userJson) throws JSONException;
    public void onError(String message);
}
