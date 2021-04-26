package com.palo.palo.fragments.feed;

import android.content.Context;

import com.palo.palo.model.Palo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.palo.palo.volley.ServerURLs.PICS;

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
    public void likePalo(int position, int paloId, int userId, boolean toLike) {
        if(toLike)
            model.addPaloLike(position, paloId, userId, this);
        model.removePaloLike(position, paloId, userId, this);
    }

    @Override
    public void onEmptyResponse(String response) {
        view.loadEmptyFeed();
    }

    @Override
    public void onSuccess(JSONArray response) throws JSONException {
        ArrayList<Palo> palos = new ArrayList<>();
        int a = 0;
        for (int i = response.length()-1; i >= 0; i--) {
            try {
                Palo palo = new Palo (response.getJSONObject(i), currentUserId);
                model.getUserRequest((response.length()-1-i), palo.getAuthor().getId(), this);
                if (palo.getAttachment().getSpotifyId() != null)
                    model.getAttachmentRequest((response.length()-1-i), palo.getAttachment().getType(), palo.getAttachment().getSpotifyId(), this);
                palos.add(palo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            a++;
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
        palo.setPaloAuthorProfileImage(PICS + response.getString("id") + "/" + response.getString("id"));
        view.updatePalo(paloIndex, palo);
    }


    @Override
    public void onAttachmentRequestSuccess(int paloIndex, int type, JSONObject response) throws JSONException {
        String name = (type == 1) ? response.getString("artist") : response.getString("name");
        String artist = (type == 1) ? " " : response.getString("artist");

        System.out.println(type + " " + name);
        Palo palo = view.getPalo(paloIndex);
        palo.updateAttachment(name, artist, response.getString("imageUrl"), response.getString("id"));
        if( type ==2) palo.updateAttachmentPlaybackLink(response.getString("playbackLink"));
        view.updatePalo(paloIndex, palo);
    }

    @Override
    public void onLikeRequestSuccess(int position,  boolean isLiked) {
        //todo refresh palo in recycler view
        view.updateLikeToPalo(position, isLiked);
    }
}

