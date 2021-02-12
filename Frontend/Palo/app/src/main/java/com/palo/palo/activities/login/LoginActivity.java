package com.palo.palo.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.HomeActivity;
import com.palo.palo.model.User;
import com.palo.palo.volley.ServerURLs;
import com.palo.palo.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
            startActivity(new Intent(this, HomeActivity.class));
        }
        
        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);

        register = findViewById(R.id.toRegisterPage);
        register.setOnClickListener(v -> {
            finish();
//            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(v -> login());
    }

    // todo - should probably create a login class and move this there
    private void login(){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (hasEmptyCredentials(email, password)) return;

        StringRequest request = new StringRequest(Request.Method.POST, ServerURLs.LOGIN,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        if(!json.getBoolean("error")) {
                            JSONObject userJson = json.getJSONObject("user");

                            User user = new User(userJson.getInt("id"), userJson.getString("username"), userJson.getString("email"));
                            SharedPrefManager.getInstance(getApplicationContext()).login(user);

                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                        } else {
                            Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) 
            {
               @Override
                  protected Map<String, String> getParams() {
                      Map<String, String> params = new HashMap<>();
                      params.put("username", email);
                      params.put("password", password);
                      return params;
                  }
            };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

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