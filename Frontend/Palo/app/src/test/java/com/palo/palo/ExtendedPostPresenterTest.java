package com.palo.palo;

import android.content.Context;

import com.palo.palo.activities.extendedPost.ExtendedPostModel;
import com.palo.palo.activities.extendedPost.ExtendedPostPresenter;
import com.palo.palo.activities.extendedPost.IExtendedPostView;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
    public void postCommentTest(){
//        JSONObject newComment = new JSONObject();
//        try {
//            newComment.put("body", "I really liked this song so I commented.");
//            newComment.put("user_id", 17);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        ExtendedPostPresenter presenter = new ExtendedPostPresenter(view, context, 1);
//        presenter.postComment(newComment);
//
//        verify(model,times(1)).postComment(newComment, 1, presenter);
    }

}
