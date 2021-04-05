package com.palo.palo.model;

public class Comment {
    private User author;
    private int author_id;
    private String postDate;
    private String caption;
    
    public Comment(){}
    public Comment(int author_id, String postDate, String caption){
        this.author_id = author_id;
        this.postDate = postDate;
        this.caption = caption;
        this.author = new User(author_id);
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
}
