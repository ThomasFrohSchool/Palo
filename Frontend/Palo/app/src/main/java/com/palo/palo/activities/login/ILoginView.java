package com.palo.palo.activities.login;

import com.palo.palo.model.User;

public interface ILoginView {
    public void makeToast(String message);
    public void logd(String response);
    public void startNewActivity();
    public void postLogin();
    public void login(User user);
    public void dismissKeyboard();
}
