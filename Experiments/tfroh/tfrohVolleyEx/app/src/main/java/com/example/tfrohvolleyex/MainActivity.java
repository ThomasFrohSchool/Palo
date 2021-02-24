package com.example.tfrohvolleyex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button str, json, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = (Button) findViewById(R.id.stringReq);
        json = (Button) findViewById(R.id.jsonReq);
        img = (Button) findViewById(R.id.imgReq);

        str.setOnClickListener(this);
        json.setOnClickListener(this);
        img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.stringReq:
                startActivity(new Intent(MainActivity.this, StringActivity.class));
                break;
                
            case R.id.jsonReq:
                startActivity(new Intent(MainActivity.this, JsonActivity.class));
                break;
                
            case R.id.imgReq:
                startActivity(new Intent(MainActivity.this, ImageActivity.class));
                break;
            default:
                break;
        }
    }
}
