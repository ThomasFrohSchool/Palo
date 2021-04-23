package com.palo.palo.volley.JsonRequest;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.volley.AppController;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerJsonRequest implements IServerJsonRequest {
    private String tag_json_obj = "json_obj_req";
    private IVolleyJsonListener volleyListener;
    
    public void sendToServer(String url, JSONObject newUserObj, String methodType) throws JSONException {
        int method = (methodType.equals("POST")) ? Request.Method.POST : Request.Method.GET;

        JsonObjectRequest registerUserRequest = new JsonObjectRequest(method, url, newUserObj,
                response -> {
                    try {
                        if(response != null)  volleyListener.onSuccess(response);
                        else volleyListener.onError("Null Response object received");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> volleyListener.onError(error.getMessage()));
        AppController.getInstance().addToRequestQueue(registerUserRequest, tag_json_obj);
    }

    public void addVolleyListener(IVolleyJsonListener logic) {
        volleyListener = logic;
    }
}
