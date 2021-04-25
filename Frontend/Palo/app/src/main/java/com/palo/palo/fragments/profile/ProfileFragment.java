package com.palo.palo.fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.palo.palo.FeedAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.extendedPost.ExtendedPostActivity;
import com.palo.palo.model.Palo;
import com.palo.palo.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is for the users profile page and its functionality.
 * This class is associated with the fragment_profile.xml.
 */
public class ProfileFragment extends Fragment implements FeedAdapter.OnFeedListener, IProfileView {
    //temporary url
    private static String url = "https://440b43ef-556f-4d7d-a95d-081ca321b8f9.mock.pstmn.io";
    private TextView profileName;
    private ImageView profileImage;
    private TextView paloAmt;
    private TextView followerAmt;
    private TextView followingAmt;
    private static User user;
    private RecyclerView r;
    FeedAdapter postAdapter;
    List<Palo> palos;
//    private String str;
    private Context context;
    private IProfilePresenter profilePresenter;

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
        context = getActivity().getApplicationContext();
        user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        paloAmt = view.findViewById(R.id.paloAmt);
        followerAmt = view.findViewById(R.id.followerAmt);
        followingAmt = view.findViewById(R.id.followingAmt);
        r = view.findViewById(R.id.userPalos);

        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            if (v.equals(logoutButton)) {
                SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
            }
        });

        profileName.setText(user.getUsername());
        postAdapter = new FeedAdapter(getActivity().getApplicationContext(), new ArrayList<>(), this);
        r.setAdapter(postAdapter);
        r.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        profilePresenter = new ProfilePresenter(this, context, user.getId(), user.getUsername());
    }

    @Override
    public void onPaloClick(int position) {
        System.out.println("post clicked..." + palos.get(position).getCaption());
        Palo p = palos.get(position);
        Intent intent =  new Intent(getContext(), ExtendedPostActivity.class);
        intent.putExtra("selected_post", p);
        startActivity(intent);
    }

    @Override
    public void onLikeClicked(int position) {
        System.out.println("post like clicked..." + palos.get(position).getCaption());
    }

    @Override
    public void loadPalos(List<Palo> palos) {
       this.palos = palos;
       postAdapter.swapDataSet(palos);
    }

    @Override
    public void loadEmptyPosts() {
        //todo
    }
    
    @Override
    public void refreshFeed(int currentUserId) {
        //todo
    }

    @Override
    public void makeToast(String message) {
        //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        System.out.println("filler for the toast for errors in profile: " + message);
    }

    @Override
    public void updatePalo(int paloIndex, Palo palo) {
        postAdapter.updatePalo(paloIndex, palo);
    }

    @Override
    public Palo getPalo(int paloIndex) {
        return palos.get(paloIndex);
    }

    @Override
    public void setProfilePicture(String url) {
        Picasso.get().load(url).into(profileImage);
    }

    @Override
    public void setFollowersCount(String num) {
        followerAmt.setText(num);
    }

    @Override
    public void setFollowingCount(String num) {
        followingAmt.setText(num);
    }

    @Override
    public void setPaloCount(String num) {
        paloAmt.setText(num);
    }
}