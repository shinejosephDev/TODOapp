package com.shinejoseph.to_doapp;

import com.shinejoseph.to_doapp.view.LoginActivity;
import com.shinejoseph.to_doapp.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.et_name)).perform(typeText("Test User")).check(matches(withText("Test User")));
    }

    @Test
    public void validateLoginGuestButton() {
        onView(withId(R.id.et_name)).perform(typeText("Test User 2"));
        Espresso.closeSoftKeyboard();
        Intents.init();
        onView(withId(R.id.btn_guest)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }
}
