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
//        url = SEARCH + "mango";
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject json = jsonifyResponse(response);
                        ArrayList<Attatchment> attachments = new ArrayList<Attatchment>();
                        addAlbums(attachments, json.getJSONArray("Albums"));
                        addArtist(attachments, json.getJSONArray("Artists"));
                        addTracks(attachments, json.getJSONArray("Tracks"));
                        searchAdapter = new AttachementSearchAdapter(getActivity().getApplicationContext(), attachments);
                        searchRecyclerView.setAdapter(searchAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> error.printStackTrace());
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private JSONObject jsonifyResponse(String response){
        String [] splitRes = response.split(" \n");
        //TYPE => album=0, artist=1, song=2
        JSONObject resultJSON = new JSONObject();
        try {
            for(int j = 0; j < splitRes.length; j++) {
                String[] albums = splitRes[j].split(" #([0-9])+ : ");
                JSONArray albumsJSON = new JSONArray();
                for (int i = 1; i < albums.length; i++) {
                    albums[i] = albums[i].replaceAll(" Name:", "\"Name\":").replaceAll(" ID:", ", \"ID\":");
                    albums[i] = "{" + albums[i] + "}";

                    albumsJSON.put(new JSONObject(albums[i]));
                }
                resultJSON.put( albums[0].substring(0, albums[0].length()-1), albumsJSON);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultJSON;
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
        album.setTitle(songJSON.getString("Name"));
        album.setArtist(songJSON.getString("ID"));
        album.setSpotifyId(songJSON.getString("ID"));
        album.setAlbumCover("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRO-RJoyWwvL5Q2zHo1UE0K03GisHZldnh5Bg&usqp=CAU");
//        song.setAlbumCover(songJSON.getString("album_cover"));
        return album;
    }

    private static Artist extractArtist(JSONObject songJSON) throws JSONException {
        Artist artist = new Artist();
        artist.setTitle(songJSON.getString("Name"));
        artist.setArtist(songJSON.getString("ID"));
        artist.setSpotifyId(songJSON.getString("ID"));
        artist.setAlbumCover("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ-oydmw-ZLBeZUeFVx_hiRcqUwzS-T0xE4w&usqp=CAU");
//        song.setAlbumCover(songJSON.getString("album_cover"));
        return artist;
    }

    private static Song extractTrack(JSONObject songJSON) throws JSONException {
        Song song = new Song();
        song.setTitle(songJSON.getString("Name"));
        song.setArtist(songJSON.getString("ID")); 
        song.setSpotifyId(songJSON.getString("ID"));
        song.setAlbumCover("https://pbs.twimg.com/profile_images/733324424754176000/97L20Vvz_400x400.jpg");
//        song.setAlbumCover(songJSON.getString("album_cover"));
        return song;
    }
    
    public Attatchment getSelectedSong(){
        return searchAdapter.getSelectedSong();
    }
}