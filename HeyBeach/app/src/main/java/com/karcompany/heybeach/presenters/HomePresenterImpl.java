package com.karcompany.heybeach.presenters;

import com.karcompany.heybeach.views.HomeView;

/**
 * Created by pvkarthik on 2017-02-24.
 *
 * Presenter implementation.
 */

public class HomePresenterImpl implements HomePresenter {

	private static HomePresenterImpl mInstance;

	private HomeView mView;

	private HomePresenterImpl() {

	}

	public static HomePresenterImpl getInstance() {
		if (mInstance == null) {
			synchronized (BeachListPresenterImpl.class) {
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
}
