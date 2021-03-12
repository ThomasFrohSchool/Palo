package com.palo.palo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

/**
 * This class is for setting up the app and preparing the navigation bar.
 * It is associated with activity_main.xml.
 */
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            // user is logged in stuff
            User user = SharedPrefManager.getInstance(this).getUser();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame, new FeedFragment());
            ft.commit();

            // NavBar stuff
            bottomNavBar = findViewById(R.id.bottom_nav_bar);
            bottomNavBar.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_palolist){
                        PaloListFragment paloListFragment = new PaloListFragment();
                        openFragment(paloListFragment);
                        return true;
                } else if (id == R.id.action_create){
                        CreatePaloFragment createPaloFragment = new CreatePaloFragment();
                        openFragment(createPaloFragment);
                        return true;
                } else if (id == R.id.action_home){
                        FeedFragment feedFragment = new FeedFragment();
                        openFragment(feedFragment);
                        return true;
                } else if (id == R.id.action_search){
                        SearchFragment searchFragment = new SearchFragment();
                        openFragment(searchFragment);
                        return true;
                } else if (id == R.id.action_profile){
                        ProfileFragment profileFragment = new ProfileFragment();
                        openFragment(profileFragment);
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

    /**
     * This method opens a fragment whenever a user presses on a different button on the nav bar.
     * @param fragment: The pressed fragment.
     */
    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}