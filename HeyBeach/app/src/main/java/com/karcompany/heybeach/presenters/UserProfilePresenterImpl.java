package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.views.UserProfileView;

/**
 * Created by pvkarthik on 2017-02-24.
 *
 * Presenter implementation which handles fetching user profile.
 */

public class UserProfilePresenterImpl implements UserProfilePresenter {

	private static UserProfilePresenterImpl mInstance;

	private UserProfileView mView;

	private UserProfilePresenterImpl() {

	}

	public static UserProfilePresenterImpl getInstance() {
		if (mInstance == null) {
			synchronized (UserProfilePresenterImpl.class) {
				if (mInstance == null) {
					mInstance = new UserProfilePresenterImpl();
				}
			}
		}
		return mInstance;
	}

	@Override
	public void setView(UserProfileView userProfileView) {
		mView = userProfileView;
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
	}

	@Override
	public void loadUserProfile(Context ctx, ApiResultReceiver apiResultReceiver) {
		ServiceHelper.fetchProfile(ctx, apiResultReceiver);
	}
}
