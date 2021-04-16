package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.CommentAdapter;
import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.model.Comment;
import com.palo.palo.model.Palo;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.palo.palo.volley.ServerURLs.CREATE_COMMENT;
import static com.palo.palo.volley.ServerURLs.GET_COMMENTS;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

/**
 * This activity is to show an extended view of a post. It is accessible from feed or profile views.
 * Basic post detials (caption, author, post date) are still visible. Comments are displayed and are
 * able to be made on the post.
 */
public class ExtendedPostActivity extends AppCompatActivity {
    Palo attachedPalo;
    RecyclerView commentRV;
    CommentAdapter commentAdapter;
    List<Comment> comments;
    EditText newCommentBody;
    TextView postComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_post);

        ImageButton backButton = findViewById(R.id.backToFeedButton);
        backButton.setOnClickListener(v -> finish());
        attachedPalo = getIntent().getParcelableExtra("selected_post");
        setView(attachedPalo);
        // TODO set current user profile pic next to make comment text field.

        //comment initialization
        newCommentBody = findViewById(R.id.addCommentBody);
        postComment = findViewById(R.id.postCommentButton);
        postComment.setOnClickListener(v -> post());

        commentAdapter = new CommentAdapter(getApplicationContext(), new ArrayList<>());
        commentRV = findViewById(R.id.commentRecyclerView);
        commentRV.setAdapter(commentAdapter);
        commentRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        updateCommentView();
    }

    /**
     * Sets the basic post information received from feed when post was clicked.
     * @param palo
     */
    private void setView(Palo palo){
        Picasso.get().load(palo.getAuthor().getProfileImage()).into((ImageView) findViewById(R.id.paloAuthorProfileImage));
        ((TextView) findViewById(R.id.paloAuthorUserName)).setText(palo.getAuthor().getUsername());
        ((TextView) findViewById(R.id.paloDate)).setText(palo.getPostDate()); // TODO display date in a nicer way
        ((TextView) findViewById(R.id.paloCaption)).setText(palo.getCaption());

        ((TextView) findViewById(R.id.songTitle)).setText(palo.getAttachment().getTitle());
        ((TextView) findViewById(R.id.songArtist)).setText(palo.getAttachment().getArtist());
        Picasso.get().load(palo.getAttachment().getAlbumCover()).into((ImageView) findViewById(R.id.coverImage));
    }

    /**
     * Updates comment view based with data received on volley call to the surver.
     */
    private void updateCommentView(){
        JsonArrayRequest request = getComments();
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Sets up comment volley request. Makes request to "{server_url}/posts/getcomments/{post_id}".
     * On response sets comment recyclerview with received data.
     * @return
     */
    private JsonArrayRequest getComments(){
        int post_id = attachedPalo.getId();
        return new JsonArrayRequest(Request.Method.GET, GET_COMMENTS + post_id,null,
                response -> {
                    if (response.length() == 0){
                        System.out.println("NO COMMENTS FOR POST");

                    } else {
                        comments = new ArrayList<>();
                        for(int i = 0; i < response.length(); i++) {
                            Comment comment = null;
                            try {
                                comment = extractComment(response.getJSONObject(i));
                                userRequest(i, comment.getAuthor().getId());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            comments.add(comment);
                        }
                        commentAdapter.swapDataSet(comments);
                    }
                },
                error -> {System.out.println(error.getMessage());});
    }

    /**
     * Makes volley call to server to update username and profile picture on comments received.
     * Makes request to "{server_url}/user/{user_id}".
     * @param commentIndex
     * @param userId
     */
    private void userRequest(int commentIndex, int userId){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,USER_BY_ID + userId, null, response -> {
            try {
                comments.get(commentIndex).setAuthorUsername(response.getString("username"));
                commentAdapter.updateComment(commentIndex, comments.get(commentIndex));
                // TODO set profile image here...
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> System.out.println(error.getMessage()));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Makes post request to server to make a new comment on post.
     * Makes request to "{server_url}/createComment/{post_id}".
     */
    public void post(){
        JSONObject newPost = new JSONObject();
        try {
            newPost.put("body", newCommentBody.getText().toString());
            newPost.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = CREATE_COMMENT + attachedPalo.getId();
        JsonObjectRequest request = new JsonObjectRequest(url, newPost, json -> {
            try {
                if(json.getString("message").equals("success")) {
                    Toast.makeText(this, "successfully posted.", Toast.LENGTH_LONG).show();
                    System.out.println("Made new comment successfully");
                    newCommentBody.setText("");
                    dismissKeyboard(this);
                    updateCommentView();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            System.out.println(error.getMessage());
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Creates comment object from json data received from server. Sets userid, postdate, and caption of comment.
     * @param commentJSON
     * @return
     * @throws JSONException
     */
    private  Comment extractComment(JSONObject commentJSON) throws JSONException {
        return new Comment(commentJSON.getInt("user_id"),
                           commentJSON.getString("createDate"),
                           commentJSON.getString("body"));
    }

    /**
     * Minimizes keyboard when called.
     * @param activity
     */
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}