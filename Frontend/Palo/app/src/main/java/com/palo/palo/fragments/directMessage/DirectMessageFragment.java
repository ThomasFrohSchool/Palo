package com.palo.palo.fragments.directMessage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.palo.palo.R;
import com.palo.palo.UserSearchDMAdapter;
import com.palo.palo.activities.DirectMessageUserActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is for the different PaloLists a user would follow or create.
 * This class is associated with fragment_palo_list.xml.
 */
public class DirectMessageFragment extends Fragment implements UserSearchDMAdapter.onUserDMListener, IDirectMessageView {
    EditText searchET;
    ImageButton searchButton;
    Button clearSearchResults;
    RecyclerView recyclerView;
    UserSearchDMAdapter userSearchDMAdapter;
    List<String> usernames;
    Context context;
    IDirectMessagePresenter presenter;
    String TAG = DirectMessageFragment.class.getSimpleName();
    
    public DirectMessageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_direct_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        searchET = view.findViewById(R.id.searchUserDM);
        searchButton = view.findViewById(R.id.userSearchBarButton);
        clearSearchResults = view.findViewById(R.id.clearUserSearchButton);
        recyclerView = view.findViewById(R.id.directMessageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        userSearchDMAdapter = new UserSearchDMAdapter(context, new ArrayList<>(), this);
        recyclerView.setAdapter(userSearchDMAdapter);
        presenter = new DirectMessagePresenter(this, context);
        searchButton.setOnClickListener(v -> presenter.loadUsersSearch(searchET.getText().toString()));
        clearSearchResults.setOnClickListener(v -> presenter.loadUsers());
        presenter.loadUsers();
    }

    @Override
    public void onUserClicked(int position) {
        Intent intent = new Intent(context, DirectMessageUserActivity.class);
        intent.putExtra("USER_TO_SEND", usernames.get(position));
        startActivity(intent);
    }

    @Override
    public void loadUsers(List<String> users) {
        clearSearchResults.setVisibility(View.GONE);
        this.usernames = users;
        userSearchDMAdapter.swapDataSet(users);
    }

    @Override
    public void loadUsersSearch(List<String> users) {
        clearSearchResults.setVisibility(View.VISIBLE);
        this.usernames = users;
        userSearchDMAdapter.swapDataSet(users);
    }

    @Override
    public void makeToast(String message) {
        System.out.println(message);
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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