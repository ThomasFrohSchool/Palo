package com.palo.palo.activities.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.palo.palo.R;
import com.palo.palo.fragments.profile.ProfileFragment;
import com.palo.palo.model.User;

public class ProfileActivity extends AppCompatActivity {
    User user;
    TextView profileHeader;
    ImageButton backButton;
    FragmentManager fragmentManager = getSupportFragmentManager();
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = getIntent().getParcelableExtra("user_obj");
        profileHeader = findViewById(R.id.profileHeaderText);
        profileHeader.setText(user.getUsername() + "'s profile");

        backButton = findViewById(R.id.backProfileButton);
        backButton.setOnClickListener(v -> finish());

        profileFragment = new ProfileFragment(user);
        fragmentManager.beginTransaction().add(R.id.profileFrame, profileFragment, "profileFrag").commit();
    }
}