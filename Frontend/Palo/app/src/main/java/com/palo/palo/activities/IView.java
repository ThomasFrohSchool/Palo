package com.palo.palo.activities;

import com.palo.palo.model.User;

public interface IView {
    public void showText(String s);
    public void toastText(String s);
    public void startActivity(Class<?> cls);
    public void login(User user);
}
