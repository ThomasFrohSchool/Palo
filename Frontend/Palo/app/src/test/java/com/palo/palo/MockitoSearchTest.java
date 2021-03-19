package com.palo.palo;

import com.spotify.sdk.android.authentication.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSearchTest {

    @Mock
    //private SearchFragment f;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getResponseTest_returnsTrue() {

    }

    @Test
    public void getResponseTest_returnsFalse() {

    }
}
