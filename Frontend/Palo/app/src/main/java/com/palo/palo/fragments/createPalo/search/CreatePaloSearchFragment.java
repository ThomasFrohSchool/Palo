package com.palo.palo.fragments.createPalo.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.palo.palo.AttachementSearchAdapter;

import com.palo.palo.R;
import com.palo.palo.fragments.createPalo.search.ISearchPresenter;
import com.palo.palo.fragments.createPalo.search.ISearchView;
import com.palo.palo.fragments.createPalo.search.SearchPresenter;
import com.palo.palo.model.Attachment;

import java.util.ArrayList;
import java.util.List;

public class CreatePaloSearchFragment extends Fragment implements ISearchView {
    private RecyclerView searchRecyclerView;
    private EditText searchET;
    private ImageButton searchButton;
    AttachementSearchAdapter searchAdapter;
    List<Attachment> attachments;
    Context context;
    ISearchPresenter presenter;

    public CreatePaloSearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_palo_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        searchET = view.findViewById(R.id.attached_song_search_bar_edit_text);
        searchButton = view.findViewById(R.id.attached_song_search_bar_button);
        searchRecyclerView = view.findViewById(R.id.create_new_post_search_recycler_view);

        presenter = new SearchPresenter(this, context);
        searchButton.setOnClickListener(v -> presenter.loadSearchResults(searchET.getText().toString()));
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        searchAdapter = new AttachementSearchAdapter(context, new ArrayList<>());
        searchRecyclerView.setAdapter(searchAdapter);
    }
    
    public Attachment getSelectedSong(){
        return searchAdapter.getSelectedSong();
    }

    @Override
    public void loadAttatchments(List<Attachment> attachments) {
        this.attachments = attachments;
        searchRecyclerView.setVisibility(View.VISIBLE);
        searchAdapter.swapDataSet(attachments);
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissKeyboard() {
        Activity activity = getActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }
}