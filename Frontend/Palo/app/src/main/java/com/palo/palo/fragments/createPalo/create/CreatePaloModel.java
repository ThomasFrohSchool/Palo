package com.palo.palo.fragments.createPalo.create;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;

import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import static com.palo.palo.volley.ServerURLs.CREATE_POST;

public class CreatePaloModel {
    private Context context;

    public CreatePaloModel(Context context){ this.context = context; }

    public void postNewPalo(JSONObject newPalo, int currentUserId, ICreatePaloVolleyListener volleyListener){
        String url = CREATE_POST + currentUserId;
        JsonObjectRequest request = new JsonObjectRequest(url,
                newPalo,
                json -> {
                            try {
                                if(json.getString("message").equals("success"))
                                    volleyListener.onPostSuccess("successfully posted.");
                                else
                                    volleyListener.onPostSuccess("error posting. try again.");
                            } catch (JSONException e) {
                                volleyListener.onPostError(e.getMessage());
                            }
                        },
                 error -> volleyListener.onPostError(error.getMessage()));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
