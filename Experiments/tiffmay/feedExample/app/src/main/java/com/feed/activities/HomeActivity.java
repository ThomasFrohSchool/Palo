package com.feed.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.feed.R;
import com.feed.volley.SongRequestHandler;
import com.feed.volley.VolleySingleton;



public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        recyclerView = findViewById(R.id.songList);
        extractSongs();
    }

    private void extractSongs() {
        JsonArrayRequest request = SongRequestHandler.paloRequest(recyclerView, getApplicationContext());
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}