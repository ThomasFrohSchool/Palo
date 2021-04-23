package com.palo.palo.fragments.createPalo.create;

import com.palo.palo.model.Attachment;

public interface ICreatePaloView {
    public void makeToast(String message);
    public void startNewActivity();
    public void loadAttatchment(Attachment attachment);
    public void postPalo();
    public void dismissKeyboard();
}
