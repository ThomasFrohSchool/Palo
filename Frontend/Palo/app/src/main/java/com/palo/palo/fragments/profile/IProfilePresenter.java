package com.palo.palo.fragments.profile;

public interface IProfilePresenter {
    public void loadProfile(int userId);
    public void loadPosts(int userId);
    public void likePalo(int position, int paloId, int userId, boolean toLike);
}
