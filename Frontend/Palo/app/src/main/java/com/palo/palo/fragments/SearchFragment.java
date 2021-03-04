package com.palo.palo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.R;
import com.palo.palo.volley.VolleySingleton;


public class SearchFragment extends Fragment {
    private EditText song;
    private TextView req;
    private Button b;
    String url = "https://60dfb9a0-6de1-47ff-9b9b-69c16d317496.mock.pstmn.io";
    private ProgressDialog p;
    private String TAG = SearchFragment.class.getSimpleName();
    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";

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
        song = view.findViewById(R.id.spotifySearch);
        req = view.findViewById(R.id.songResult);
        b = view.findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonSongRequest();
            }
        });
    }

    private void makeJsonSongRequest() {
        JsonObjectRequest jsonSongRequest = new JsonObjectRequest(Request.Method.GET,
                url + "/search?q=" + song.getText().toString(), null,
                response -> {
                    Log.d(TAG, response.toString());
                    req.setText("Response: " + response.toString());
                }, error -> VolleyLog.d(TAG, "Error: " + error.getMessage()));

        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonSongRequest);
    }


}