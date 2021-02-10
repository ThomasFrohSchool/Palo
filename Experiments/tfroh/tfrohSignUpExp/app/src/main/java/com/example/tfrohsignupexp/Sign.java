package com.example.tfrohsignupexp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Sign extends AppCompatActivity {
    Button b1;
    EditText e1, e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.signButton);
        e1 = (EditText) findViewById((R.id.editPassword));
        e2 = (EditText) findViewById((R.id.editConfirmPassword));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().equals(e2.getText().toString())) {
                    Intent i = new Intent(Sign.this, ConfirmedActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}