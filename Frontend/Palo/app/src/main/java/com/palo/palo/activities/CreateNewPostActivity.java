package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import com.palo.palo.R;
import com.palo.palo.fragments.CreatePaloFragment;
import com.palo.palo.fragments.CreatePaloSearchFragment;


public class CreateNewPostActivity extends AppCompatActivity {
    final Fragment createNewFrag = new CreatePaloFragment();
    final Fragment createNewSearchFrag = new CreatePaloSearchFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = createNewFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);
        fm.beginTransaction().add(R.id.create_new_post_frame, createNewFrag, "createNewFrag").commit(); // will show feed on default
        fm.beginTransaction().add(R.id.create_new_post_frame, createNewSearchFrag, "createNewSearchFrag").hide(createNewSearchFrag).commit();

    }
}