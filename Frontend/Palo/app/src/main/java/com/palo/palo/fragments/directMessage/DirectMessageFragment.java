package com.palo.palo.fragments.directMessage;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.palo.palo.R;
import com.palo.palo.UserSearchAdapter;
import com.palo.palo.fragments.searchPage.SearchFragment;
import com.palo.palo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is for the different PaloLists a user would follow or create.
 * This class is associated with fragment_palo_list.xml.
 */
public class DirectMessageFragment extends Fragment implements UserSearchAdapter.onUserListener, IDirectMessageView {
    ImageButton searchButton;
    RecyclerView recyclerView;
    UserSearchAdapter userSearchAdapter;
    List<User> users;
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
        searchButton = view.findViewById(R.id.userSearchBarButton);
        recyclerView = view.findViewById(R.id.userSearchBarButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        userSearchAdapter = new UserSearchAdapter(context, new ArrayList<>(), this);
        presenter = new DirectMessagePresenter();
    }

    @Override
    public void onFollowClicked(int position) {
        //TODO go to chat screen...
    }

    @Override
    public void loadUsers(List<User> users) {
        
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