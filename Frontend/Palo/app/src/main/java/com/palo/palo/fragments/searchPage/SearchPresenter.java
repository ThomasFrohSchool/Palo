package com.palo.palo.fragments.searchPage;

import android.content.Context;

import com.palo.palo.model.Album;
import com.palo.palo.model.Artist;
import com.palo.palo.model.Attachment;
import com.palo.palo.model.Song;
import com.palo.palo.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements ISearchPresenter, ISearchVolleyListener {
    private ISearchView view;
    private SearchModel model;
//    private Context context;

    public SearchPresenter(ISearchView view, Context context) {
        this.view = view;
        this.model = new SearchModel(context);
//        this.context = context;
    }
    @Override
    public void onSpotifySearchSuccess(String response) throws JSONException {
        view.logd(response);
        JSONObject json = new JSONObject(response);
        List<Attachment> attachments = new ArrayList<>();
        // albums
        JSONArray jsonArray = json.getJSONArray("albums");
        for (int i =0; i <jsonArray.length(); i++)
            attachments.add(new Album(jsonArray.getJSONObject(i)));
        // artist
        jsonArray = json.getJSONArray("artists");
        for (int i =0; i <jsonArray.length(); i++)
            attachments.add(new Artist(jsonArray.getJSONObject(i)));
        // tracks
        jsonArray = json.getJSONArray("tracks");
        for (int i =0; i <jsonArray.length(); i++)
            attachments.add(new Song(jsonArray.getJSONObject(i)));
        view.dismissKeyboard();
        view.loadAttachments(attachments);
    }

    @Override
    public void onUserSearchSuccess(JSONArray jsonArray, String username) throws JSONException {
        view.logd(jsonArray.toString());
        List<User> users = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = jsonArray.getJSONObject(i);
            if(username.equalsIgnoreCase(userJson.getString("username"))) {
                users.add(new User(userJson));
            }
        }
        view.dismissKeyboard();
        view.loadUsers(users);
    }

    @Override
    public void onSearchError(String message) {
        view.makeToast(message);
    }

    @Override
    public void loadSpotifySearchResults(String searchMessage) {
        model.getSpotifySearchResults(searchMessage, this);
    }

    @Override
    public void loadUserSearchResults(String searchMessage) {
        model.getUserSearchResults(searchMessage, this);
    }
}
