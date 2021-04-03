package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import static com.palo.palo.volley.ServerURLs.CREATE_POST;
import static com.palo.palo.volley.ServerURLs.GET_COMMENTS;

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
    private void setView(Palo palo){
        Picasso.get().load(palo.getAuthor().getProfileImage()).into((ImageView) findViewById(R.id.paloAuthorProfileImage));
        ((TextView) findViewById(R.id.paloAuthorUserName)).setText(palo.getAuthor().getUsername());
        ((TextView) findViewById(R.id.paloDate)).setText(palo.getPostDate()); // TODO display date in a nicer way
        ((TextView) findViewById(R.id.paloCaption)).setText(palo.getCaption());

        ((TextView) findViewById(R.id.songTitle)).setText(palo.getAttachment().getTitle());
        ((TextView) findViewById(R.id.songArtist)).setText(palo.getAttachment().getArtist());
        Picasso.get().load(palo.getAttachment().getAlbumCover()).into((ImageView) findViewById(R.id.coverImage));
    }

    private void updateCommentView(){
        JsonArrayRequest request = getComments();
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private JsonArrayRequest getComments(){
        int post_id = attachedPalo.getId();
        post_id = 10; //todo delete this
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            comments.add(comment);
                        }
                    }
                    commentAdapter.swapDataSet(comments);
                },
                error -> {System.out.println(error.getMessage());});
    }

    public void post(){
        JSONObject newPost = new JSONObject();
        try {
            newPost.put("body", newCommentBody.getText().toString());
            newPost.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = CREATE_COMMENT + attachedPalo.getId();
        url = CREATE_COMMENT + "10";
        JsonObjectRequest request = new JsonObjectRequest(url, newPost, json -> {
            try {
                if(json.getString("message").equals("success")) {
                    Toast.makeText(this, "successfully posted.", Toast.LENGTH_LONG).show();
                    System.out.println("Made new comment successfully");
                    newCommentBody.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            System.out.println(error.getMessage());
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private  Comment extractComment(JSONObject commentJSON) throws JSONException {
        return new Comment(commentJSON.getInt("user_id"),
                           commentJSON.getString("createDate"),
                           commentJSON.getString("body"));
    }
}