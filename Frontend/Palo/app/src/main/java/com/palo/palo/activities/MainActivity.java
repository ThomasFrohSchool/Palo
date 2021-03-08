package com.palo.palo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.login.LoginActivity;
import com.palo.palo.fragments.CreatePaloFragment;
import com.palo.palo.fragments.FeedFragment;
import com.palo.palo.fragments.PaloListFragment;
import com.palo.palo.fragments.ProfileFragment;
import com.palo.palo.fragments.SearchFragment;
import com.palo.palo.model.User;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavBar;
    final Fragment feedFrag = new FeedFragment();
    final Fragment palolistFrag = new PaloListFragment();
//    final Fragment createNewFrag = new CreatePaloFragment();
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
//            fm.beginTransaction().add(R.id.main_frame, createNewFrag, "createNewFrag").hide(createNewFrag).commit();
            fm.beginTransaction().add(R.id.main_frame, searchFrag, "searchFrag").hide(searchFrag).commit();
            fm.beginTransaction().add(R.id.main_frame, profileFrag, "profileFrag").hide(profileFrag).commit();


//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.main_frame, new FeedFragment());
//            ft.commit();

            // NavBar stuff
            bottomNavBar = findViewById(R.id.bottom_nav_bar);
            bottomNavBar.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_palolist){
//                        PaloListFragment paloListFragment = new PaloListFragment();
//                        openFragment(paloListFragment);
                    fm.beginTransaction().hide(active).show(palolistFrag).commit();
                    active = palolistFrag;
                    return true;
                } else if (id == R.id.action_create){
//                        CreatePaloFragment createPaloFragment = new CreatePaloFragment();
//                        openFragment(createPaloFragment);
                    startActivity(new Intent(this, CreateNewPostActivity.class));
//                    fm.beginTransaction().hide(active).show(createNewFrag).commit();
//                    active = createNewFrag;
                    return true;
                } else if (id == R.id.action_home){
//                        FeedFragment feedFragment = new FeedFragment();
//                        openFragment(feedFragment);
                    fm.beginTransaction().hide(active).show(feedFrag).commit();
                    active = feedFrag;
                    return true;
                } else if (id == R.id.action_search){
//                        SearchFragment searchFragment = new SearchFragment();
//                        openFragment(searchFragment);
                    fm.beginTransaction().hide(active).show(searchFrag).commit();
                    active = searchFrag;
                    return true;
                } else if (id == R.id.action_profile){
//                        ProfileFragment profileFragment = new ProfileFragment();
//                        openFragment(profileFragment);
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

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}