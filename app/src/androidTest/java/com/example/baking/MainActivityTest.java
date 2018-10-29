package com.example.baking;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;


import com.example.baking.utils.RecyclerViewNotEmptyAssertion;
import com.example.baking.view.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.baking.utils.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by diegocotta on 29/10/2018.
 */
@RunWith(AndroidJUnit4.class)

public class MainActivityTest {

    private static final int ITEM_POSITION = 1;
    private static final String NAME_SELECTED = "Brownies";
    private static final String INGREDIENTS_TITLE = "Ingredients";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void setUp() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void tearDown() throws Exception {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void itemClickTest() {

        //https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso

        onView(withRecyclerView((R.id.rvReceipes))
                .atPositionOnView(ITEM_POSITION, R.id.tvRecipeTitle))
                .check(matches(withText(NAME_SELECTED)));

        // https://github.com/googlesamples/android-testing/blob/master/ui/espresso/RecyclerViewSample/app/src/androidTest/java/com/example/android/testing/espresso/RecyclerViewSample/RecyclerViewSampleTest.java
        onView(withId((R.id.rvReceipes)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));

        //https://stackoverflow.com/questions/36329978/how-to-check-toolbar-title-in-android-instrumental-test
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText(NAME_SELECTED)));

        onView(withText(INGREDIENTS_TITLE)).check(matches(isDisplayed()));

    }

    @Test
    public void recipeItemsTest() {
        onView(withId((R.id.rvReceipes)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
        //based on https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso
        onView(withId((R.id.rvIngredients)))
                .check(new RecyclerViewNotEmptyAssertion());

        onView(withId((R.id.rvSteps)))
                .check(new RecyclerViewNotEmptyAssertion());


    }
}
