package com.palo.palo.activities.extendedPost;

import org.json.JSONException;
import org.json.JSONObject;

public interface IExtendedPostPresenter {
    public void postComment(JSONObject commentJson) throws JSONException;
    public void loadComments();
    public void likePalo(int paloId, int userId, boolean toLike);

    public void loadPlaybackLink(String playbackLink);
}
