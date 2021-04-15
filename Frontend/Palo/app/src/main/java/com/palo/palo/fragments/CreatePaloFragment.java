package com.palo.palo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.JsonObjectRequest;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.*;
import com.palo.palo.model.Attachment;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.palo.palo.volley.ServerURLs.CREATE_POST;

/**
 * This fragment is for creating a post. Allows user to add caption and the ability to post a new Palo.
 * This class is associated with fragment_create_palo.xml.
 */
public class CreatePaloFragment extends Fragment {
    EditText captionField;
    View myView;
    Attachment attachment;
    
    // temp server url's
    private static String server_url = "https://9eddb02a-d334-4848-ab3a-744f07eb89d2.mock.pstmn.io";
    private static String create_new_post = server_url +"/create_post";

    public CreatePaloFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_palo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        myView = view;
        captionField = view.findViewById(R.id.caption_create_new_post);
    }

    /**
     * Makes post request to server to make a new post.
     * Makes request to "{server_url}/createPost/{user_id}".
     */
    public void post(){
        // receives currently {"message":"success"}
        JSONObject newPost = new JSONObject();
        try {
//            newPost.put("tempID", SharedPrefManager.getInstance(myView.getContext()).getUser().getId());
            newPost.put("description", captionField.getText().toString());
            newPost.put("spot_id", attachment.getSpotifyId());
            newPost.put("type", attachment.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = CREATE_POST + SharedPrefManager.getInstance(myView.getContext()).getUser().getId();
        JsonObjectRequest request = new JsonObjectRequest(url, newPost, json -> {
            try {
                if(!json.getString("message").equals("success")) {
                    Toast.makeText(getActivity(), "successfully posted.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            System.out.println(error.getMessage());
        });
        VolleySingleton.getInstance(myView.getContext()).addToRequestQueue(request);
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    /**
     * Sets attachment based off chosen attachment from create post spotify search fragment.
     * @param a selected Spotify attachment (song, album, or artist)
     */
    public void setAttachment(Attachment a){
        this.attachment = a;
        ((TextView) myView.findViewById(R.id.songTitle)).setText(attachment.getTitle());
        ((TextView) myView.findViewById(R.id.songArtist)).setText(attachment.getArtist());
        Picasso.get().load(attachment.getAlbumCover()).into((ImageView) myView.findViewById(R.id.coverImage));
        ((TextView) myView.findViewById(R.id.coverImageURL)).setText(attachment.getAlbumCover());
    }
}