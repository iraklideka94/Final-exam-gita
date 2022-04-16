package com.androidmanifester.developers.examdemo;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.androidmanifester.developers.examdemo.MainActivity;
import com.androidmanifester.developers.examdemo.SmileyListActivity;
import com.google.developers.examdemo.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ValidateStartActivityTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void initIntent(){
        Intents.init();
    }

    @Test
    public void navigateToList() {

        onView(ViewMatchers.withId(R.id.action_list))
                .perform(click());

        intended(hasComponent(SmileyListActivity.class.getName()));

    }

}