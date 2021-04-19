package com.palo.palo.activities.extendedPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palo.palo.CommentAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.model.Comment;
import com.palo.palo.model.Palo;
import com.palo.palo.model.Song;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExtendedPostActivity extends AppCompatActivity implements IExtendedPostView{
    Palo attachedPalo;
    RecyclerView commentRV;
    CommentAdapter commentAdapter;
    List<Comment> comments;
    EditText newCommentBody;
    TextView postComment;
    WebView playbackWebView;
    Context context;
    IExtendedPostPresenter presenter;
    private String TAG = ExtendedPostActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_extended_post);
        ImageButton backButton = findViewById(R.id.backToFeedButton);
        backButton.setOnClickListener(v -> finish());
        attachedPalo = getIntent().getParcelableExtra("selected_post");
        setPaloView(attachedPalo);
        presenter = new ExtendedPostPresenter(this, context, attachedPalo.getId());

        // TODO set current user profile pic next to make comment text field.

        //comment initialization
        newCommentBody = findViewById(R.id.addCommentBody);
        postComment = findViewById(R.id.postCommentButton);
        postComment.setOnClickListener(v -> postComment());
        commentAdapter = new CommentAdapter(getApplicationContext(), new ArrayList<>());
        commentRV = findViewById(R.id.commentRecyclerView);
        commentRV.setAdapter(commentAdapter);
        commentRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if(attachedPalo.getAttachment() instanceof Song){
            makeToast("attachment is a song...");
            if(((Song) attachedPalo.getAttachment()).getPlaybackLink() == null){
                makeToast("null playback link... need to handle at somepoint");
            } else {
                makeToast("playback link... =" + ((Song) attachedPalo.getAttachment()).getPlaybackLink() );
                playbackWebView = findViewById(R.id.playbackWebView);
                playbackWebView.setVisibility(View.VISIBLE);
                playbackWebView.loadUrl(((Song) attachedPalo.getAttachment()).getPlaybackLink());
    //            presenter.loadPlaybackLink();
                playbackWebView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }
        }
        presenter.loadComments();
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
    public void setPaloView(Palo palo) {
        Picasso.get().load(palo.getAuthor().getProfileImage()).into((ImageView) findViewById(R.id.paloAuthorProfileImage));
        ((TextView) findViewById(R.id.paloAuthorUserName)).setText(palo.getAuthor().getUsername());
        ((TextView) findViewById(R.id.paloDate)).setText(palo.getPostDate()); // TODO display date in a nicer way
        ((TextView) findViewById(R.id.paloCaption)).setText(palo.getCaption());

        ((TextView) findViewById(R.id.songTitle)).setText(palo.getAttachment().getTitle());
        ((TextView) findViewById(R.id.songArtist)).setText(palo.getAttachment().getArtist());
        Picasso.get().load(palo.getAttachment().getAlbumCover()).into((ImageView) findViewById(R.id.coverImage));
    }

    @Override
    public void loadComments(List<Comment> comments) {
        this.comments = comments;
        commentAdapter.swapDataSet(comments);
    }

    @Override
    public void postComment() {
        presenter.postComment(setupCommentJson());
    }

    @Override
    public void clearCommentText() {
        newCommentBody.setText("");
    }

    @Override
    public void updateUser(int commentIndex, String username) {
        comments.get(commentIndex).setAuthorUsername(username);
        commentAdapter.updateComment(commentIndex, comments.get(commentIndex));
    }

    @Override
    public void dismissKeyboard() {
        Activity activity = this;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }

    public JSONObject setupCommentJson(){
        JSONObject newComment = new JSONObject();
        try {
            newComment.put("body", newCommentBody.getText().toString());
            newComment.put("user_id", SharedPrefManager.getInstance(context).getUser().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newComment;
    }
}