package com.palo.palo.activities.login.register;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.activities.login.ILoginVolleyListener;
import com.palo.palo.volley.ServerURLs;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterModel {
    private Context context;

    public RegisterModel(Context context){
        this.context = context;
    }

    public void register(JSONObject paloJson, ILoginVolleyListener volleyListener){
        JsonObjectRequest request = new JsonObjectRequest(ServerURLs.REGISTER, paloJson, response -> {
            try {
                if(!response.getBoolean("error")) {
                    volleyListener.onSuccess(response.getJSONObject("user"));
                } else {
                    volleyListener.onError(response.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> volleyListener.onError(error.getMessage()));

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
