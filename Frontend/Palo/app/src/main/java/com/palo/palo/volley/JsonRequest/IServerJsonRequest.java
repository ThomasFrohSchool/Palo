package com.palo.palo.volley.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public interface IServerJsonRequest {
    public void sendToServer(String url, JSONObject newUserObj, String methodType) throws JSONException;
    public void addVolleyListener(IVolleyJsonListener logic);
}