package com.palo.palo.activities.extendedPost;

import android.content.Context;

import com.palo.palo.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExtendedPostPresenter implements IExtendedPostPresenter, IExtendedPostVolleyListener {
    private IExtendedPostView view;
    private ExtendedPostModel model;
    private int postId;

    public ExtendedPostPresenter(IExtendedPostView view, Context context, int postId) {
        this.view = view;
        this.model = new ExtendedPostModel(context);
        this.postId = postId;
    }

    @Override
    public void postComment(JSONObject commentJson) {
        model.postComment(commentJson, postId, this);
    }

    @Override
    public void loadComments() {
        model.getComments(postId, this);
    }

    @Override
    public void onCommentLoadSuccess(JSONArray response) throws JSONException {
        ArrayList<Comment> comments = new ArrayList<>();
        for(int i = 0; i < response.length(); i++) {
            Comment comment = new Comment(response.getJSONObject(i));
            model.updateUserInfo(i, comment.getAuthor_id(), this);
            comments.add(comment);
        }
        view.loadComments(comments);
    }

    @Override
    public void onPostSuccess(String message) {
        view.logd("Made new comment successfully.");
        view.makeToast("successfully posted.");
        view.clearCommentText();
        view.dismissKeyboard();
        loadComments();
    }

    @Override
    public void onUserSuccess(JSONObject jsonObject, int commentIndex) throws JSONException {
        try {
            view.updateUser(commentIndex, jsonObject.getString("username"));
            // TODO set profile image here...
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
        view.logd(message);
//        view.makeToast(message);
    }
}
