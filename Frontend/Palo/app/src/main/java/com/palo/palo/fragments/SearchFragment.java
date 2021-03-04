package com.palo.palo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.palo.palo.R;


public class SearchFragment extends Fragment {
    private EditText search;
    private ProgressDialog p;
    private String TAG = SearchFragment.class.getSimpleName();

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
        //TextView headerET = view.findViewById(R.id.search_header);
        EditText search = view.findViewById((R.id.spotifySearch));

    }

    private void getJsonObject() {

    }


}