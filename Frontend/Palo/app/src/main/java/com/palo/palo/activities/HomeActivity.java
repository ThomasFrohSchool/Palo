package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.login.LoginActivity;
import com.palo.palo.model.User;

public class HomeActivity extends AppCompatActivity {
    TextView homeMessage;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            homeMessage = findViewById(R.id.homeTitle);

            User user = SharedPrefManager.getInstance(this).getUser();
            homeMessage.setText(String.format("Hello %s! You are logged into Palo! :)", user.getUsername() ));
            
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