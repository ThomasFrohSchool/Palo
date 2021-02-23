package com.example.tfrohvolleyex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button str, json, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = (Button) findViewById(R.id.stringReq);
        json = (Button) findViewById(R.id.jsonReq);
        img = (Button) findViewById(R.id.imgReq);

        str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StringActivity.class);
                startActivity(i);
            }
        });
    }


}