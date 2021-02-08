package com.volleyexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(v -> jsonParse());
    }

    private void jsonParse(){
        String url = "https://700786a5-39db-42af-826b-f26c4346b128.mock.pstmn.io/songs";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                // Example json array below
                //{ songs"songs": [ { "title": "Bummerland", "artist": "AJR" }, { "title": "Netflix Trip", "artist": "AJR" }, { "title": "Cigarette Daydreams", "artist": "Cage The Elephant" } ] }
                JSONArray jsonArray = response.getJSONArray("songs");
                mTextViewResult.setText("");
                mTextViewResult.setTextColor(getSemiRandomColor());
                for ( int i = 0; i < jsonArray.length(); i++){
                    JSONObject song = jsonArray.getJSONObject(i);
                    String title = song.getString("title");
                    String artist = song.getString("artist");

                    mTextViewResult.append(title + " by " + artist + "\n\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> error.printStackTrace());

        mQueue.add(request);
    }

    private int getSemiRandomColor(){
        Random rand = new Random();
        int[] colors = {Color.argb(255, 199, 64, 113),
                        Color.argb(255, 151, 84, 79),
                        Color.argb(255, 99, 110, 77),
                        Color.argb(255, 67, 81, 104),
                        Color.argb(255, 112, 75, 105)
                        };
        return colors[rand.nextInt(5)];
    }
}