package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.palo.palo.R;
import com.palo.palo.model.Palo;
import com.squareup.picasso.Picasso;

public class ExtendedPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_post);

        ImageButton backButton = findViewById(R.id.backToFeedButton);
        backButton.setOnClickListener(v -> finish());
        Palo attachedSong = getIntent().getParcelableExtra("selected_post");
        setView(attachedSong);
    }
    private void setView(Palo palo){
        Picasso.get().load(palo.getAuthor().getProfileImage()).into((ImageView) findViewById(R.id.paloAuthorProfileImage));
        ((TextView) findViewById(R.id.paloAuthorUserName)).setText(palo.getAuthor().getUsername());
        ((TextView) findViewById(R.id.paloDate)).setText(palo.getPostDate()); // TODO display date in a nicer way
        ((TextView) findViewById(R.id.paloCaption)).setText(palo.getCaption());

        ((TextView) findViewById(R.id.songTitle)).setText(palo.getAttachment().getTitle());
        ((TextView) findViewById(R.id.songArtist)).setText(palo.getAttachment().getArtist());
        Picasso.get().load(palo.getAttachment().getAlbumCover()).into((ImageView) findViewById(R.id.coverImage));

    }
}