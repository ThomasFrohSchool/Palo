package com.firstapp.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.buttonMessage);
        b2 = findViewById(R.id.buttonNext);
        b3 = findViewById(R.id.buttonChangeColor);

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

        b3.setOnClickListener(v -> {
            //Change activities title
            TextView title = findViewById(R.id.mainTitle);
            title.setTextColor(this.getRandomColor());
        });
    }

    private int getRandomColor(){
        Random rand = new Random();
        return Color.argb(255, rand.nextInt(),rand.nextInt(), rand.nextInt());
    }
}