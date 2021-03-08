package com.palo.palo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.MainActivity;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class CreatePaloFragment extends Fragment {
    Button postButton;
    EditText captionField;
    EditText searchField;
    ImageButton searchButton;
    RecyclerView searchRecyclerView;
    
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
        captionField = view.findViewById(R.id.caption_create_new_post);
        
        searchField = view.findViewById(R.id.attached_song_search_bar_edit_text);
        searchButton = view.findViewById(R.id.attached_song_search_bar_button);
        searchButton.setOnClickListener(v -> search(view));
        
        Button cancel = view.findViewById(R.id.cancel_create_new_post);
        cancel.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getActivity(), "cancel clicked.", Toast.LENGTH_LONG);
            toast.show();
            startActivity(new Intent(getActivity(), MainActivity.class));
        });
        postButton = view.findViewById(R.id.post_create_new_post);
        postButton.setOnClickListener(v -> post(view));
    }
    private void search(View view){
        String searchText = searchField.getText().toString();
        JsonArrayRequest request = searchRequest(searchText);
        //jsonArrayRequest
        //make request
        //update searchRecycleView with results from request...
    }
    
    private void post(View v){
        // sending: {"author_id": "", "postdate":"", "caption":"", "attachment":{"title":"","artist":"",album_cover":""}}
        // recieves currently {"error":"boolean"}
        JSONObject newPost = new JSONObject();
        try {
            newPost.put("author_id", SharedPrefManager.getInstance(v.getContext()).getUser().getId());
            newPost.put("postdate", new Date().getTime());
            newPost.put("caption", captionField.getText().toString());

            JSONObject song = new JSONObject();
            song.put("title", ((TextView) v.findViewById(R.id.songTitle)).getText().toString());
            song.put("artist", ((TextView) v.findViewById(R.id.songArtist)).getText().toString());
            song.put("album_cover", ((TextView) v.findViewById(R.id.coverImageURL)).getText().toString());
            newPost.put("attachment", song);
            System.out.println(newPost.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(create_new_post, newPost, json -> {
            try {
                if(!json.getBoolean("error")) {
                    Toast.makeText(getActivity(), "successfully posted.", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show());
        VolleySingleton.getInstance(v.getContext()).addToRequestQueue(request);
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
    private  JsonArrayRequest searchRequest(String text){
        return null;
    }
}