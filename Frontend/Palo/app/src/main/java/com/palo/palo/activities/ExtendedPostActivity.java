package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.palo.palo.CommentAdapter;
import com.palo.palo.R;
import com.palo.palo.model.Comment;
import com.palo.palo.model.Palo;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.palo.palo.volley.ServerURLs.GET_COMMENTS;

public class ExtendedPostActivity extends AppCompatActivity {
    Palo attachedPalo;
    RecyclerView commentRV;
    CommentAdapter commentAdapter;
    List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_post);

        ImageButton backButton = findViewById(R.id.backToFeedButton);
        backButton.setOnClickListener(v -> finish());
        attachedPalo = getIntent().getParcelableExtra("selected_post");
        setView(attachedPalo);

        //comment initialization
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
        int post_id =attachedPalo.getId();
        post_id = 10;
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

    private  Comment extractComment(JSONObject commentJSON) throws JSONException {
        return new Comment(commentJSON.getInt("user_id"),
                           commentJSON.getString("createDate"),
                           commentJSON.getString("body"));
    }
}