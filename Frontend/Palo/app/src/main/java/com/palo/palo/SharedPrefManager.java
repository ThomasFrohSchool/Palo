package com.palo.palo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.palo.palo.activities.login.LoginActivity;
import com.palo.palo.model.User;

//Shared Preferences allow you to save and retrieve data as key-value pairs
public class SharedPrefManager {
    private static final String LOGIN_REGISTER_KEY = "loginRegisterKey";
    private static final String USERNAME_KEY = "usernameKey";
    private static final String EMAIL_KEY = "emailKey";
    private static final String ID_KEY = "idKey";

    private static SharedPrefManager instance;
    private static Context context;

    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static SharedPrefManager getInstance(Context c) {
        if (instance == null) instance = new SharedPrefManager(c);
        return instance;
    }

    public void login(User user) {
        SharedPreferences sharedPref = context.getSharedPreferences(LOGIN_REGISTER_KEY, Context.MODE_PRIVATE);
        Editor editor = sharedPref.edit();
        editor.putInt(ID_KEY, user.getId());
        editor.putString(USERNAME_KEY, user.getUsername());
        editor.putString(EMAIL_KEY, user.getEmail());
        editor.apply();
    }

    public void logout() {
        SharedPreferences sharedPref = context.getSharedPreferences(LOGIN_REGISTER_KEY, Context.MODE_PRIVATE);
        Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPref = context.getSharedPreferences(LOGIN_REGISTER_KEY, Context.MODE_PRIVATE);
        return sharedPref.getString(USERNAME_KEY, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPref = context.getSharedPreferences(LOGIN_REGISTER_KEY, Context.MODE_PRIVATE);
        return new User(sharedPref.getInt(ID_KEY, -1), sharedPref.getString(USERNAME_KEY, null), sharedPref.getString(EMAIL_KEY, null));
    }
}
