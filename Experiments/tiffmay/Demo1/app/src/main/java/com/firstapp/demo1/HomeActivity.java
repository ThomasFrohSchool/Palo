package com.firstapp.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}