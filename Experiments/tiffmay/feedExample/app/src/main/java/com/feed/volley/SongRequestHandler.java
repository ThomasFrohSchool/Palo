package com.feed.volley;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.feed.FeedAdapter;
import com.feed.models.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongRequestHandler {
    private static String SERVER_URL = "https://6e8134ce-7a91-4a1d-8c23-f06c12c6fcfd.mock.pstmn.io"; 

    public SongRequestHandler(){}

    /*EXAMPLE OF JSON STRING:
        [{"song": "Goodbye", "artist":"Cage The Elephant","album_cover": "https://upload.wikimedia.org/wikipedia/en/1/17/Cage_the_Elephant_Social_Cues.jpg"},
        {"song": "Freakin' Out On the Interstate", "artist":"Briston Maroney","album_cover": "https://i0.wp.com/altangeles.com/wp-content/uploads/2018/12/briston.jpg?fit=640%2C640&ssl=1"}]
     */
    public static JsonArrayRequest songRequest(RecyclerView recyclerView, Context context){
        return new JsonArrayRequest(Request.Method.GET, SERVER_URL + "/feed", null, response -> {
            List<Song> songs = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject songJSON = response.getJSONObject(i);
                    Song song = new Song();
                    song.setTitle(songJSON.getString("song"));
                    song.setArtist(songJSON.getString("artist"));
                    song.setAlbumCover(songJSON.getString("album_cover"));

                    songs.add(song);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new FeedAdapter(context, songs));
        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
