package com.palo.palo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.login.LoginActivity;
import com.palo.palo.model.User;

public class HomeActivity extends AppCompatActivity {
    TextView homeMessage;
    Button logoutButton;
    BottomNavigationView bottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            homeMessage = findViewById(R.id.homeTitle);

            User user = SharedPrefManager.getInstance(this).getUser();
            homeMessage.setText(String.format("Hello %s! You are logged into Palo! :)", user.getUsername() ));

            bottomNavBar = findViewById(R.id.bottom_nav_bar);
            bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_palolist:
                            //branch to palolist page
                            break;
                        case R.id.action_create:
                            //branch to create palo page
                            break;
                        case R.id.action_home:
                            //branch to home (feed) page
                            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                            break;
                        case R.id.action_search:
                            //branch to search page
                            break;
                        case R.id.action_profile:
                            //branch to profile page
                            startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
                            break;
                    }
                    return true;
                }
            });

            logoutButton = findViewById(R.id.logout);
            logoutButton.setOnClickListener(v -> {
                if (v.equals(logoutButton)) {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                }
            });
            
        } else {
            Intent  intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}