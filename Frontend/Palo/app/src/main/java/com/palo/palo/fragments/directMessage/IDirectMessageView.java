package com.palo.palo.fragments.directMessage;


import java.util.List;

public interface IDirectMessageView {
    public void loadUsers(List<String> usernames);
    public void loadUsersSearch(List<String> users);
    public void makeToast(String message);
    public void logd(String response);
    public void dismissKeyboard();
}
