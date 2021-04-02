package com.palo.palo.model;

public class Comment {
    private String author;
    private String postDate;
    private String caption;
    
    public Comment(){}
    public Comment(String author, String postDate, String caption){
        this.author = author;
        this.postDate = postDate;
        this.caption = caption;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
