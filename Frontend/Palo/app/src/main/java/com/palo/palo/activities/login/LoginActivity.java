package com.palo.palo.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.MainActivity;
import com.palo.palo.activities.login.register.RegisterActivity;
import com.palo.palo.model.User;
import com.palo.palo.volley.ServerURLs;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This activity class is used for the login functionality.
 * This class is associated with the activity_login.xml file.
 */
public class LoginActivity extends AppCompatActivity {

    EditText emailField;
    EditText passwordField;
    Button register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);

        register = findViewById(R.id.toRegisterPage);
        register.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(v -> login());
    }

    // todo - should probably create a login class and move this there

    /**
     * This method is for login functionality when the login button is pressed.
     * Sends a post request to the backend and gets information back for if an existing account with
     * the given parameters works.
     */
    private void login(){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (hasEmptyCredentials(email, password)) return;
        Map<String, String> params = new HashMap<>();
        params.put("password", password);
        params.put("username", email);

        JsonObjectRequest request = new JsonObjectRequest(ServerURLs.LOGIN, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                try {
                    if(!json.getBoolean("error")) {

                        JSONObject userJson = json.getJSONObject("user");

                        User user = new User(Integer.parseInt(userJson.getString("id")), userJson.getString("username"), userJson.getString("email"));
                        SharedPrefManager.getInstance(getApplicationContext()).login(user);

                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

    /**
     * This method is for checking if the login fields are empty or not.
     * @param email: Given email
     * @param password: Given password
     * @return Returns if any of the login fields are empty
     */
    private boolean hasEmptyCredentials(String email, String password){
        if(email.isEmpty()){
            emailField.setError("Enter email.");
            emailField.requestFocus();
            return true;
        }

        // TODO check if email is valid

        if(password.isEmpty()){
            passwordField.setError("Enter password.");
            passwordField.requestFocus();
            return true;
        }
        return false;
    }
}