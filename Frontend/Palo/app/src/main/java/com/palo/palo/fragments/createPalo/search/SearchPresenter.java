package com.palo.palo.fragments.createPalo.search;

import android.content.Context;

import com.palo.palo.model.Album;
import com.palo.palo.model.Artist;
import com.palo.palo.model.Attachment;
import com.palo.palo.model.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements ISearchPresenter, ISpotifySearchVolleyListener{
    private ISearchView view;
    private SearchModel model;
    private Context context;

    public SearchPresenter(ISearchView view, Context context) {
        this.view = view;
        this.model = new SearchModel(context);
        this.context = context;
    }
    @Override
    public void onSearchSuccess(String response) throws JSONException {
        System.out.println(response);
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
        view.loadAttatchments(attachments);
    }

    @Override
    public void onSearchError(String message) {
        view.makeToast(message);
    }

    @Override
    public void loadSearchResults(String searchMessage) {
        model.getSpotifySearchResults(searchMessage, this);
    }
}
