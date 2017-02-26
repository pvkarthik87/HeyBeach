package com.karcompany.heybeach;

/**
 * Created by pvkarthik on 2017-02-26.
 *
 * Full scenario instrumentation tests.
 */

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karcompany.heybeach.views.activities.BeachListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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

}
