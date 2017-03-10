package com.karcompany.heybeach;

/**
 * Created by pvkarthik on 2017-02-26.
 *
 * Full scenario instrumentation tests.
 */

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karcompany.heybeach.views.activities.BeachListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BeachListActivityTest {

	@Rule
	public ActivityTestRule<BeachListActivity> activityRule = new ActivityTestRule<>(BeachListActivity.class);

	@Test
	public void testBeachLoading() {
		SystemClock.sleep(3000);
		onView(withId(R.id.beach_list)).perform(scrollToPosition(10));
		SystemClock.sleep(3000);
		onView(withId(R.id.beach_list)).perform(scrollToPosition(20));
	}

	private static final String VALID_USER_EMAIL = "pvkarthik85@gmail.com";
	private static final String VALID_USER_PWD = "123456";

	@Test
	public void testUserLoginLogoutInSameSession() {
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
		onView(withText(R.string.login)).perform(click());
		onView(allOf(withId(R.id.userEmail), isDisplayed())).perform(typeText(VALID_USER_EMAIL));
		onView(allOf(withId(R.id.userPwd), isDisplayed())).perform(typeText(VALID_USER_PWD)).perform(closeSoftKeyboard());;
		onView(withId(R.id.loginBtn)).check(matches(isEnabled()));
		onView(withId(R.id.loginBtn)).perform(click());
		SystemClock.sleep(5000);
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
		onView(withText(R.string.my_profile)).check(matches(isDisplayed()));
		onView(withText(R.string.logout)).check(matches(isDisplayed()));
		onView(withText(R.string.my_profile)).perform(click());
		onView(withId(R.id.profileCardView)).check(matches(isDisplayed()));
		onView(withContentDescription("Navigate up")).perform(click());
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
		onView(withText(R.string.logout)).check(matches(isDisplayed()));
		onView(withText(R.string.logout)).perform(click());
		onView(withText(R.string.logout_confirmation_msg)).check(matches(isDisplayed()));
		onView(withText(R.string.yes)).perform(click());
	}

}
