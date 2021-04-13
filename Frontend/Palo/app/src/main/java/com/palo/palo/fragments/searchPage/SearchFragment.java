package com.palo.palo.fragments.searchPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.palo.palo.AttachementSearchAdapter;
import com.palo.palo.R;
import com.palo.palo.UserSearchAdapter;
import com.palo.palo.model.Attachment;
import com.palo.palo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to allow user to search songs, albums, artist, etc. from Spotify.
 */
public class SearchFragment extends Fragment implements ISearchView {
    private EditText song;
    private Button bSpotify;
    private Button bUsers;
    private RecyclerView r;
    private AttachementSearchAdapter spotifyAdapter;
    private UserSearchAdapter userAdapter;
    private ProgressDialog p;
    private String TAG = SearchFragment.class.getSimpleName();
    //    private String JSONTAG = SearchFragment.class.getSimpleName();
//    private String STRINGTAG = SearchFragment.class.getSimpleName();
//    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";
//    private String tag_string_req = "string_req";
    private List<Attachment> attachments;
    private List<User> users;
    private Context context;
    ISearchPresenter presenter;

    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        presenter = new SearchPresenter(this, context);

        song = view.findViewById(R.id.spotifySearch);
        bSpotify = view.findViewById(R.id.buttonSpotify);
        bUsers = view.findViewById(R.id.buttonUsers);
        r = view.findViewById(R.id.res);
        r.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        spotifyAdapter = new AttachementSearchAdapter(context, new ArrayList<>());
        userAdapter = new UserSearchAdapter(context, new ArrayList<>());


        p = new ProgressDialog(getActivity().getApplicationContext());
        p.setMessage("Loading...");
        p.setCancelable(false);

        bSpotify.setOnClickListener(v -> presenter.loadSpotifySearchResults(song.getText().toString()));
        bUsers.setOnClickListener(v -> presenter.loadUserSearchResults(song.getText().toString()));
    }

    @Override
    public void loadAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        r.setVisibility(View.VISIBLE);
        r.setAdapter(spotifyAdapter);
        spotifyAdapter.swapDataSet(attachments);
    }

    @Override
    public void loadUsers(List<User> users) {
        this.users = users;
        r.setVisibility(View.VISIBLE);
        r.setAdapter(userAdapter);
        userAdapter.swapDataSet(users);
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void logd(String response) {
        Log.d(TAG,response);
    }

    @Override
    public void dismissKeyboard() {
        Activity activity = getActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }
}