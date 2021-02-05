package com.firstapp.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.buttonMessage);
        b2 = findViewById(R.id.buttonNext);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello! This is a test. A simple test for DEMO 1.", Toast.LENGTH_LONG).show();
            }
        });

        b2.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        });
    }
}