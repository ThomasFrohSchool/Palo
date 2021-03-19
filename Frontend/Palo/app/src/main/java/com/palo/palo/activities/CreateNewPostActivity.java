package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.palo.palo.R;
import com.palo.palo.fragments.CreatePaloFragment;
import com.palo.palo.fragments.CreatePaloSearchFragment;
import com.palo.palo.model.Attatchment;
import com.palo.palo.model.Song;


public class CreateNewPostActivity extends AppCompatActivity {
    final Fragment createNewFrag = new CreatePaloFragment();
    final Fragment createNewSearchFrag = new CreatePaloSearchFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = createNewSearchFrag;

    Button back;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);
        fm.beginTransaction().add(R.id.create_new_post_frame, createNewSearchFrag, "createNewSearchFrag").commit();
        fm.beginTransaction().add(R.id.create_new_post_frame, createNewFrag, "createNewFrag").hide(createNewFrag).commit(); // will show feed on default

        back = findViewById(R.id.create_new_post_back);
        back.setOnClickListener(v ->backClicked());

        next = findViewById(R.id.create_new_post_next);
        next.setOnClickListener(v ->nextClicked());

    }

    private void backClicked(){
        if(createNewSearchFrag.isVisible()) {
            startActivity(new Intent(CreateNewPostActivity.this, MainActivity.class));
        } else {
            fm.beginTransaction().hide(active).show(createNewSearchFrag).commit();
            active = createNewSearchFrag;
            back.setText("CANCEL");
            next.setText("NEXT");
        }

    }


    private void nextClicked(){
        if(createNewSearchFrag.isVisible()) {
            Attatchment song = ((CreatePaloSearchFragment) createNewSearchFrag).getSelectedSong();
            if (song == null) {
                Toast.makeText(getApplicationContext(), "Select an attachment.", Toast.LENGTH_LONG).show();
                return;
            }
            ((CreatePaloFragment) createNewFrag).setAttatchment(song);
            fm.beginTransaction().hide(active).show(createNewFrag).commit();
            active = createNewFrag;
            back.setText("BACK");
            next.setText("POST");
        } else {
            ((CreatePaloFragment) createNewFrag).post();
            startActivity(new Intent(CreateNewPostActivity.this, MainActivity.class));
        }

    }
}