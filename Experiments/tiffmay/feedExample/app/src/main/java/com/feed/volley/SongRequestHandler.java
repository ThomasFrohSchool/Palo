package com.feed.volley;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.feed.FeedAdapter;
import com.feed.models.Palo;
import com.feed.models.Song;
import com.feed.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongRequestHandler {
    private static String SERVER_URL = "https://6e8134ce-7a91-4a1d-8c23-f06c12c6fcfd.mock.pstmn.io"; 

    public SongRequestHandler(){}

    /*EXAMPLE OF JSON STRING:
        []
     */
    public static JsonArrayRequest paloRequest(RecyclerView recyclerView, Context context){
        return new JsonArrayRequest(Request.Method.GET, SERVER_URL + "/feed", null, response -> {
            List<Palo> palos = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    palos.add(extractPalo(response.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new FeedAdapter(context, palos));
        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
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
        User user = new User();
        user.setUsername(userJSON.getString("username"));
        user.setProfileImage(userJSON.getString("profile_image"));
        return user;
    }
}
