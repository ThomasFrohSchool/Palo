package com.palo.palo.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.palo.palo.activities.ExtendedPostActivity;
import com.palo.palo.model.Album;
import com.palo.palo.model.Artist;
import com.palo.palo.model.Palo;
import com.palo.palo.model.Song;
import com.palo.palo.model.User;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;
import com.palo.palo.volley.ServerURLs.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.palo.palo.volley.ServerURLs.ATTACHMENT;
import static com.palo.palo.volley.ServerURLs.PICS;
import static com.palo.palo.volley.ServerURLs.POSTS_FROM_USER;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

/**
 * This fragment is for the users profile page and its functionality.
 * This class is associated with the fragment_profile.xml.
 */
public class ProfileFragment extends Fragment implements FeedAdapter.OnFeedListener {
    //temporary url
    //private static String url = "https://440b43ef-556f-4d7d-a95d-081ca321b8f9.mock.pstmn.io";
    private TextView profileName;
    private ImageView profileImage;
    private TextView paloAmt;
    //private TextView palos;
    private TextView followerAmt;
    //private TextView followers;
    private TextView followingAmt;
    //private TextView following;
    private User user;
    //private ProgressDialog p;
    private RecyclerView r;
    FeedAdapter postAdapter;
    List<Palo> palos;
    private String str;

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
        getProfile(USER_BY_ID + user.getId());
        postAdapter = new FeedAdapter(getActivity().getApplicationContext(), new ArrayList<>(), this);
        r.setAdapter(postAdapter);
        r.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        extractPalos();
    }

    /**
     * Makes volley request to set profile infomation.
     * @param s
     * @return
     */
    public String getProfile(String s) {
        //p.show();
        ArrayList<Integer> userFollowing = new ArrayList<>();
        ArrayList<Integer> userFollowers = new ArrayList<>();
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, s, null,
                response -> {
                    try {
                        //String imgLink = response.getString("profileImage");
                        str = response.getString("username");
                        //JSONArray following = response.getJSONArray("following");
                        //JSONArray followers = response.getJSONArray("followers");
                        user.setProfileImage(PICS + user.getId() + "/" + user.getId());
                        Picasso.get().load(PICS + user.getId() + "/" + user.getId()).into(profileImage);
                        followerAmt.setText(String.valueOf(response.getJSONArray("followers").length()));
                        followingAmt.setText(String.valueOf(response.getJSONArray("following").length()));
                        for(int i = 0; i < response.getJSONArray("following").length(); i++) {
                            //Integer f = following.getInt(i);
                            userFollowing.add(response.getJSONArray("following").getInt(i));
                        }
                        for(int i = 0; i < response.getJSONArray("followers").length(); i++) {
                            //Integer f = followers.getInt(i);
                            userFollowers.add(response.getJSONArray("followers").getInt(i));
                        }
                        user.setUserFollowing(userFollowing);
                        user.setUserFollowers(userFollowers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        //p.hide();
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(j);
        return str;
    }

    /**
     * See Feed Fragement onPaloClick method.
     */
    private void extractPalos() {
        JsonArrayRequest request = getUserPalos(r, getActivity().getApplicationContext(), paloAmt);
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Sets up volley request to recieve a users posts from the server. Updates recycler view to shows posts.
     * @param recyclerView
     * @param context
     * @param amt
     * @return
     */
    private JsonArrayRequest getUserPalos(RecyclerView recyclerView, Context context, TextView amt) {
        return new JsonArrayRequest(Request.Method.GET, POSTS_FROM_USER + user.getId(), null,
                response -> {
                    palos = new ArrayList<>();
                    for(int i = 0; i < response.length(); i++) {
                        try {
                            Palo palo = extractPalo(response.getJSONObject(i), context);
                            userRequest(i, palo.getAuthor().getId(), context);
                            if (palo.getAttachment().getSpotifyId() != null)
                                attachmentRequest(i, palo.getAttachment().getType(), palo.getAttachment().getSpotifyId(), context);
                            palos.add(palo);
                            //palos.add(extractPalo(response.getJSONObject(i), context));
                            //String len = String.valueOf(response.length());
                            amt.setText(String.valueOf(response.length()));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    postAdapter.swapDataSet(palos);
                }, Throwable::printStackTrace);
    }

    private static Palo extractPalo(JSONObject paloJSON, Context context) throws JSONException {
        Palo palo = new Palo();
        palo.setId(paloJSON.getInt("id"));
        palo.setAuthor(new User(paloJSON.getInt("user_id")));
//        JsonObjectRequest userRequest = userRequest(paloJSON.getInt("user_id"), palo, context);
//        VolleySingleton.getInstance(context).addToRequestQueue(userRequest);
        palo.setPostDate(paloJSON.getString("createDate"));
        palo.setCaption(paloJSON.getString("description"));
        int type = paloJSON.getInt("type");
        switch (type){
            case 0:
                palo.setAttachment(new Album(paloJSON.getString("spot_id")));
                break;
            case 1:
                palo.setAttachment(new Artist(paloJSON.getString("spot_id")));
                break;
            case 2:
                palo.setAttachment(new Song(paloJSON.getString("spot_id")));
                break;
            default: System.out.println("error");
        }
        return palo;
    }

    private void attachmentRequest(int palo_index, int type, String spotify_link, Context context){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,ATTACHMENT(type) + spotify_link, null, response -> {
            try {
                String name = (type == 1) ? "" : response.getString("name");
                palos.get(palo_index).updateAttachment(name, response.getString("artist"), response.getString("imageUrl"), response.getString("id"));
                postAdapter.updatePalo(palo_index, palos.get(palo_index));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> System.out.println(error.getMessage()));
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private void userRequest(int palo_index, int userId, Context context){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,USER_BY_ID + userId, null, response -> {
            try {
                palos.get(palo_index).setAuthorUsername(response.getString("username"));
                postAdapter.updatePalo(palo_index, palos.get(palo_index));
                palos.get(palo_index).setPaloAuthorProfileImage(PICS + userId + "/" + userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> System.out.println(error.getMessage()));
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    /*private static Palo extractPalo(JSONObject paloJSON) throws JSONException {
        Palo palo = new Palo();
        palo.setAuthor(extractUser(paloJSON.getJSONObject("author")));
        palo.setPostDate(paloJSON.getString("postdate"));
        palo.setCaption(paloJSON.getString("caption"));
        palo.setAttachment(extractSong(paloJSON.getJSONObject("song")));
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
        user.setProfileImage(PICS + user.getId() + "/" + user.getId());
        return user;
    }*/

    /**
     * See Feed Fragement onPaloClick method.
     * @param position
     */
    @Override
    public void onPaloClick(int position) {
        System.out.println("post clicked..." + palos.get(position).getCaption());
        Palo p = palos.get(position);
        Intent intent =  new Intent(getContext(), ExtendedPostActivity.class);
        intent.putExtra("selected_post", palos.get(position));
        startActivity(intent);
    }

    @Override
    public void onLikeClicked(int position) {
        System.out.println("post like clicked..." + palos.get(position).getCaption());
    }
}