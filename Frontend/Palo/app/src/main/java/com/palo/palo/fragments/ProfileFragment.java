package com.palo.palo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.FeedAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.model.Palo;
import com.palo.palo.model.Song;
import com.palo.palo.model.User;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is for the users profile page and its functionality.
 * This class is associated with the fragment_profile.xml.
 */
public class ProfileFragment extends Fragment {
    //temporary url
    private static String url = "https://440b43ef-556f-4d7d-a95d-081ca321b8f9.mock.pstmn.io";
    private static String picUrl = "http://coms-309-021.cs.iastate.edu/pics/";
    private TextView profileName;
    private ImageView profileImage;
    private TextView paloAmt;
    //private TextView palos;
    private TextView followerAmt;
    //private TextView followers;
    private TextView followingAmt;
    //private TextView following;
    private static User user;
    //private ProgressDialog p;
    private RecyclerView r;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        paloAmt = view.findViewById(R.id.paloAmt);
        followerAmt = view.findViewById(R.id.followerAmt);
        followingAmt = view.findViewById(R.id.followingAmt);
        r = view.findViewById(R.id.userPalos);
        //p = new ProgressDialog(getActivity().getApplicationContext());
        //p.setMessage("Loading...");

        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            if (v.equals(logoutButton)) {
                SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
            }
        });

        profileName.setText(user.getUsername());
        getProfile();
        extractPalos();
    }

    private void getProfile() {
        //p.show();
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, url + "/profile?q=" + user.getUsername(), null,
                response -> {
                    try {
                        //String imgLink = response.getString("profileImage");
                        Picasso.get().load(picUrl + user.getId() + "/" + user.getId()).into(profileImage);
                        followerAmt.setText(response.getString("followers"));
                        followingAmt.setText(response.getString("following"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        //p.hide();
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(j);
    }

    private void extractPalos() {
        JsonArrayRequest request = getUserPalos(r, getActivity().getApplicationContext(), paloAmt);
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private static JsonArrayRequest getUserPalos(RecyclerView recyclerView, Context context, TextView amt) {
        return new JsonArrayRequest(Request.Method.GET, url + "/Palo?q=" + user.getUsername(), null,
                response -> {
                    List<Palo> palos = new ArrayList<>();
                    for(int i = 0; i < response.length(); i++) {
                        try {
                            palos.add(extractPalo(response.getJSONObject(i)));
                            String len = String.valueOf(response.length());
                            amt.setText(len);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new FeedAdapter(context, palos));
                }, Throwable::printStackTrace);
    }

    private static Palo extractPalo(JSONObject paloJSON) throws JSONException {
        Palo palo = new Palo();
        palo.setAuthor(extractUser(paloJSON.getJSONObject("author")));
        palo.setPostDate(paloJSON.getString("postdate"));
        palo.setCaption(paloJSON.getString("caption"));
        palo.setAttachedSong(extractSong(paloJSON.getJSONObject("song")));
        return palo;
    }

    // Example --> {"song": "Freakin' Out On the Interstate", "artist":"Briston Maroney","album_cover": "https://i0.wp.com/altangeles.com/wp-content/uploads/2018/12/briston.jpg?fit=640%2C640&ssl=1"}
    private static Song extractSong(JSONObject songJSON) throws JSONException {
        Song song = new Song();
        song.setTitle(songJSON.getString("song"));
        song.setArtist(songJSON.getString("artist"));
        song.setAlbumCover(songJSON.getString("album_cover"));
        return song;
    }

    // Example --> {"username": "tiffmay", "profile_image": "fillimage link here"},
    private static User extractUser(JSONObject userJSON) throws JSONException{
        //User user = new User();
        user.setUsername(userJSON.getString("username"));
        user.setProfileImage(picUrl + user.getId() + "/" + user.getId());
        return user;
    }
}