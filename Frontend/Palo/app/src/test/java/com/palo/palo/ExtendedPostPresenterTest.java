package com.palo.palo;

import android.content.Context;

import com.palo.palo.activities.extendedPost.ExtendedPostModel;
import com.palo.palo.activities.extendedPost.ExtendedPostPresenter;
import com.palo.palo.activities.extendedPost.IExtendedPostView;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExtendedPostPresenterTest {
    @Mock
    private Context context;
    @Mock
    private IExtendedPostView view;
    @Mock
    private ExtendedPostModel model;

    @Test
    public void postCommentTestCase1() throws JSONException {
        //test correct formatted json
        JSONObject comment1 = Mockito.mock(JSONObject.class);
        Mockito.when(comment1.getString("body")).thenReturn("cool this is what I commented.");
//        Mockito.when(comment1.getInt("user_id")).thenReturn(17);

        ExtendedPostPresenter presenter = new ExtendedPostPresenter(view,1, model);
        presenter.postComment(comment1);

        verify(model,times(1)).postComment(comment1, 1, presenter);
    }
    @Test
    public void postCommentTestCase2() throws JSONException {
        //Tests when body of json is empty string
        JSONObject comment1 = Mockito.mock(JSONObject.class);
        Mockito.when(comment1.getString("body")).thenReturn("");
//        Mockito.when(comment1.getInt("user_id")).thenReturn(17);

        ExtendedPostPresenter presenter = new ExtendedPostPresenter(view,1, model);
        presenter.postComment(comment1);

        verify(model,times(0)).postComment(comment1, 1, presenter);
        verify(view,times(1)).makeToast("Comment must have length > 1.");
    }

    @Test
    public void likePalo_case1_when_toLike_is_true() {
        //case when true
        int paloId = 3;
        int userId = 5;
        boolean toLike = true;
        ExtendedPostPresenter presenter = new ExtendedPostPresenter(view,paloId, model);
        presenter.likePalo(paloId, userId, toLike);

        verify(model, times(1)).addPaloLike(paloId, userId, presenter);
        verify(model, times(0)).removePaloLike(paloId, userId, presenter);
    }

    @Test
    public void likePalo_case2_when_toLike_is_false() {
        // case when false
        int paloId = 3;
        int userId = 5;
        boolean toLike = false;
        ExtendedPostPresenter presenter = new ExtendedPostPresenter(view,paloId, model);
        presenter.likePalo(paloId, userId, toLike);

        verify(model, times(0)).addPaloLike(paloId, userId, presenter);
        verify(model, times(1)).removePaloLike(paloId, userId, presenter);
    }

    @Test
    public void loadComments_case1(){
        int paloId = 5;
        ExtendedPostPresenter presenter = new ExtendedPostPresenter(view,paloId, model);
        presenter.loadComments();
        verify(model, times(1)).getComments(paloId,  presenter);
    }
}
