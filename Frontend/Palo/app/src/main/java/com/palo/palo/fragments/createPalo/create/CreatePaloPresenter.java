package com.palo.palo.fragments.createPalo.create;

import android.content.Context;

import org.json.JSONObject;

public class CreatePaloPresenter implements ICreatePaloPresenter, ICreatePaloVolleyListener{
    private ICreatePaloView view;
    private CreatePaloModel model;
    private Context context;
    private int currentUserId;

    public CreatePaloPresenter(ICreatePaloView view, Context context, int currentUserId) {
        this.view = view;
        this.model = new CreatePaloModel(context);
        this.context = context;
        this.currentUserId = currentUserId;
    }

    @Override
    public void onPostSuccess(String message) {
        view.makeToast(message);
        view.startNewActivity();
    }

    @Override
    public void onPostError(String message) {
        view.makeToast(message);
    }

    @Override
    public void postPalo(JSONObject paloJson) {
        model.postNewPalo(paloJson, currentUserId, this);
    }
}
