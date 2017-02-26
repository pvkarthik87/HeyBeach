package com.karcompany.heybeach.presenters;

import com.karcompany.heybeach.views.SignUpView;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * Presenter implementation which handles core features (user signup).
 */

public class SignUpPresenterImpl implements SignUpPresenter {

	private static SignUpPresenterImpl mInstance;

	private SignUpView mView;

	private SignUpPresenterImpl() {

	}

	public static SignUpPresenterImpl getInstance() {
		if (mInstance == null) {
			synchronized (SignUpPresenterImpl.class) {
				if (mInstance == null) {
					mInstance = new SignUpPresenterImpl();
				}
			}
		}
		return mInstance;
	}

	@Override
	public void setView(SignUpView signUpView) {
		mView = signUpView;
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
