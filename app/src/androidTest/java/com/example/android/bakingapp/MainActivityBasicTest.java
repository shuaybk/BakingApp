package com.example.android.bakingapp;

import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;


import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    private static final String POSITION1_TITLE = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem_OpensRecipeDetailActivity() {
        onData(anything()).inAdapterView(withId(R.id.recyclerViewRecipes_id)).atPosition(1).perform(click());

        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar")))).check(matches(withText(POSITION1_TITLE)));
    }
}
