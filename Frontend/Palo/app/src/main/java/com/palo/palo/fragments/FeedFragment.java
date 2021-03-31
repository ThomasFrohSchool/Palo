package com.palo.palo.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.palo.palo.FeedAdapter;
import com.palo.palo.R;
import com.palo.palo.model.Palo;
import com.palo.palo.model.Song;
import com.palo.palo.model.User;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is for the feed and its functionalities.
 * This class is associated with fragment_feed.xml.
 */
public class FeedFragment extends Fragment {
    RecyclerView recyclerView;
    private static String SERVER_URL = "https://6e8134ce-7a91-4a1d-8c23-f06c12c6fcfd.mock.pstmn.io";

    public FeedFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.songList);
        recyclerView.setAdapter(new FeedAdapter(getActivity().getApplicationContext(), new ArrayList<>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        extractSongs();
    }

    /**
     * This method is for extracting the song from a Palo.
     */
    private void extractSongs() {
        JsonArrayRequest request = paloRequest(recyclerView, getActivity().getApplicationContext());
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    /**
     *
     * @param recyclerView: The recycler view of all the Palos.
     * @param context: The context of the fragment
     * @return Returns the list of Palos.
     */
    private static JsonArrayRequest paloRequest(RecyclerView recyclerView, Context context){
        return new JsonArrayRequest(Request.Method.GET, SERVER_URL + "/feed", null, response -> {
            List<Palo> palos = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    palos.add(extractPalo(response.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.setAdapter(new FeedAdapter(context, palos));
        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
    }

    /**
     * This method is for setting different fields of the Palo.
     * @param paloJSON: The JSONObject of the Palo.
     * @return Returns the Palo and its different fields.
     * @throws JSONException
     */
    private static Palo extractPalo(JSONObject paloJSON) throws JSONException {
        Palo palo = new Palo();
        palo.setAuthor(extractUser(paloJSON.getJSONObject("author")));
        palo.setPostDate(paloJSON.getString("postdate"));
        palo.setCaption(paloJSON.getString("caption"));
        palo.setAttachedSong(extractSong(paloJSON.getJSONObject("song")));
        return palo;
    }

    /**
     * This method is for setting different fields of the song.
     * @param songJSON: The JSONObject of the song.
     * @return Returns the song and its different fields.
     * @throws JSONException
     */
    // Example --> {"song": "Freakin' Out On the Interstate", "artist":"Briston Maroney","album_cover": "https://i0.wp.com/altangeles.com/wp-content/uploads/2018/12/briston.jpg?fit=640%2C640&ssl=1"}
    private static Song extractSong(JSONObject songJSON) throws JSONException {
        Song song = new Song();
        song.setTitle(songJSON.getString("song"));
        song.setArtist(songJSON.getString("artist"));
        song.setAlbumCover(songJSON.getString("album_cover"));
        return song;
    }

    /**
     * This method is for setting different fields of the user.
     * @param userJSON: The JSONObject of the user.
     * @return Returns the user and its different fields.
     * @throws JSONException
     */
    // Example --> {"username": "tiffmay", "profile_image": "fillimage link here"},
    private static User extractUser(JSONObject userJSON) throws JSONException{
        User user = new User();
        user.setUsername(userJSON.getString("username"));
        user.setProfileImage(userJSON.getString("profile_image"));
        return user;
    }
}
