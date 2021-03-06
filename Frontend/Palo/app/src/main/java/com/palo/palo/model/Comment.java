package com.palo.palo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model for comment on post.
 */
public class Comment {
    private User author;
    private int author_id;
    private String postDate;
    private String caption;
    private String authorUsername;
    
    public Comment(){}
    public Comment(int author_id, String postDate, String caption){
        this.author_id = author_id;
        this.postDate = postDate;
        this.caption = caption;
        this.author = new User(author_id);
    }
    public Comment(JSONObject commentJSON) throws JSONException {
        author_id = commentJSON.getInt("user_id");
        author = new User(author_id);
        postDate = commentJSON.getString("createDate");
        caption = commentJSON.getString("body");
    }
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setAuthorUsername(String username) {
        this.author.setUsername(username);
    }

    public void setAuthorProfileImage(String image) { this.author.setProfileImage(image); }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getAuthor_id() { return author_id; }
}
