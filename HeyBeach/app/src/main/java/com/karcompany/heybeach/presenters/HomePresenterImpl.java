package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.storage.KeyValueUtils;
import com.karcompany.heybeach.views.HomeView;

/**
 * Created by pvkarthik on 2017-02-24.
 *
 * Presenter implementation.
 */

public class HomePresenterImpl implements HomePresenter {

	private static final String TAG = DefaultLogger.makeLogTag(HomePresenterImpl.class);

	private static HomePresenterImpl mInstance;

	private HomeView mView;

	private HomePresenterImpl() {

	}

	public static HomePresenterImpl getInstance() {
		if (mInstance == null) {
			synchronized (HomePresenterImpl.class) {
				if (mInstance == null) {
					mInstance = new HomePresenterImpl();
				}
			}
		}
		return mInstance;
	}

	@Override
	public void setView(HomeView homeView) {
		mView = homeView;
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
	public void onLoginSuccess() {
		DefaultLogger.d(TAG, "Login Success");
		if(mView != null) {
			mView.onLoginSuccess();
		}
	}

	@Override
	public void onRegisterSuccess() {
		if(mView != null) {
			mView.onRegisterSuccess();
		}
	}

	@Override
	public void onLogoutSuccess() {
		if(mView != null) {
			mView.onLogoutSuccess();
		}
	}

	@Override
	public void logout(Context ctx, ApiResultReceiver apiResultReceiver) {
		if(ctx == null || apiResultReceiver == null) return;
		if(!KeyValueUtils.isLoggedIn(ctx)) return;
		if(mView != null) {
			mView.onLogoutProgress();
		}
		ServiceHelper.logout(ctx, apiResultReceiver);
	}
}
