package com.palo.palo;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.palo.palo.activities.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginViewTest {
//    private static final int SIMULATED_DELAY_MS = 1000;
//
//    @Rule
//    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);
//
//    @Test
//    public void loginTest(){
//        Intents.init();
//
//        String username = "asdf"; //username and password are same...
//        onView(withId(R.id.loginEmail))
//                .perform(typeText(username), closeSoftKeyboard());
//        onView(withId(R.id.loginPassword))
//                .perform(typeText(username), closeSoftKeyboard());
//        onView(withId(R.id.loginButton)).perform(click());
//
//        // Put thread to sleep to allow volley to handle the request
//        try {
//            Thread.sleep(SIMULATED_DELAY_MS);
//        } catch (InterruptedException e) {
//        }
//
//        intended(hasComponent("com.palo.palo.activities.MainActivity"));
//        Intents.release();
//    }

}

