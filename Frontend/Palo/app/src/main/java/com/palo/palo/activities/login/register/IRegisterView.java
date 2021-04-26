package com.palo.palo.activities.login.register;

import com.palo.palo.model.User;

public interface IRegisterView {
    public void makeToast(String message);
    public void logd(String response);
    public void startNewActivity(Class<?> cls);
    public void postRegister();
    public void login(User user);
    public void dismissKeyboard();
}
