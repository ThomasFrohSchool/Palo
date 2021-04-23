package com.palo.palo.fragments.searchPage;

import com.palo.palo.model.Attachment;
import com.palo.palo.model.User;

import java.util.List;

public interface ISearchView {
    public void loadAttachments(List<Attachment> attachments);
    public void loadUsers(List<User> users);
    public void makeToast(String message);
    public void logd(String response);
    public void dismissKeyboard();
}
