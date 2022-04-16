package com.androidmanifester.developers.examdemo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.PreferenceMatchers.withKey;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.androidmanifester.developers.examdemo.MainActivity;
import com.google.developers.examdemo.R;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class ValidateSettingTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testNumberOfAnswers() {

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        openActionBarOverflowOrOptionsMenu(context);

        onView(ViewMatchers.withText(R.string.action_settings))
                .perform(click());

        onView(withId(androidx.preference.R.id.recycler_view)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(R.string.pref_answers_title)), click()));

        onView(withText("Show two answers")).perform(click());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(context.getString(R.string.pref_key_answers),"");

        Assert.assertEquals("2",value);


        onView(withId(androidx.preference.R.id.recycler_view)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(R.string.pref_answers_title)), click()));

        onView(withText("Show three answers")).perform(click());

        value = preferences.getString(context.getString(R.string.pref_key_answers),"");

        Assert.assertEquals("3",value);


    }

}