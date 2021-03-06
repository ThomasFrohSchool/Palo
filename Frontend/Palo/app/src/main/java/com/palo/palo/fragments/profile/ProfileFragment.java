package com.palo.palo.fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.palo.palo.FeedAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.EditBioActivity;
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
    private TextView bio;
    private ImageButton editBio;
    Button logoutButton;
    //View settingView;
    private User user;
    private RecyclerView r;
    SwipeRefreshLayout layout;
    FeedAdapter postAdapter;
    List<Palo> palos;
//    private String str;
    private Context context;
    private IProfilePresenter profilePresenter;
    User users;

    public ProfileFragment() {
    }

    public ProfileFragment(User user) {
        this.user = user;
    }

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
        if(user == null || user.getId() ==  SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser().getId()) {
            user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
            logoutButton = view.findViewById(R.id.logout);
            editBio = view.findViewById(R.id.editBio);
            editBio.setOnClickListener(v -> {
                if(v.equals(editBio)) {
                    Intent i = new Intent(context, EditBioActivity.class);
                    i.putExtra("ID",SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser().getId());
                    startActivity(i);
                }
            });
            logoutButton.setOnClickListener(v -> {
                if (v.equals(logoutButton)) {
                    user = null;
                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
                }
            });
        } else {
            editBio = view.findViewById(R.id.editBio);
            logoutButton = view.findViewById(R.id.logout);
            this.hideOwnProfileStuff();
        }
//        user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        paloAmt = view.findViewById(R.id.paloAmt);
        followerAmt = view.findViewById(R.id.followerAmt);
        followingAmt = view.findViewById(R.id.followingAmt);
        bio = view.findViewById(R.id.bio);
        r = view.findViewById(R.id.userPalos);
        r.setVisibility(View.VISIBLE);
        profileName.setText(user.getUsername());
        postAdapter = new FeedAdapter(getActivity().getApplicationContext(), new ArrayList<>(), this);
        r.setAdapter(postAdapter);
        r.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        profilePresenter = new ProfilePresenter(this, context, user.getId(), user.getUsername());
        layout = view.findViewById(R.id.profileSwipeRefreshLayout);

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.setRefreshing(false);
                profilePresenter.loadProfile(user.getId());
            }
        });
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
        Palo palo =  palos.get(position);
        System.out.println("post like clicked..." + palo.getCaption() + palo.getIsLiked());
        profilePresenter.likePalo(position, palo.getId(), SharedPrefManager.getInstance(context).getUser().getId(), !palo.getIsLiked());
    }

    @Override
    public void onUserNameClicked(int position) {
        //not needed you're already on profile
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
        System.out.println(num);
        followerAmt.setText(num);
    }

    @Override
    public void setFollowingCount(String num) {
        System.out.println(num);

        followingAmt.setText(num);
    }

    @Override
    public void setProfileBio(String bio) {
        System.out.println(bio);

        this.bio.setText(bio);
    }

    @Override
    public void setPaloCount(String num) {
        System.out.println(num);

        paloAmt.setText(num);
    }

    @Override
    public void hideOwnProfileStuff() {
        logoutButton.setVisibility(View.INVISIBLE);
        editBio.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateLikeToPalo(int paloIndex, boolean isLiked) {
        Palo p = palos.get(paloIndex);
        p.setIsLiked(isLiked);
        System.out.println("updateLike = " + isLiked);
        postAdapter.updatePalo(paloIndex, p);
    }
}