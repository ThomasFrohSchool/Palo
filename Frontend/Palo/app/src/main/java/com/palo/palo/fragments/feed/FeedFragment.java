package com.palo.palo.fragments.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.palo.palo.FeedAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.extendedPost.ExtendedPostActivity;
import com.palo.palo.activities.profile.ProfileActivity;
import com.palo.palo.model.Palo;

import java.util.ArrayList;
import java.util.List;


/**
 * This fragment is for the feed and its functionalities.
 * This class is associated with fragment_feed.xml.
 */
public class FeedFragment extends Fragment implements FeedAdapter.OnFeedListener, IFeedView {
    private final String FEED_STR_TAG = FeedFragment.class.getSimpleName();
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;
    SwipeRefreshLayout layout;
    TextView emptyFeedMessage;
    Button refreshFeed;
    View myView;
    IFeedPresenter feedPresenter;
    private Context context;
    List<Palo> palos;

    public FeedFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        myView = view;
        context = getActivity().getApplicationContext();
        recyclerView = view.findViewById(R.id.songList);
        layout = view.findViewById(R.id.feedSwipeRefreshLayout);
        emptyFeedMessage = view.findViewById(R.id.emptyFeedMessage);
        //refreshFeed= view.findViewById(R.id.refreshFeedButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        feedAdapter = new FeedAdapter(context, new ArrayList<>(), this);
        recyclerView.setAdapter(feedAdapter);
        feedPresenter = new FeedPresenter(this, context,  SharedPrefManager.getInstance(myView.getContext()).getUser().getId());
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.setRefreshing(false);
                feedPresenter.loadFeed(SharedPrefManager.getInstance(myView.getContext()).getUser().getId());
            }
        });
    }

    @Override
    public void onPaloClick(int position) {
        System.out.println("post clicked..." + palos.get(position).getAttachment().getClass().getSimpleName());
        Palo p = palos.get(position);
        Intent intent =  new Intent(getContext(), ExtendedPostActivity.class);
        intent.putExtra("selected_post", palos.get(position));
        startActivity(intent);
    }

    @Override
    public void onLikeClicked(int position) {
        Palo palo =  palos.get(position);
        System.out.println("post like clicked..." + palo.getCaption() + palo.getIsLiked());
        feedPresenter.likePalo(position, palo.getId(), SharedPrefManager.getInstance(context).getUser().getId(), !palo.getIsLiked());
    }

    @Override
    public void onUserNameClicked(int position) {
        System.out.println("post like clicked..." + palos.get(position).getAuthorUsername());
        Palo p = palos.get(position);
        Intent intent =  new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("user_obj", palos.get(position).getAuthor());
        startActivity(intent);
    }

    @Override
    public void loadPalos(List<Palo> palos) {
        System.out.println("hello");
        this.palos = palos;
        if(palos.size() > 0) {
            emptyFeedMessage.setVisibility(View.INVISIBLE);
        }
        recyclerView.setVisibility(View.VISIBLE);
        feedAdapter.swapDataSet(palos);
    }

    @Override
    public void loadEmptyFeed() {
        recyclerView.setVisibility(View.INVISIBLE);
        emptyFeedMessage.setVisibility(View.VISIBLE);
        //refreshFeed.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshFeed(int currentUserId) {
        //TODO
    }

    @Override
    public void makeToast(String message) {
        System.out.println(message);
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updatePalo(int paloIndex, Palo palo) {
        feedAdapter.updatePalo(paloIndex, palo);
    }

    @Override
    public Palo getPalo(int paloIndex) {
        return palos.get(paloIndex);
    }

    @Override
    public void updateLikeToPalo(int paloIndex, boolean isLiked) {
        Palo p = palos.get(paloIndex);
        p.setIsLiked(isLiked);
        p.updateLikeCount(isLiked);
        System.out.println("updateLike = " + isLiked);
        feedAdapter.updatePalo(paloIndex, p);
    }
}
