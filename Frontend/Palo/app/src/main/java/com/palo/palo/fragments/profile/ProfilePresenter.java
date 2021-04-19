package com.palo.palo.fragments.profile;

import android.content.Context;

import com.palo.palo.model.Palo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.palo.palo.volley.ServerURLs.PICS;


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
        String url = "https://440b43ef-556f-4d7d-a95d-081ca321b8f9.mock.pstmn.io" + "/Palo?q=" + username;
        model.getUserPosts(url, username, this);
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
    }

    @Override
    public void onPostsSuccess(JSONArray response) throws JSONException {
        ArrayList<Palo> palos = new ArrayList<>();
        for (int i = response.length()-1; i >= 0; i--) {
            try {
                //todo update Palo constructor below to handle correct formated json response from server
                Palo palo = new Palo (response.getJSONObject(i), username, PICS + userId + "/" + userId);
//                model.getUserRequest(i, palo.getAuthor().getId(), this);
                if (palo.getAttachment().getSpotifyId() != null)
                    model.getAttachmentRequest(i, palo.getAttachment().getType(), palo.getAttachment().getSpotifyId(), this);
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
        view.updatePalo(paloIndex, palo);
    }
}
