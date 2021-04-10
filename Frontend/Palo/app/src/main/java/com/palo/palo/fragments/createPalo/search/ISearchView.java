package com.palo.palo.fragments.createPalo.search;

import com.palo.palo.model.Attachment;

import java.util.List;

public interface ISearchView {
    public void loadAttatchments(List<Attachment> attachments);
    public void makeToast(String message);
    public void dismissKeyboard();
}
