package com.karcompany.heybeach;

/**
 * Created by pvkarthik on 2017-02-26.
 *
 * Full scenario instrumentation tests.
 */

import android.content.Context;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.karcompany.heybeach.views.activities.BeachListActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class UserLoginTest {

	@Rule
	public ActivityTestRule<BeachListActivity> activityRule = new ActivityTestRule<>(BeachListActivity.class);

	@Test
	public void testLogin17() {
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
		onView(withText(R.string.login)).perform(click());
		onView(allOf(withId(R.id.userEmail), isDisplayed())).perform(typeText("pvkarthik81@gmail.com"));
		onView(allOf(withId(R.id.userPwd), isDisplayed())).perform(typeText("123456")).perform(closeSoftKeyboard());;
		onView(withId(R.id.loginBtn)).check(matches(isEnabled()));
		onView(withId(R.id.loginBtn)).perform(click());
		SystemClock.sleep(5000);
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
		onView(withText(R.string.logout)).check(matches(isDisplayed()));
		onView(withText(R.string.my_profile)).perform(click());
		onView(withId(R.id.profileCardView)).check(matches(isDisplayed()));
	}

	@Test
	public void testLogin18() {
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
		onView(withText(R.string.logout)).perform(click());
		onView(withText(R.string.logout_confirmation_msg)).check(matches(isDisplayed()));
		onView(withText(R.string.yes)).perform(click());
		SystemClock.sleep(5000);
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
		onView(withText(R.string.login)).check(matches(isDisplayed()));
	}



	private static Context getContext() {
		return InstrumentationRegistry.getTargetContext();
	}

	public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
		return new TypeSafeMatcher<View>() {

			@Override
			public boolean matchesSafely(View view) {
				if (!(view instanceof TextInputLayout)) {
					return false;
				}

				CharSequence error = ((TextInputLayout) view).getError();

				if (error == null) {
					return false;
				}

				String hint = error.toString();

				return expectedErrorText.equals(hint);
			}

			@Override
			public void describeTo(Description description) {
			}
		};
	}

}
