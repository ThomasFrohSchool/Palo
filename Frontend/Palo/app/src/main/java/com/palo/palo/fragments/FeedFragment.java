package com.palo.palo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.FeedAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.ExtendedPostActivity;
import com.palo.palo.model.Album;
import com.palo.palo.model.Artist;
import com.palo.palo.model.Palo;
import com.palo.palo.model.Song;
import com.palo.palo.model.User;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.palo.palo.volley.ServerURLs.FEED;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

/**
 * This fragment is for the feed and its functionalities.
 * This class is associated with fragment_feed.xml.
 */
public class FeedFragment extends Fragment implements FeedAdapter.OnFeedListener {
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;
    View myView;
    private static String SERVER_URL = "https://6e8134ce-7a91-4a1d-8c23-f06c12c6fcfd.mock.pstmn.io";
    List<Palo> palos;
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
        myView = view;
        recyclerView = view.findViewById(R.id.songList);
        feedAdapter = new FeedAdapter(getActivity().getApplicationContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        extractSongs();
    }

    /**
     * This method is for extracting the song from a Palo.
     */
    private void extractSongs() {
        JsonArrayRequest request = feedRequest(recyclerView, getActivity().getApplicationContext());
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private  JsonArrayRequest feedRequest(RecyclerView recyclerView, Context context){
        return new JsonArrayRequest(Request.Method.GET, FEED + SharedPrefManager.getInstance(myView.getContext()).getUser().getId(), null, response -> {
           palos = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    Palo palo = extractPalo(response.getJSONObject(i), context);
                    userRequest(i, palo.getAuthor().getId(), context);
                    palos.add(palo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            feedAdapter.swapDataSet(palos);
//            recyclerView.setAdapter(new FeedAdapter(context, palos, this));
        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
    }

    /**
     * This method is for setting different fields of the Palo.
     * @param paloJSON: The JSONObject of the Palo.
     * @return Returns the Palo and its different fields.
     * @throws JSONException
     */
    private static Palo extractPalo(JSONObject paloJSON, Context context) throws JSONException {
        Palo palo = new Palo();
        palo.setAuthor(new User(paloJSON.getInt("user_id")));
//        JsonObjectRequest userRequest = userRequest(paloJSON.getInt("user_id"), palo, context);
//        VolleySingleton.getInstance(context).addToRequestQueue(userRequest);
        palo.setPostDate(paloJSON.getString("createDate"));
        palo.setCaption(paloJSON.getString("description"));
        int type = paloJSON.getInt("type");
        switch (type){
            case 0:
                palo.setAttachment(new Album(paloJSON.getString("spot_link")));
                break;
            case 1:
                palo.setAttachment(new Artist(paloJSON.getString("spot_link")));

                break;
            case 2:
                palo.setAttachment(new Song(paloJSON.getString("spot_link")));
                break;
            default: System.out.println("error");
        }
//        palo.setAttachedSong(extractSong(paloJSON.getJSONObject("song")));
        return palo;
    }

    private void userRequest(int palo_index, int userId, Context context){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,USER_BY_ID + userId, null, response -> {
            try {
                palos.get(palo_index).setAuthorUsername(response.getString("username"));
                feedAdapter.updatePalo(palo_index, palos.get(palo_index));
                // TODO set profile image here...
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
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

    @Override
    public void onPaloClick(int position) {
        System.out.println("post clicked..." + palos.get(position).getCaption());
        Palo p = palos.get(position);
        Intent intent =  new Intent(getContext(), ExtendedPostActivity.class);
        intent.putExtra("selected_post", palos.get(position));
        startActivity(intent);
    }
}
