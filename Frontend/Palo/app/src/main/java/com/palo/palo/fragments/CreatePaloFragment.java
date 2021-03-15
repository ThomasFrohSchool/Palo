package com.palo.palo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.JsonObjectRequest;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.MainActivity;
import com.palo.palo.model.Song;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.palo.palo.volley.ServerURLs.CREATE_POST;


public class CreatePaloFragment extends Fragment {
    EditText captionField;
    View myView;
    Song attatchment;
    
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
    
    public void post(){
        // sending: {"tempID": (int)userid, "description":"caption goes here", "spot_link": "spotifyID of song", "type":(int)attachmentType}
        // recieves currently {"message":"success"}
        JSONObject newPost = new JSONObject();
        try {
            newPost.put("tempID", SharedPrefManager.getInstance(myView.getContext()).getUser().getId());
//            newPost.put("postdate", new Date().getTime());
            newPost.put("description", captionField.getText().toString());
            newPost.put("spot_link", attatchment.getSpotifyId());
            newPost.put("type", attatchment.TYPE);


//            JSONObject song = new JSONObject();
//            song.put("title", ((TextView) myView.findViewById(R.id.songTitle)).getText().toString());
//            song.put("artist", ((TextView) myView.findViewById(R.id.songArtist)).getText().toString());
//            song.put("album_cover", ((TextView) myView.findViewById(R.id.coverImageURL)).getText().toString());
//            newPost.put("attachment", song);
//            System.out.println(newPost.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = CREATE_POST;
//        url = create_new_post;
        JsonObjectRequest request = new JsonObjectRequest(url, newPost, json -> {
            try {
                if(!json.getString("message").equals("success")) {
                    Toast.makeText(getActivity(), "successfully posted.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
//            System.out.println(error.getMessage());
        });
        VolleySingleton.getInstance(myView.getContext()).addToRequestQueue(request);
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    public void setAttatchment(Song song){
        this.attatchment = song;
        ((TextView) myView.findViewById(R.id.songTitle)).setText(attatchment.getTitle());
        ((TextView) myView.findViewById(R.id.songArtist)).setText(attatchment.getArtist());
        Picasso.get().load(attatchment.getAlbumCover()).into((ImageView) myView.findViewById(R.id.coverImage));
        ((TextView) myView.findViewById(R.id.coverImageURL)).setText(attatchment.getAlbumCover());
    }
}