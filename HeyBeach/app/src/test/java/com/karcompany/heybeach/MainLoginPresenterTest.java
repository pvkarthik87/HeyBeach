package com.karcompany.heybeach;

/**
 * Created by pvkarthik on 2017-02-27.
 *
 * Presenter unit test cases.
 */

import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.presenters.MainLoginPresenter;
import com.karcompany.heybeach.presenters.MainLoginPresenterImpl;
import com.karcompany.heybeach.views.MainLoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class MainLoginPresenterTest {

	@Mock
	private MainLoginView view;

	private MainLoginPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = MainLoginPresenterImpl.getInstance();
	}

	/**
	 Verify if view.updateTabs was called once.
	 */
	@Test
	public void testNumberOfTabsWhenViewSet() {
		presenter.setView(view);
		presenter.onStart();
		// verify can be called only on mock objects
		verify(view, times(1)).updateTabs(any(Constants.Tab[].class));
	}

	/**
	 Verify if view.updateTabs was not called.
	 */
	@Test
	public void testNumberOfTabsWhenViewNotSet() {
		presenter.onStart();
		// verify can be called only on mock objects
		verify(view, times(0)).updateTabs(any(Constants.Tab[].class));
	}

}
