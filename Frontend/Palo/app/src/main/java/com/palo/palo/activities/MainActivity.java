package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.login.LoginActivity;
import com.palo.palo.fragments.FeedFragment;
import com.palo.palo.fragments.PaloListFragment;
import com.palo.palo.fragments.ProfileFragment;
import com.palo.palo.fragments.SearchFragment;


/**
 * This class is for setting up the app and preparing the navigation bar.
 * It is associated with activity_main.xml.
 */
public class MainActivity extends AppCompatActivity{
    BottomNavigationView bottomNavBar;
    final Fragment feedFrag = new FeedFragment();
    final Fragment palolistFrag = new PaloListFragment();
    final Fragment searchFrag = new SearchFragment();
    final Fragment profileFrag = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = feedFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            fm.beginTransaction().add(R.id.main_frame, feedFrag, "feedFrag").commit(); // will show feed on default
            fm.beginTransaction().add(R.id.main_frame, palolistFrag, "palolistFrag").hide(palolistFrag).commit();
            fm.beginTransaction().add(R.id.main_frame, searchFrag, "searchFrag").hide(searchFrag).commit();
            fm.beginTransaction().add(R.id.main_frame, profileFrag, "profileFrag").hide(profileFrag).commit();

            // NavBar stuff
            bottomNavBar = findViewById(R.id.bottom_nav_bar);
            bottomNavBar.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_palolist){

                    fm.beginTransaction().hide(active).show(palolistFrag).commit();
                    active = palolistFrag;
                    return true;
                } else if (id == R.id.action_create){
                    startActivity(new Intent(this, CreateNewPostActivity.class));
                    return true;
                } else if (id == R.id.action_home){
                    fm.beginTransaction().hide(active).show(feedFrag).commit();
                    active = feedFrag;
                    return true;
                } else if (id == R.id.action_search){
                    fm.beginTransaction().hide(active).show(searchFrag).commit();
                    active = searchFrag;
                    return true;
                } else if (id == R.id.action_profile){
                    fm.beginTransaction().hide(active).show(profileFrag).commit();
                    active = profileFrag;
                    return true;
                }
                return false;
            });
        } else {
            Intent  intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}