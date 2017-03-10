package com.karcompany.heybeach.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.storage.KeyValueUtils;
import com.karcompany.heybeach.views.MainLoginView;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * Presenter implementation which handles sending tabs to view. Based on tab types view will show
 * appropriate fragments.
 */

public class MainLoginPresenterImpl implements MainLoginPresenter {

	private static final String TAG = DefaultLogger.makeLogTag(MainLoginPresenterImpl.class);

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
		if(ctx == null || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd) || apiResultReceiver == null) return;
		if(KeyValueUtils.isLoggedIn(ctx)) return;
		if(mView != null) {
			mView.onRegisterProgress();
		}
		ServiceHelper.register(ctx, email, pwd, apiResultReceiver);
	}

	@Override
	public void login(Context ctx, String email, String pwd, ApiResultReceiver apiResultReceiver) {
		if(ctx == null || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd) || apiResultReceiver == null) return;
		if(KeyValueUtils.isLoggedIn(ctx)) return;
		if(mView != null) {
			mView.onLoginProgress();
		}
		ServiceHelper.login(ctx, email, pwd, apiResultReceiver);
	}

	@Override
	public void onLoginSuccess() {
		DefaultLogger.d(TAG, "Login Success");
		if(mView != null) {
			mView.onLoginSuccess();
		}
		HomePresenter homePresenter = HomePresenterImpl.getInstance();
		homePresenter.onLoginSuccess();
	}

	@Override
	public void onLoginFailed() {
		DefaultLogger.d(TAG, "Login Failed");
		if(mView != null) {
			mView.onLoginFailed();
		}
	}

	@Override
	public void onRegisterSuccess() {
		if(mView != null) {
			mView.onRegisterSuccess();
		}
		HomePresenter homePresenter = HomePresenterImpl.getInstance();
		homePresenter.onRegisterSuccess();
	}

	@Override
	public void onRegisterFailed() {
		if(mView != null) {
			mView.onRegisterFailed();
		}
	}
}
