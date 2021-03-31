package com.palo.palo.volley.JsonRequest;


import org.json.JSONException;
import org.json.JSONObject;

public interface IVolleyJsonListener {
    public void onSuccess(JSONObject jsonObject) throws JSONException;
    public void onError(String s);
}
