package com.palo.palo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.palo.palo.R;

/**
 * This fragment is for the different PaloLists a user would follow or create.
 * This class is associated with fragment_palo_list.xml.
 */
public class PaloListFragment extends Fragment {

    public PaloListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_palo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView headerET = view.findViewById(R.id.palolist_header);
    }
}