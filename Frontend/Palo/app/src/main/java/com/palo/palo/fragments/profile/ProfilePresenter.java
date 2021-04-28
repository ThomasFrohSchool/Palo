package com.palo.palo.fragments.profile;

import android.content.Context;

import com.palo.palo.SharedPrefManager;
import com.palo.palo.model.Palo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.palo.palo.volley.ServerURLs.PICS;
import static com.palo.palo.volley.ServerURLs.POSTS_FROM_USER;


public class ProfilePresenter implements IProfilePresenter, IProfileVolleyListener {
    private IProfileView view;
    private ProfileModel model;
    private Context context;
    private int userId;
    private String username;
    
    public ProfilePresenter(IProfileView view, Context context, int userId, String username){
        this.view = view;
        this.model = new ProfileModel(context);
        this.context = context;
        this.userId = userId;
        this.username = username;
        loadProfile(userId);
        loadPosts(userId);
    }

    @Override
    public void loadProfile(int userId) {
        model.getProfile(userId, this);
    }

    @Override
    public void loadPosts(int userId) {
        //todo update path to server path
        System.out.println("cool stuff " + userId);
        String url = POSTS_FROM_USER + userId;
        model.getUserPosts(url, username, this);
    }

    @Override
    public void likePalo(int position, int paloId, int userId, boolean toLike) {
        if(toLike)
            model.addPaloLike(position, paloId, userId, this);
        else
            model.removePaloLike(position, paloId, userId, this);
    }

    @Override
    public void onEmptyResponse(String response) {
        view.loadEmptyPosts();
    }

    @Override
    public void onProfileSuccess(JSONObject response) throws JSONException {
//        str = response.getString("username"); return?
        view.setProfilePicture(PICS + userId + "/" + userId);
        view.setFollowersCount(String.valueOf(response.getJSONArray("followers").length()));
        view.setFollowingCount(String.valueOf(response.getJSONArray("following").length()));
        if(response.getString("bio").equalsIgnoreCase("null")) {
            view.setProfileBio("Set a bio here...");
        }else {
            view.setProfileBio(response.getString("bio"));
        }
    }

    /*@Override
    public void onEditBioSuccess() {

    }*/

    @Override
    public void onPostsSuccess(JSONArray response) {
        ArrayList<Palo> palos = new ArrayList<>();
        for (int i = response.length()-1; i >= 0; i--) {
            try {
                //todo handle likes in palo...
                Palo palo = new Palo (response.getJSONObject(i), username, PICS + userId + "/" + userId, SharedPrefManager.getInstance(context).getUser().getId());
//                model.getUserRequest(i, palo.getAuthor().getId(), this);
                if (palo.getAttachment().getSpotifyId() != null)
                    model.getAttachmentRequest(response.length()-1-i, palo.getAttachment().getType(), palo.getAttachment().getSpotifyId(), this);
                palos.add(palo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        view.setPaloCount(""+ palos.size());
        view.loadPalos(palos);
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
        view.makeToast(message);
    }

    @Override
    public void onAttachmentRequestSuccess(int paloIndex, int type, JSONObject response) throws JSONException {
        String name = (type == 1) ? "" : response.getString("name");
        Palo palo = view.getPalo(paloIndex);
        palo.updateAttachment(name, response.getString("artist"), response.getString("imageUrl"), response.getString("id"));
        if( type ==2) palo.updateAttachmentPlaybackLink(response.getString("playbackLink"));

        view.updatePalo(paloIndex, palo);
    }

    @Override
    public void onLikeRequestSuccess(int position, boolean isLiked) {
        view.updateLikeToPalo(position, isLiked);
    }
}
