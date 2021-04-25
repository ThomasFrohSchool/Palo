package com.palo.palo;

import com.palo.palo.activities.DirectMessageUserActivity;
import com.palo.palo.fragments.directMessage.DirectMessageFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class Thomas2Test {

    @Mock
    private DirectMessageUserActivity dmUserActivity;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void dmUser() {
        dmUserActivity = mock(DirectMessageUserActivity.class);


    }
}
