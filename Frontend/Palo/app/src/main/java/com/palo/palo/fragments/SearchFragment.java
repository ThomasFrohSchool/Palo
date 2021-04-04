package com.palo.palo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.palo.palo.AttachementSearchAdapter;
import com.palo.palo.R;
import com.palo.palo.UserSearchAdapter;
import com.palo.palo.model.Album;
import com.palo.palo.model.Artist;
import com.palo.palo.model.Attachment;
import com.palo.palo.model.Song;
import com.palo.palo.model.User;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static com.palo.palo.volley.ServerURLs.PICS;
import static com.palo.palo.volley.ServerURLs.SEARCH;
import static com.palo.palo.volley.ServerURLs.USERS;

/**
 * Fragment to allow user to search songs, albums, artist, etc. from Spotify.
 */
public class SearchFragment extends Fragment {
    private EditText song;
    //private TextView req;
    private Button bSpotify;
    private Button bUsers;
    private RecyclerView r;
    private AttachementSearchAdapter spotifyAdapter;
    private UserSearchAdapter userAdapter;
    //private String url = "https://60dfb9a0-6de1-47ff-9b9b-69c16d317496.mock.pstmn.io";
    //private String url = "http://coms-309-021.cs.iastate.edu:8080";
    private ProgressDialog p;
    private String JSONTAG = SearchFragment.class.getSimpleName();
    private String STRINGTAG = SearchFragment.class.getSimpleName();
    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";
    private String tag_string_req = "string_req";
    private String str;

    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TextView headerET = view.findViewById(R.id.search_header);
        song = view.findViewById(R.id.spotifySearch);
        //req = view.findViewById(R.id.res);
        bSpotify = view.findViewById(R.id.buttonSpotify);
        bUsers = view.findViewById(R.id.buttonUsers);
        r = view.findViewById(R.id.res);
        r.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        p = new ProgressDialog(getActivity().getApplicationContext());
        p.setMessage("Loading...");
        p.setCancelable(false);

        bSpotify.setOnClickListener(v -> makeStringSongRequest(SEARCH + song.getText().toString()));
        bUsers.setOnClickListener(v -> makeUsersRequest());
    }

    /**
     * Makes JSONObjectRequest to SERVER/search?q=[TEXT], where TEXT is a string to what the user is searching.
     * Adds request to VolleySingleton request queue. Server responds with a json string containing a list of albums, osngs, and artist from Spotify.
     */
    private void makeJsonSongRequest() {
        //p.show();
        JsonObjectRequest jsonSongRequest = new JsonObjectRequest(Request.Method.GET,
                SEARCH + song.getText().toString(), null,
                response -> {
                    Log.d(JSONTAG, response.toString());
                    //req.setText("Response: " + response.toString());
                }, error -> VolleyLog.d(JSONTAG, "Error: " + error.getMessage()));
        //p.hide();

        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonSongRequest);
    }

    private void makeUsersRequest() {
        JsonArrayRequest arr = new JsonArrayRequest(USERS,
                response -> {
                    Log.d(JSONTAG, response.toString());
                    ArrayList<User> users = new ArrayList<>();
                    try {
                        addUsers(users, response);
                        r.setAdapter(new UserSearchAdapter(getActivity().getApplicationContext(), new ArrayList<>()));
                        userAdapter = new UserSearchAdapter(getActivity().getApplicationContext(), users);
                        r.setAdapter(userAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });

        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(arr);
    }

    /**
     * Makes StringRequest to SERVER/search?q=[TEXT], where TEXT is a string to what the user is searching.
     * Adds request to VolleySingleton request queue. Server responds with a string containing a list of albums, osngs, and artist from Spotify.
     */
    public String makeStringSongRequest(String hurl) {
        //p.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                SEARCH + song.getText().toString(),
                response -> {
                    Log.d(STRINGTAG, response);
                    try {
                        JSONObject o = new JSONObject(response);
                        ArrayList<Attachment> al = new ArrayList<>();
                        addAlbums(al, o.getJSONArray("albums"));
                        addArtist(al, o.getJSONArray("artists"));
                        addTracks(al, o.getJSONArray("tracks"));
                        r.setAdapter(new AttachementSearchAdapter(getActivity().getApplicationContext(), new ArrayList<>()));
                        spotifyAdapter = new AttachementSearchAdapter(getActivity().getApplicationContext(), al);
                        r.setAdapter(spotifyAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //req.setText("Response: " + response);
                }, error -> error.printStackTrace());
        //p.hide();
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
        return str;
    }

    private void addUsers(ArrayList<User> users, JSONArray a) throws JSONException {
        String s = song.getText().toString();
        for(int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);
            if(s.equalsIgnoreCase(o.getString("username"))) {
                users.add(extractUser(o));
            }
        }
    }

    private void addTracks(ArrayList<Attachment> attachments, JSONArray a) throws JSONException {
        for (int i =0; i <a.length(); i++)
            attachments.add(extractTrack(a.getJSONObject(i)));
    }
    private void addAlbums(ArrayList<Attachment> attachments, JSONArray a) throws JSONException {
        for (int i =0; i <a.length(); i++)
            attachments.add(extractAlbum(a.getJSONObject(i)));
    }
    private void addArtist(ArrayList<Attachment> attachments, JSONArray a) throws JSONException {
        for (int i =0; i <a.length(); i++)
            attachments.add(extractArtist(a.getJSONObject(i)));
    }

    private static User extractUser(JSONObject userJson) throws JSONException {
        User user = new User();
        user.setUsername(userJson.getString("username"));
        user.setProfileImage(PICS + userJson.getInt("id") + "/" + userJson.getInt("id"));

        return user;
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
        artist.setTitle(songJSON.getString("artist"));
        //artist.setArtist(songJSON.getString("artist"));
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
}