package com.palo.palo.fragments.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.palo.palo.FeedAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.ExtendedPostActivity;
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
    TextView emptyFeedMessage;
    Button refreshFeed;
    View myView;
    IFeedPresenter feedPresenter;
    Context context;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        feedAdapter = new FeedAdapter(context, new ArrayList<>(), this);
        recyclerView.setAdapter(feedAdapter);
        feedPresenter = new FeedPresenter(this, context,  SharedPrefManager.getInstance(myView.getContext()).getUser().getId());
    }

    @Override
    public void onPaloClick(int position) {
        System.out.println("post clicked..." + palos.get(position).getCaption());
        Palo p = palos.get(position);
        Intent intent =  new Intent(getContext(), ExtendedPostActivity.class);
        intent.putExtra("selected_post", palos.get(position));
        startActivity(intent);
    }

    @Override
    public void onLikeClicked(int position) {
        System.out.println("post like clicked..." + palos.get(position).getCaption());
    }

    @Override
    public void loadPalos(List<Palo> palos) {
        System.out.println("hello");
        this.palos = palos;
        recyclerView.setVisibility(View.VISIBLE);
        feedAdapter.swapDataSet(palos);
    }

    @Override
    public void loadEmptyFeed() {
        recyclerView.setVisibility(View.INVISIBLE);
        emptyFeedMessage.setVisibility(View.VISIBLE);
        refreshFeed.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshFeed(int currentUserId) {
        //TODO
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updatePalo(int paloIndex, Palo palo) {
        feedAdapter.updatePalo(paloIndex, palo);
    }

    @Override
    public Palo getPalo(int paloIndex) {
        return palos.get(paloIndex);
    }
}
