package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.views.MainLoginView;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * Presenter implementation which handles sending tabs to view. Based on tab types view will show
 * appropriate fragments.
 */

public class MainLoginPresenterImpl implements MainLoginPresenter {

	private static MainLoginPresenterImpl mInstance;

	private MainLoginView mView;

	private MainLoginPresenterImpl() {

	}

	public static MainLoginPresenterImpl getInstance() {
		if (mInstance == null) {
			synchronized (MainLoginPresenterImpl.class) {
				if (mInstance == null) {
					mInstance = new MainLoginPresenterImpl();
				}
			}
		}
		return mInstance;
	}

	@Override
	public void setView(MainLoginView recipeListView) {
		mView = recipeListView;
	}

	@Override
	public void onStart() {
		loadTabs();
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

	private void loadTabs() {
		Constants.Tab[] tabs = new Constants.Tab[] {
				new Constants.LoginTab(),
				new Constants.SignUpTab()
		};
		if(mView != null) {
			mView.updateTabs(tabs);
		}
	}

	@Override
	public void register(Context ctx, String email, String pwd, ApiResultReceiver apiResultReceiver) {
		if(mView != null) {
			mView.onRegisterProgress();
		}
		ServiceHelper.register(ctx, email, pwd, apiResultReceiver);
	}

	@Override
	public void login(Context ctx, String email, String pwd, ApiResultReceiver apiResultReceiver) {
		if(mView != null) {
			mView.onLoginProgress();
		}
		ServiceHelper.login(ctx, email, pwd, apiResultReceiver);
	}

	@Override
	public void logout(Context ctx, ApiResultReceiver apiResultReceiver) {
		ServiceHelper.logout(ctx, apiResultReceiver);
	}

	@Override
	public void onLoginSuccess() {
		if(mView != null) {
			mView.onLoginSuccess();
		}
		HomePresenter homePresenter = HomePresenterImpl.getInstance();
		homePresenter.onLoginSuccess();
	}

	@Override
	public void onRegisterSuccess() {
		if(mView != null) {
			mView.onRegisterSuccess();
		}
		HomePresenter homePresenter = HomePresenterImpl.getInstance();
		homePresenter.onRegisterSuccess();
	}
}
