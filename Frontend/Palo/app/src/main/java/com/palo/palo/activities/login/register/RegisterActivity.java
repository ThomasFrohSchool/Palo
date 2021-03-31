package com.palo.palo.activities.login.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.IView;
import com.palo.palo.activities.MainActivity;
import com.palo.palo.model.User;
import com.palo.palo.volley.AppController;
import com.palo.palo.volley.JsonRequest.ServerJsonRequest;

import org.json.JSONException;


/**
 * This activity is for the register functionality.
 * This is associated with the activity_register.xml.
 */
public class RegisterActivity extends AppCompatActivity implements IView {
    EditText usernameRegField;
    EditText emailRegField;
    EditText passwordRegField;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        setContentView(R.layout.activity_register);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        ServerJsonRequest serverRequest = new ServerJsonRequest();
        final RegisterLogic logic = new RegisterLogic(this, serverRequest);
        
        usernameRegField = findViewById(R.id.usernameRegister);
        emailRegField = findViewById(R.id.emailRegister);
        passwordRegField = findViewById(R.id.passwordRegister);

        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(v -> register(logic));
    }
    
    public void register(RegisterLogic logic){
        try {
            String username = usernameRegField.getText().toString().trim();
            String email = emailRegField.getText().toString().trim();
            String password = passwordRegField.getText().toString().trim();
            if (hasEmptyCredentials(username, email, password)) return;
            logic.register(username, email, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is for checking if any of the register fields are empty.
     * @param username: Given username
     * @param email: Given email
     * @param password: Given password
     * @return Returns if any of the register fields are empty
     */
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

    @Override
    public void showText(String s) {
        return; // not used
    }

    @Override
    public void toastText(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Class<?> cls) {
        finish();
        startActivity(new Intent(getApplicationContext(), cls));
    }

    @Override
    public void login(User user) {
        SharedPrefManager.getInstance(getApplicationContext()).login(user);
    }
}