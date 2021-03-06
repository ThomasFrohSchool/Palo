package com.palo.palo.fragments.directMessage;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class DirectMessagePresenter implements IDirectMessagePresenter, IDirectMessageVolleyListener{
    private IDirectMessageView view;
    private DirectMessageModel model;
    private Context context;
    private String usernameSearching;

    public DirectMessagePresenter(IDirectMessageView view, Context context){
        this.view = view;
        this.model = new DirectMessageModel(context);
        this.context = context;
        this.usernameSearching = usernameSearching;
        this.loadUsers();
    }

    @Override
    public void loadUsers() {
        model.getUsersRequest(this);
    }

    @Override
    public void loadUsersSearch(String usernameSearching) {
       model.getUsersSearchRequest(usernameSearching, this);
    }

    @Override
    public void onUserSearchSuccess(JSONArray response) throws JSONException {
        List<String> usernames = new ArrayList<>();
        for(int i =0; i<response.length(); i++){
            usernames.add(response.getJSONObject(i).getString("username"));
        }
        view.dismissKeyboard();
        view.loadUsersSearch(usernames);
    }

    @Override
    public void onUserWithMessagesSuccess(JSONArray response) throws JSONException {
        List<String> usernames = new ArrayList<>();
        for(int i = 0; i < response.length(); i++) {
            usernames.add(response.getString(i));
        }
        view.dismissKeyboard();
        view.loadUsers(usernames);
    }

    @Override
    public void onSearchError(String message) {
        view.makeToast(message);
    }
}
