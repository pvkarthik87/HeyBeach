package com.karcompany.heybeach;

/**
 * Created by pvkarthik on 2017-02-27.
 *
 * Presenter unit test cases.
 */

import android.content.Context;

import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.presenters.HomePresenter;
import com.karcompany.heybeach.presenters.HomePresenterImpl;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.views.HomeView;

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
public class HomePresenterTest {

	@Mock
	private HomeView view;

	private HomePresenter presenter;

	@Mock
	private Context fakeContext;

	@Mock
	private ApiResultReceiver apiResultReceiver;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		presenter = HomePresenterImpl.getInstance();
	}

	@Test
	public void testLogoutProgressCallbackWithInvalidParams() {
		presenter.logout(null, apiResultReceiver);
		verify(view, times(0)).onLogoutProgress();
		presenter.logout(fakeContext, null);
		verify(view, times(0)).onLogoutProgress();
		presenter.logout(fakeContext, apiResultReceiver);
		verify(view, times(0)).onLogoutProgress();
	}

	@Test
	public void testLogoutProgressCallbackWithValidParams() {
		presenter.setView(view);
		presenter.logout(fakeContext, apiResultReceiver);
		verify(view, times(0)).onLogoutProgress();
	}

}
