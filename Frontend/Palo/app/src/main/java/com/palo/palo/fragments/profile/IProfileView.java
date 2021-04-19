package com.palo.palo.fragments.profile;

import com.palo.palo.model.Palo;

import java.util.List;

public interface IProfileView {
    public void loadPalos(List<Palo> palos);
    public void loadEmptyPosts();
    public void refreshFeed(int currentUserId);
    public void makeToast(String message);
    public void updatePalo(int paloIndex, Palo palo);
    public Palo getPalo(int paloIndex);
    public void setProfilePicture(String url);
    public void setFollowersCount(String num);
    public void setFollowingCount(String num);
    public void setPaloCount(String num);


}
