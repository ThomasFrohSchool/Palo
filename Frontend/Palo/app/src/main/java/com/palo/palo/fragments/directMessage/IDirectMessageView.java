package com.palo.palo.fragments.directMessage;

import com.palo.palo.model.User;

import java.util.List;

public interface IDirectMessageView {
    public void loadUsers(List<User> users);
    public void makeToast(String message);
    public void logd(String response);
    public void dismissKeyboard();
}
