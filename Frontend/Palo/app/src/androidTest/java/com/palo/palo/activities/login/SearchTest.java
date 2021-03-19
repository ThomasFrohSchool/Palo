package com.palo.palo.activities.login;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.palo.palo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void searchTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.loginEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("TommySmokes"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.loginPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Bdb!!"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav_bar),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.spotifySearch),
                        childAtPosition(
                                allOf(withId(R.id.searchText),
                                        childAtPosition(
                                                withId(R.id.main_frame),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("dababy"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button), withText("Search!"),
                        childAtPosition(
                                allOf(withId(R.id.searchText),
                                        childAtPosition(
                                                withId(R.id.main_frame),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.songResult), withText("Response: {\"albums\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"BLAME IT ON BABY\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/623PL2MBg50Br5dLXC9E9e\",\"id\":\"623PL2MBg50Br5dLXC9E9e\"},{\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/04m06KhJUuwe1Q487puIud\",\"id\":\"04m06KhJUuwe1Q487puIud\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f2b94b2fda4f08836d6371ba\",\"name\":\"KIRK\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/1NsTSXjVNE7XmZ8PmyW0wl\",\"id\":\"1NsTSXjVNE7XmZ8PmyW0wl\"}], \"artists\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/9fabb3d7dd075894af0b3805c129e433b9104b26\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/4r63FhuTkUYltbVAg5TQnk\",\"id\":\"4r63FhuTkUYltbVAg5TQnk\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f51d22db94b7ccbed26870c0\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/7MCrEuHBgUcjP8eMxM2IFC\",\"id\":\"7MCrEuHBgUcjP8eMxM2IFC\"},{\"artist\":\"Dababy Type Beat\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02dcc76e0695b15ade6a26c26d\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/23B5uIwoN5UFrHHMUlyVfT\",\"id\":\"23B5uIwoN5UFrHHMUlyVfT\"}], \"tracks\":[{\"playbackLink\":null,\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"ROCKSTAR (feat. Roddy Ricch)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/7ytR5pFWmSjzHJIeQkgog4\",\"id\":\"7ytR5pFWmSjzHJIeQkgog4\"},{\"playbackLink\":\"https:\\/\\/p.scdn.co\\/mp3-preview\\/cc617f669fd1e3ee33a4ac0c66346fefd15286e7?cid=b82ef89345fa42a7893a0f199d64439f\",\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/463CkQjx2Zk1yXoBuierM9\",\"id\":\"463CkQjx2Zk1yXoBuierM9\"},{\"playbackLink\":null,\"artist\":\"Pop Smoke\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0277ada0863603903f57b34369\",\"name\":\"For The Night (feat. Lil Baby & DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/0PvFJmanyNQMseIFrU708S\",\"id\":\"0PvFJmanyNQMseIFrU708S\"}]}"),
                        withParent(allOf(withId(R.id.searchText),
                                withParent(withId(R.id.main_frame)))),
                        isDisplayed()));
        textView.check(matches(withText("Response: {\"albums\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"BLAME IT ON BABY\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/623PL2MBg50Br5dLXC9E9e\",\"id\":\"623PL2MBg50Br5dLXC9E9e\"},{\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/04m06KhJUuwe1Q487puIud\",\"id\":\"04m06KhJUuwe1Q487puIud\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f2b94b2fda4f08836d6371ba\",\"name\":\"KIRK\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/1NsTSXjVNE7XmZ8PmyW0wl\",\"id\":\"1NsTSXjVNE7XmZ8PmyW0wl\"}], \"artists\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/9fabb3d7dd075894af0b3805c129e433b9104b26\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/4r63FhuTkUYltbVAg5TQnk\",\"id\":\"4r63FhuTkUYltbVAg5TQnk\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f51d22db94b7ccbed26870c0\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/7MCrEuHBgUcjP8eMxM2IFC\",\"id\":\"7MCrEuHBgUcjP8eMxM2IFC\"},{\"artist\":\"Dababy Type Beat\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02dcc76e0695b15ade6a26c26d\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/23B5uIwoN5UFrHHMUlyVfT\",\"id\":\"23B5uIwoN5UFrHHMUlyVfT\"}], \"tracks\":[{\"playbackLink\":null,\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"ROCKSTAR (feat. Roddy Ricch)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/7ytR5pFWmSjzHJIeQkgog4\",\"id\":\"7ytR5pFWmSjzHJIeQkgog4\"},{\"playbackLink\":\"https:\\/\\/p.scdn.co\\/mp3-preview\\/cc617f669fd1e3ee33a4ac0c66346fefd15286e7?cid=b82ef89345fa42a7893a0f199d64439f\",\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/463CkQjx2Zk1yXoBuierM9\",\"id\":\"463CkQjx2Zk1yXoBuierM9\"},{\"playbackLink\":null,\"artist\":\"Pop Smoke\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0277ada0863603903f57b34369\",\"name\":\"For The Night (feat. Lil Baby & DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/0PvFJmanyNQMseIFrU708S\",\"id\":\"0PvFJmanyNQMseIFrU708S\"}]}")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
