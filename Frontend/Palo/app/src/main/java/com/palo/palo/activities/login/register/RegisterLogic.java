package com.palo.palo.activities.login.register;

import com.palo.palo.activities.IView;
import com.palo.palo.activities.MainActivity;
import com.palo.palo.activities.login.LoginActivity;
import com.palo.palo.model.User;
import com.palo.palo.volley.JsonRequest.IServerJsonRequest;
import com.palo.palo.volley.JsonRequest.IVolleyJsonListener;
import com.palo.palo.volley.ServerURLs;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterLogic implements IVolleyJsonListener {
    IView r;
    IServerJsonRequest serverRequest;

    public RegisterLogic(RegisterActivity r, IServerJsonRequest serverRequest){
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    public void register(String username, String email, String password) throws JSONException {
        
        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("email", email);

        serverRequest.sendToServer(ServerURLs.REGISTER, newUser, "POST");
    }

    @Override
    public void onSuccess(JSONObject response) throws JSONException {
        if(!response.getBoolean("error")) {
            JSONObject userJson = response.getJSONObject("user");
            User user = new User(Integer.parseInt(userJson.getString("id")), userJson.getString("username"), userJson.getString("email"));
            r.login(user);
            r.startActivity(MainActivity.class);
        }
    }
    
    @Override
    public void onError (String errorMessage){
        r.toastText(errorMessage);
        r.startActivity(LoginActivity.class);
    }
}
