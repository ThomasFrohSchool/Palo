package com.palo.palo.activities.login.register;

import android.content.Context;

import com.palo.palo.activities.MainActivity;
import com.palo.palo.activities.login.ILoginVolleyListener;
import com.palo.palo.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPresenter implements  IRegisterPresenter, ILoginVolleyListener {
    private IRegisterView view;
    private RegisterModel model;
    private Context context;

    public RegisterPresenter(IRegisterView view, Context context){
        this.view = view;
        this.model = new RegisterModel(context);
        this.context = context;
    }

    @Override
    public void onSuccess(JSONObject userJson) throws JSONException {
        User user = new User(Integer.parseInt(userJson.getString("id")), userJson.getString("username"), userJson.getString("email"));
        view.login(user);
        view.startNewActivity(MainActivity.class);
    }

    @Override
    public void onError(String message) {
        view.makeToast(message);
    }


    @Override
    public void register(JSONObject registerJson) {
        model.register(registerJson, this);
    }
}
