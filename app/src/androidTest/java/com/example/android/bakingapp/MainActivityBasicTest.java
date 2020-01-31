package com.example.android.bakingapp;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;


import androidx.test.rule.ActivityTestRule;

import com.example.android.bakingapp.Adapters.RecipeListAdapter;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    private static final String POSITION1_TITLE = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void countRecipeItems() {
        IdlingRegistry.getInstance().register(mActivityTestRule.getActivity().idlingResource);

        //Assertion that there should be 4 recipes
        onView(withId(R.id.recyclerViewRecipes_id)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                assertThat(adapter.getItemCount(), is(4));
            }
        });
    }

    @Test
    public void clickRecipeItem_OpensRecipeDetailActivity_AndCountsSteps() {
        IdlingRegistry.getInstance().register(mActivityTestRule.getActivity().idlingResource);

        onView(withId(R.id.recyclerViewRecipes_id)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Assertion that clicking on item at position 1 takes us to the Brownies recipe
        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar")))).check(matches(withText(POSITION1_TITLE)));

        //Assertion that there should be 11 list items in the Brownies recipe
        onView(withId(R.id.recyclerViewRecipeDetails_id)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                assertThat(adapter.getItemCount(), is(11));
            }
        });
    }

}
