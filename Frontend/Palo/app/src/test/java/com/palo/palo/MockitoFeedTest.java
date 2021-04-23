package com.palo.palo;

import com.palo.palo.fragments.feed.FeedFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoFeedTest {
    @Mock
    private FeedFragment fragment;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getCorrectEmptyFeedResponse() {
//        fragment = mock(FeedFragment.class);
//        int mockitoFeedTestUserId = 51;
//        when(fragment.extractSongs(mockitoFeedTestUserId)).thenReturn("[]");
//        assertEquals("[]",fragment.extractSongs(mockitoFeedTestUserId));
    }

    @Test
    public void getCorrectFeedWithPostsResponse(){
//        fragment = mock(FeedFragment.class);
//        int mockitoFeedTestUserId = 52;
//        when(fragment.extractSongs(mockitoFeedTestUserId)).thenReturn("[{\"id\":48,\"spot_id\":\"6Fqf0OTfB9VPfYk2HfBhPn\",\"description\":\"Wow this makes me want to drive!\",\"type\":2,\"likes\":0,\"createDate\":\"2021\\/04\\/04 23:34:17\",\"comments\":[],\"user_id\":51},{\"id\":49,\"spot_id\":\"2PFIZFcGry0po3ZfRZkzKc\",\"description\":\"wow!\",\"type\":2,\"likes\":0,\"createDate\":\"2021\\/04\\/04 23:35:22\",\"comments\":[],\"user_id\":51},{\"id\":50,\"spot_id\":\"3PhoLpVuITZKcymswpck5b\",\"description\":\"legendary...\",\"type\":1,\"likes\":0,\"createDate\":\"2021\\/04\\/04 23:35:57\",\"comments\":[],\"user_id\":51}]");
//        assertEquals("[{\"id\":48,\"spot_id\":\"6Fqf0OTfB9VPfYk2HfBhPn\",\"description\":\"Wow this makes me want to drive!\",\"type\":2,\"likes\":0,\"createDate\":\"2021\\/04\\/04 23:34:17\",\"comments\":[],\"user_id\":51},{\"id\":49,\"spot_id\":\"2PFIZFcGry0po3ZfRZkzKc\",\"description\":\"wow!\",\"type\":2,\"likes\":0,\"createDate\":\"2021\\/04\\/04 23:35:22\",\"comments\":[],\"user_id\":51},{\"id\":50,\"spot_id\":\"3PhoLpVuITZKcymswpck5b\",\"description\":\"legendary...\",\"type\":1,\"likes\":0,\"createDate\":\"2021\\/04\\/04 23:35:57\",\"comments\":[],\"user_id\":51}]", fragment.extractSongs(mockitoFeedTestUserId));
    }
}
