package com.palo.palo.model;

public class Palo {
    private User author;
    private String postDate, caption;
    private Song attachedSong;

    public Palo(){}

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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

    public Song getAttachedSong() {
        return attachedSong;
    }

    public void setAttachedSong(Song attachedSong) {
        this.attachedSong = attachedSong;
    }

    public String getProfileImage() {
        return this.author.getProfileImage();
    }

    public String getAuthorUsername() {
        return this.author.getUsername();

    }
}

