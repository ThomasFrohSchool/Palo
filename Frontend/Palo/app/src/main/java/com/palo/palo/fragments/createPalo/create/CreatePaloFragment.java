package com.palo.palo.fragments.createPalo.create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.*;
import com.palo.palo.model.Attachment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This fragment is for creating a post. Allows user to add caption and the ability to post a new Palo.
 * This class is associated with fragment_create_palo.xml.
 */
public class CreatePaloFragment extends Fragment implements ICreatePaloView {
    EditText captionField;
    View myView;
    Attachment attachment;
    Context context;
    ICreatePaloPresenter presenter;

    public CreatePaloFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_palo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        myView = view;
        context = getActivity().getApplicationContext();
        captionField = view.findViewById(R.id.caption_create_new_post);
        presenter = new CreatePaloPresenter(this, context, SharedPrefManager.getInstance(myView.getContext()).getUser().getId());

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

    /**
     * Makes post request to server to make a new post.
     * Makes request to "{server_url}/createPost/{user_id}".
     */
    public void post(){
        // receives currently {"message":"success"}

    @Override
    public void startNewActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void loadAttatchment(Attachment attachment) {
        this.attachment = attachment;
        ((TextView) myView.findViewById(R.id.songTitle)).setText(attachment.getTitle());
        ((TextView) myView.findViewById(R.id.songArtist)).setText(attachment.getArtist());
        Picasso.get().load(attachment.getAlbumCover()).into((ImageView) myView.findViewById(R.id.coverImage));
        ((TextView) myView.findViewById(R.id.coverImageURL)).setText(attachment.getAlbumCover());
    }

    @Override
    public void postPalo() {
        presenter.postPalo(setupPaloJson());
    }

    public JSONObject setupPaloJson(){
        JSONObject newPost = new JSONObject();
        try {
            newPost.put("description", captionField.getText().toString());
            newPost.put("spot_id", attachment.getSpotifyId());
            newPost.put("type", attachment.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newPost;
    }
}