package com.palo.palo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.model.User;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;


public class ProfileFragment extends Fragment {
    //temporary url
    private String url = "https://440b43ef-556f-4d7d-a95d-081ca321b8f9.mock.pstmn.io";
    private TextView profileName;
    private ImageView profileImage;
    private TextView paloAmt;
    private TextView palos;
    private TextView followerAmt;
    private TextView followers;
    private TextView followingAmt;
    private TextView following;
    private User user;
    private ProgressDialog p;

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
        //TextView headerET = view.findViewById(R.id.profile_header);
        user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
        //headerET.setText(user.getUsername());
        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        paloAmt = view.findViewById(R.id.paloAmt);
        palos = view.findViewById(R.id.Palos);
        followerAmt = view.findViewById(R.id.followerAmt);
        followers = view.findViewById(R.id.Followers);
        followingAmt = view.findViewById(R.id.followingAmt);
        following = view.findViewById(R.id.Following);
        //p = new ProgressDialog(getActivity().getApplicationContext());
        //p.setMessage("Loading...");

        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            if (v.equals(logoutButton)) {
                SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
            }
        });

        profileName.setText(user.getUsername());
        getProfile();
    }

    private void getProfile() {
        //p.show();
        System.out.println(url + "/profile?q=" + user.getUsername());
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, url + "/profile?q=" + user.getUsername(), null,
                response -> {
                    try {
                        String imgLink = response.getString("profileImage");
                        Picasso.get().load(imgLink).into(profileImage);
                        paloAmt.setText(response.getString("palos"));
                        followerAmt.setText(response.getString("followers"));
                        followingAmt.setText(response.getString("following"));
                        System.out.println(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        //p.hide();
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(j);
    }
}