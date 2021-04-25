package com.palo.palo.fragments.feed;

public interface IFeedPresenter {
    public void loadFeed(int currentUserId);
    public void likePalo(int position, int paloId, int userId, boolean toLike);

}
