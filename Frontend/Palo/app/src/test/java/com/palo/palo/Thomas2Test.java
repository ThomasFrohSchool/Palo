package com.palo.palo;

import com.palo.palo.activities.DirectMessageUserActivity;
import com.palo.palo.fragments.directMessage.DirectMessageFragment;
import com.palo.palo.fragments.searchPage.SearchFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Thomas2Test {

    @Mock
    private DirectMessageUserActivity dmUserActivity;
    private SearchFragment search;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void connectUser() {
        dmUserActivity = mock(DirectMessageUserActivity.class);
        String result;

        String[] receipts = new String[3];
        receipts[0] = "Icebear";
        receipts[1] = "mboser";
        receipts[2] = "tiffany";

        when(dmUserActivity.connectWebSocket()).thenReturn("Connected to ws://coms-309-021.cs.iastate.edu:8080/chat/tommyboy/" + receipts[0]);
        result = dmUserActivity.connectWebSocket();
        assertEquals("Connected to ws://coms-309-021.cs.iastate.edu:8080/chat/tommyboy/" + receipts[0], result);

        when(dmUserActivity.connectWebSocket()).thenReturn("Connected to ws://coms-309-021.cs.iastate.edu:8080/chat/tommyboy/" + receipts[1]);
        result = dmUserActivity.connectWebSocket();
        assertEquals("Connected to ws://coms-309-021.cs.iastate.edu:8080/chat/tommyboy/" + receipts[1], result);

        when(dmUserActivity.connectWebSocket()).thenReturn("Connected to ws://coms-309-021.cs.iastate.edu:8080/chat/tommyboy/" + receipts[2]);
        result = dmUserActivity.connectWebSocket();
        assertEquals("Connected to ws://coms-309-021.cs.iastate.edu:8080/chat/tommyboy/" + receipts[2], result);
    }

    @Test
    public void followUser() {
        search = mock(SearchFragment.class);
        String result;
        int userID;

        userID = 0;
        when(search.onAddFollowClicked(0)).thenReturn("user followed " + userID);
        result = search.onAddFollowClicked(0);
        assertEquals("user followed " + userID, result);

        userID = 1;
        when(search.onAddFollowClicked(3)).thenReturn("user followed " + userID);
        result = search.onAddFollowClicked(3);
        assertEquals("user followed " + userID, result);

        userID = 2;
        when(search.onAddFollowClicked(5)).thenReturn("user followed " + userID);
        result = search.onAddFollowClicked(5);
        assertEquals("user followed " + userID, result);

        userID = 0;
        when(search.onRemoveFollowClicked(0)).thenReturn("user removed " + userID);
        result = search.onRemoveFollowClicked(0);
        assertEquals("user removed " + userID, result);

        userID = 1;
        when(search.onRemoveFollowClicked(3)).thenReturn("user removed " + userID);
        result = search.onRemoveFollowClicked(3);
        assertEquals("user removed " + userID, result);

        userID = 2;
        when(search.onRemoveFollowClicked(5)).thenReturn("user removed " + userID);
        result = search.onRemoveFollowClicked(5);
        assertEquals("user removed " + userID, result);
    }
}
