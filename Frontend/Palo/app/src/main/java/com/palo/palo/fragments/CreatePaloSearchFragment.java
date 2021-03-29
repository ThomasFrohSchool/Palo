package com.palo.palo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.palo.palo.AttachementSearchAdapter;

import com.palo.palo.R;
import com.palo.palo.model.Album;
import com.palo.palo.model.Artist;
import com.palo.palo.model.Attatchment;
import com.palo.palo.model.Song;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONArray;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.palo.palo.volley.ServerURLs.SEARCH;


public class CreatePaloSearchFragment extends Fragment {
    private RecyclerView searchRecyclerView;
    private EditText searchET;
    private ImageButton searchButton;
    AttachementSearchAdapter searchAdapter;

    public CreatePaloSearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_palo_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        searchET = view.findViewById(R.id.attached_song_search_bar_edit_text);
        searchButton = view.findViewById(R.id.attached_song_search_bar_button);
        searchButton.setOnClickListener(v -> getSearchResultsFromSpotify());
        searchRecyclerView = view.findViewById(R.id.create_new_post_search_recycler_view);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        searchRecyclerView.setAdapter(new AttachementSearchAdapter(getActivity().getApplicationContext(), new ArrayList<>()));
    }

    private void getSearchResultsFromSpotify() {
        String url = SEARCH + searchET.getText().toString();
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        ArrayList<Attatchment> attachments = new ArrayList<>();
                        addAlbums(attachments, json.getJSONArray("albums"));
                        addArtist(attachments, json.getJSONArray("artists"));
                        addTracks(attachments, json.getJSONArray("tracks"));
                        searchAdapter = new AttachementSearchAdapter(getActivity().getApplicationContext(), attachments);
                        searchRecyclerView.setAdapter(searchAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> error.printStackTrace());
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private void addTracks(ArrayList<Attatchment> attachments, JSONArray a) throws JSONException {
        for (int i =0; i <a.length(); i++)
            attachments.add(extractTrack(a.getJSONObject(i)));
    }
    private void addAlbums(ArrayList<Attatchment> attachments, JSONArray a) throws JSONException {
        for (int i =0; i <a.length(); i++)
            attachments.add(extractAlbum(a.getJSONObject(i)));
    }
    private void addArtist(ArrayList<Attatchment> attachments, JSONArray a) throws JSONException {
        for (int i =0; i <a.length(); i++)
            attachments.add(extractArtist(a.getJSONObject(i)));
    }
    private static Album extractAlbum(JSONObject songJSON) throws JSONException {
        Album album = new Album();
        album.setTitle(songJSON.getString("name"));
        album.setArtist(songJSON.getString("artist"));
        album.setSpotifyId(songJSON.getString("id"));
        album.setAlbumCover(songJSON.getString("imageUrl"));
        album.setSpotifyLink(songJSON.getString("link"));
        return album;
    }

    private static Artist extractArtist(JSONObject songJSON) throws JSONException {
        Artist artist = new Artist();
        artist.setTitle("");
        artist.setArtist(songJSON.getString("artist"));
        artist.setSpotifyId(songJSON.getString("id"));
        artist.setAlbumCover(songJSON.getString("imageUrl"));
        artist.setSpotifyLink(songJSON.getString("link"));
        return artist;
    }

    private static Song extractTrack(JSONObject songJSON) throws JSONException {
        return new Song(songJSON.getString("name"),
                songJSON.getString("artist"),
                songJSON.getString("imageUrl"),
                songJSON.getString("id"),
                songJSON.getString("link"),
                songJSON.getString("playbackLink"));
    }
    
    public Attatchment getSelectedSong(){
        return searchAdapter.getSelectedSong();
    }
}