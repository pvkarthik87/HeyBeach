package com.karcompany.heybeach.presenters;

import com.karcompany.heybeach.views.LoginView;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * Presenter implementation which handles core features (user login).
 */

public class LoginPresenterImpl implements LoginPresenter {

	private static LoginPresenterImpl mInstance;

	private LoginView mView;

	private LoginPresenterImpl() {

	}

	public static LoginPresenterImpl getInstance() {
		if (mInstance == null) {
			synchronized (LoginPresenterImpl.class) {
				if (mInstance == null) {
					mInstance = new LoginPresenterImpl();
				}
			}
		}
		return mInstance;
	}

	@Override
	public void setView(LoginView loginView) {
		mView = loginView;
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
}
