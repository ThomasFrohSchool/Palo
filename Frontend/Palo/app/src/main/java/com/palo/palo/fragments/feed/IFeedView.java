package com.palo.palo.fragments.feed;

import com.palo.palo.model.Palo;

import java.util.List;

public interface IFeedView {
    public void loadPalos(List<Palo> palos);
    public void loadEmptyFeed();
    public void refreshFeed(int currentUserId);
    public void makeToast(String message);
    public void updatePalo(int paloIndex, Palo palo);
    public Palo getPalo(int paloIndex);
}
