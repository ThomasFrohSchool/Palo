package com.palo.palo.fragments.feed;

import android.content.Context;

import com.palo.palo.model.Palo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedPresenter implements IFeedPresenter, IFeedVolleyListener {
    private IFeedView view;
    private FeedModel model;
    private Context context;
    private int currentUserId;

    public FeedPresenter(IFeedView view, Context context, int currentUserId){
        this.view = view;
        this.model = new FeedModel(context);
        this.context = context;
        this.currentUserId = currentUserId;
        loadFeed(currentUserId);
    }

    @Override
    public void loadFeed(int currentUserId) {
        model.getFeed(currentUserId, this);
    }

    @Override
    public void onEmptyResponse(String response) {
        view.loadEmptyFeed();
    }

    @Override
    public void onSuccess(JSONArray response) throws JSONException {
        ArrayList<Palo> palos = new ArrayList<>();
        for (int i = response.length()-1; i >= 0; i--) {
            try {
                Palo palo = new Palo (response.getJSONObject(i));
                model.getUserRequest(i, palo.getAuthor().getId(), this);
                if (palo.getAttachment().getSpotifyId() != null)
                    model.getAttachmentRequest(i, palo.getAttachment().getType(), palo.getAttachment().getSpotifyId(), this);
                palos.add(palo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        view.loadPalos(palos);
    }

    @Override
    public void onError(String message) {
        view.makeToast(message);
        //TODO fix error in the toast function
        System.out.println("Error in the feed fragment with toasts");
    }

    @Override
    public void onUserRequestSuccess(int paloIndex, JSONObject response) throws JSONException {
        Palo palo = view.getPalo(paloIndex);
        palo.setAuthorUsername(response.getString("username"));
        // TODO set profile image as well...
        view.updatePalo(paloIndex, palo);
    }


    @Override
    public void onAttachmentRequestSuccess(int paloIndex, int type, JSONObject response) throws JSONException {
        String name = (type == 1) ? "" : response.getString("name");
        Palo palo = view.getPalo(paloIndex);
        palo.updateAttachment(name, response.getString("artist"), response.getString("imageUrl"), response.getString("id"));
        view.updatePalo(paloIndex, palo);
    }
}

