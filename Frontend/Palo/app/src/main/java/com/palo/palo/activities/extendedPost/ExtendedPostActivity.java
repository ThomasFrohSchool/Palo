package com.palo.palo.activities.extendedPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palo.palo.CommentAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.activities.profile.ProfileActivity;
import com.palo.palo.model.Comment;
import com.palo.palo.model.Palo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExtendedPostActivity extends AppCompatActivity implements CommentAdapter.OnCommentListener, IExtendedPostView{
    Palo attachedPalo;
    RecyclerView commentRV;
    CommentAdapter commentAdapter;
    List<Comment> comments;
    EditText newCommentBody;
    TextView postComment;
    TextView likeTV;
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
        likeTV = findViewById(R.id.paloLike);
        likeTV.setOnClickListener(v -> likeClicked());

        // TODO set current user profile pic next to make comment text field.

        //comment initialization
        newCommentBody = findViewById(R.id.addCommentBody);
        postComment = findViewById(R.id.postCommentButton);
        postComment.setOnClickListener(v -> postComment());
        commentAdapter = new CommentAdapter(getApplicationContext(), new ArrayList<>(), this);
        commentRV = findViewById(R.id.commentRecyclerView);
        commentRV.setAdapter(commentAdapter);
        commentRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        presenter.loadComments();
    }

    public void likeClicked(){
        System.out.println("post like clicked..." + attachedPalo.getIsLiked());
        presenter.likePalo(attachedPalo.getId(), SharedPrefManager.getInstance(context).getUser().getId(), !attachedPalo.getIsLiked());
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
    public void updateLikeToPalo( boolean isLiked) {
        attachedPalo.setIsLiked(isLiked);
        if(isLiked) likeTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_heart_full,0,0,0);
        else likeTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_heart_empty,0,0,0);
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

    @Override
    public void onCommentUserClicked(int position) {
        System.out.println("post like clicked..." + comments.get(position).getAuthor().getUsername());
        Intent intent =  new Intent(this, ProfileActivity.class);
        intent.putExtra("user_obj", comments.get(position).getAuthor());
        startActivity(intent);
    }
}