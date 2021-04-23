package com.palo.palo.activities.login;

import android.content.Context;
import com.palo.palo.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter implements  ILoginPresenter, ILoginVolleyListener{
    private ILoginView view;
    private LoginModel model;
    private Context context;

    public LoginPresenter(ILoginView view, Context context){
        this.view = view;
        this.model = new LoginModel(context);
        this.context = context;
    }

    @Override
    public void login(JSONObject loginJson) {
        model.login(loginJson, this);
    }

    @Override
    public void onSuccess(JSONObject userJson) throws JSONException {
        User user = new User(Integer.parseInt(userJson.getString("id")), userJson.getString("username"), userJson.getString("email"));
        view.login(user);
        view.startNewActivity();
    }

    @Override
    public void onError(String message) {
        view.makeToast(message);
    }
}
