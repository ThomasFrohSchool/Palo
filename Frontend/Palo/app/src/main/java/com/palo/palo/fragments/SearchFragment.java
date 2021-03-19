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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.palo.palo.R;
import com.palo.palo.volley.VolleySingleton;

/**
 * Fragment to allow user to search songs, albums, artist, etc. from Spotify.
 */
public class SearchFragment extends Fragment {
    private EditText song;
    private TextView req;
    private Button b;
    //private String url = "https://60dfb9a0-6de1-47ff-9b9b-69c16d317496.mock.pstmn.io";
    private String url = "http://coms-309-021.cs.iastate.edu:8080";
    private ProgressDialog p;
    private String JSONTAG = SearchFragment.class.getSimpleName();
    private String STRINGTAG = SearchFragment.class.getSimpleName();
    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";
    private String tag_string_req = "string_req";

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

        p = new ProgressDialog(getActivity().getApplicationContext());
        p.setMessage("Loading...");
        p.setCancelable(false);

        b.setOnClickListener(v -> makeStringSongRequest());
    }

    /**
     * Makes JSONObjectRequest to SERVER/search?q=[TEXT], where TEXT is a string to what the user is searching.
     * Adds request to VolleySingleton request queue. Server responds with a json string containing a list of albums, osngs, and artist from Spotify.
     */
    private void makeJsonSongRequest() {
        //p.show();
        JsonObjectRequest jsonSongRequest = new JsonObjectRequest(Request.Method.GET,
                url + "/search?q=" + song.getText().toString(), null,
                response -> {
                    Log.d(JSONTAG, response.toString());
                    req.setText("Response: " + response.toString());
                }, error -> VolleyLog.d(JSONTAG, "Error: " + error.getMessage()));
        //p.hide();

        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonSongRequest);
    }

    /**
     * Makes StringRequest to SERVER/search?q=[TEXT], where TEXT is a string to what the user is searching.
     * Adds request to VolleySingleton request queue. Server responds with a string containing a list of albums, osngs, and artist from Spotify.
     */
    private void makeStringSongRequest() {
        //p.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "/search?q=" + song.getText().toString(),
                response -> {
                    Log.d(STRINGTAG, response);
                    req.setText("Response: " + response);
                }, error -> error.printStackTrace());
        //p.hide();
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }
}