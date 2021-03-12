package com.palo.palo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.model.User;

/**
 * This fragment is for the users profile page and its functionality.
 * This class is associated with the fragment_profile.xml.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView headerET = view.findViewById(R.id.profile_header);
        User user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
        headerET.setText(user.getUsername());
        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            if (v.equals(logoutButton)) {
                SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
            }
        });

    }
}