package com.palo.palo.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.MainActivity;
import com.palo.palo.model.User;
import com.palo.palo.volley.ServerURLs;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameRegField;
    EditText emailRegField;
    EditText passwordRegField;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        usernameRegField = findViewById(R.id.usernameRegister);
        emailRegField = findViewById(R.id.emailRegister);
        passwordRegField = findViewById(R.id.passwordRegister);

        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(v -> register());
    }

    // todo - should probably create a register class and move this there
    private void register() {
        String username = usernameRegField.getText().toString().trim();
        String email = emailRegField.getText().toString().trim();
        String password = passwordRegField.getText().toString().trim();

        if (hasEmptyCredentials(username, email, password)) return;
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);

        JsonObjectRequest request = new JsonObjectRequest(ServerURLs.REGISTER, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                try {
                    if(!json.getBoolean("error")) {
                        JSONObject userJson = json.getJSONObject("user");

                        User user = new User(Integer.parseInt(userJson.getString("id")), userJson.getString("username"), userJson.getString("email"));
                        SharedPrefManager.getInstance(getApplicationContext()).login(user);

                        finish();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private boolean hasEmptyCredentials(String username, String email, String password){
        if(username.isEmpty()){
            usernameRegField.setError("Enter username.");
            usernameRegField.requestFocus();
            return true;
        }

        if(email.isEmpty()){
            emailRegField.setError("Enter email.");
            emailRegField.requestFocus();
            return true;
        }

        // TODO check if email is valid

        if(password.isEmpty()){
            passwordRegField.setError("Enter password.");
            passwordRegField.requestFocus();
            return true;
        }
        return false;
    }
}