package com.palo.palo.activities.extendedPost;

import org.json.JSONObject;

public interface IExtendedPostPresenter {
    public void postComment(JSONObject commentJson);
    public void loadComments();
}