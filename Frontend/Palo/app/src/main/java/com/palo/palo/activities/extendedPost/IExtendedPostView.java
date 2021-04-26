package com.palo.palo.activities.extendedPost;

import com.palo.palo.model.Comment;
import com.palo.palo.model.Palo;

import java.util.List;

public interface IExtendedPostView {
    public void makeToast(String message);
    public void logd(String response);
    public void setPaloView(Palo palo);
    public void loadComments(List<Comment> comments);
    public void postComment();
    public void updateLikeToPalo(boolean isLiked);
    public void clearCommentText();
    public void updateUser(int commentIndex, String username, String imageLink);
    public void dismissKeyboard();
    public void showPlaybackLink(String playbackLink);
}
