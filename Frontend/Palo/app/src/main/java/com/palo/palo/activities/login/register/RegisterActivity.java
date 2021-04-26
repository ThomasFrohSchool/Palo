package com.palo.palo.activities.login.register;

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
import com.palo.palo.model.User;
import com.palo.palo.volley.AppController;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * This activity is for the register functionality.
 * This is associated with the activity_register.xml.
 */
public class RegisterActivity extends AppCompatActivity implements IRegisterView {
    EditText usernameRegField;
    EditText emailRegField;
    EditText passwordRegField;
    Button registerBtn;
    Context context;
    IRegisterPresenter presenter;
    private String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        setContentView(R.layout.activity_register);
        context = getApplicationContext();
        presenter = new RegisterPresenter(this, context);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        
        usernameRegField = findViewById(R.id.usernameRegister);
        emailRegField = findViewById(R.id.emailRegister);
        passwordRegField = findViewById(R.id.passwordRegister);

        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(v -> postRegister());
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
    public void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logd(String response) {
        Log.d(TAG,response);
    }

    @Override
    public void startNewActivity(Class<?> cls) {
        finish();
        startActivity(new Intent(getApplicationContext(), cls));
    }

    @Override
    public void postRegister() {
        JSONObject json = null;
        try {
            json = setupRegisterJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null) return;
        presenter.register(json);
    }

    @Override
    public void login(User user) {
        SharedPrefManager.getInstance(getApplicationContext()).login(user);
    }

    @Override
    public void dismissKeyboard() {
        Activity activity = this;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }

    private JSONObject setupRegisterJson() throws JSONException {
        String username = usernameRegField.getText().toString().trim();
        String email = emailRegField.getText().toString().trim();
        String password = passwordRegField.getText().toString().trim();

        if (hasEmptyCredentials(username, email, password)) return null;
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("email", email);
        json.put("password", password);
        return json;
    }
}