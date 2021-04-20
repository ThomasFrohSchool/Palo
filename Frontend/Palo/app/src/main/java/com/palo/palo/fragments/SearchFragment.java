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

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.palo.palo.AttachementSearchAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
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

import static com.palo.palo.volley.ServerURLs.FOLLOW;
import static com.palo.palo.volley.ServerURLs.PICS;
import static com.palo.palo.volley.ServerURLs.SEARCH;
import static com.palo.palo.volley.ServerURLs.UNFOLLOW;
import static com.palo.palo.volley.ServerURLs.USERS;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

/**
 * Fragment to allow user to search songs, albums, artist, etc. from Spotify.
 */
public class SearchFragment extends Fragment implements UserSearchAdapter.onUserListener{
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
    ArrayList<User> usersList;
    ArrayList<Integer> userFollowing;
    private User user;

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
        user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();

        song = view.findViewById(R.id.spotifySearch);
        bSpotify = view.findViewById(R.id.buttonSpotify);
        bUsers = view.findViewById(R.id.buttonUsers);
        getFollowing();

        userAdapter = new UserSearchAdapter(getActivity().getApplicationContext(), usersList, userFollowing, this);
        r = view.findViewById(R.id.res);
        r.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        p = new ProgressDialog(getActivity().getApplicationContext());
        p.setMessage("Loading...");
        p.setCancelable(false);

        bSpotify.setOnClickListener(v -> makeStringSongRequest(SEARCH + song.getText().toString()));
        bUsers.setOnClickListener(v -> makeUsersRequest(song.getText().toString()));
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

    /**
     * Makes json array request to server. Server respons with list of users. On response, updates result recycler view
     * with found matching usernames.
     * @param s
     */
    public void makeUsersRequest(String s) {
        //ArrayList<User> users = new ArrayList<>();

        getFollowing();

        usersList = new ArrayList<>();
        JsonArrayRequest arr = new JsonArrayRequest(USERS,
                response -> {
                    Log.d(JSONTAG, response.toString());
                    try {
                        addUsers(usersList, response, s);
                        //r.setAdapter(new UserSearchAdapter(getActivity().getApplicationContext(), new ArrayList<>(), this, user.getUserFollowing()));
                        userAdapter = new UserSearchAdapter(getActivity().getApplicationContext(), usersList, userFollowing, this);
                        /*for(int i = 0; i < usersList.size(); i++) {
                            for(int j = 0; j < userFollowing.size(); j++) {
                                if(usersList.get(i).getId() == userFollowing.get(j)) {
                                    userAdapter.toggleFollow(true);
                                }
                            }
                        }*/
                        r.setAdapter(userAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());

        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(arr);
    }

    private void getFollowing() {
        //ArrayList<Integer> u = new ArrayList<>();
        userFollowing = new ArrayList<>();

        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, USER_BY_ID + user.getId(), null,
                response -> {
                    try {
                        JSONArray arr = response.getJSONArray("following");
                        for(int i = 0; i < arr.length(); i++) {
                            Integer f = arr.getInt(i);
                            userFollowing.add(f);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        //p.hide();
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(j);

        //return u;
    }

    /**
     * Makes StringRequest to SERVER/search?q=[TEXT], where TEXT is a string to what the user is searching.
     * Adds request to VolleySingleton request queue. Server responds with a string containing a list of albums, osngs, and artist from Spotify.
     */
    public String makeStringSongRequest(String hurl) {
        //p.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                hurl,
                response -> {
            str = response;
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
                Log.d(STRINGTAG, str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //req.setText("Response: " + response);
                }, error -> error.printStackTrace());
        //p.hide();
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
        return str;
    }

    private void addUsers(ArrayList<User> users, JSONArray a, String s) throws JSONException {
        //String s = song.getText().toString();
        for(int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);
            if(s.equalsIgnoreCase(o.getString("username"))) {
                users.add(extractUser(o));
                //usersList.add(extractUser(o));
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
        User newUser = new User(userJson.getInt("id"));
        newUser.setUsername(userJson.getString("username"));
        newUser.setProfileImage(PICS + userJson.getInt("id") + "/" + userJson.getInt("id"));

        return newUser;
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

    @Override
    public void onAddFollowClicked(int position) {
        System.out.println("user followed " + usersList.get(position).getId());
        //user.addFollowing(usersList.get(position).getId());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, FOLLOW + user.getId() + "/" + usersList.get(position).getId(), null,
                response -> {
                    System.out.println("Attempting to follow " + usersList.get(position).getId());
                },
                error -> {
                    error.printStackTrace();
                }
        );

        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void onRemoveFollowClicked(int position) {
        System.out.println("user removed " + usersList.get(position).getId());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, UNFOLLOW + user.getId() + "/" + usersList.get(position).getId(), null,
                response -> {
                    System.out.println("Attempting to unfollow " + usersList.get(position).getId());
                },
                error -> {
                    error.printStackTrace();
                }
        );

        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }
}