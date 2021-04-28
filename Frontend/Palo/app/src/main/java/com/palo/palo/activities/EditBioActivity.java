package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import static com.palo.palo.volley.ServerURLs.EDITBIO;

public class EditBioActivity extends AppCompatActivity {
    private Context context;
    private Button send;
    private EditText editBioTo;
    private ImageButton backBio;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);
        context = getApplicationContext();
        send = findViewById(R.id.sendBio);
        editBioTo = findViewById(R.id.editBioTo);
        backBio = findViewById(R.id.backBio);
        id = getIntent().getIntExtra("ID", -1);

        send.setOnClickListener(v -> {
            editBioRequest(editBioTo.getText().toString());
        });

        backBio.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });
    }

    public void editBioRequest(String toBio) {

        StringRequest request = new StringRequest(Request.Method.GET, EDITBIO + id + "?bio=" + toBio, response -> {
            System.out.println("Bio changed to " + toBio);
        }, error -> error.printStackTrace());

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}