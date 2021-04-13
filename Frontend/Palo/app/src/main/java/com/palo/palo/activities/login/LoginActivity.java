package com.palo.palo.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.MainActivity;
import com.palo.palo.activities.login.register.RegisterActivity;
import com.palo.palo.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This activity class is used for the login functionality.
 * This class is associated with the activity_login.xml file.
 */
public class LoginActivity extends AppCompatActivity implements ILoginView {

    EditText emailField;
    EditText passwordField;
    Button register;
    Button login;
    Context context;
    ILoginPresenter presenter;
    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        presenter = new LoginPresenter(this, context);

        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);

        register = findViewById(R.id.toRegisterPage);
        register.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(v -> postLogin());
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void logd(String response) {
        Log.d(TAG,response);
    }

    public void startNewActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void postLogin() {
        JSONObject json = null;
        try {
            json = setupLoginJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null) return;
        presenter.login(json);
    }

    /**
     * This method is for login functionality when the login button is pressed.
     * Sends a post request to the backend and gets information back for if an existing account with
     * the given parameters works.
     */
    @Override
    public void login(User user) {
        SharedPrefManager.getInstance(context).login(user);
    }

    @Override
    public void dismissKeyboard() {
        Activity activity = this;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
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

        if(password.isEmpty()){
            passwordField.setError("Enter password.");
            passwordField.requestFocus();
            return true;
        }
        return false;
    }

    private JSONObject setupLoginJson() throws JSONException {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (hasEmptyCredentials(email, password)) return null;
        JSONObject json = new JSONObject();
        json.put("password", password);
        json.put("username", email);
        return json;
    }
}
