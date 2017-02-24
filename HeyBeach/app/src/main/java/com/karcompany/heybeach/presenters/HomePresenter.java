package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.mvputils.Presenter;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.views.HomeView;
import com.karcompany.heybeach.views.MainLoginView;

/**
 * Created by pvkarthik on 2017-02-24.
 *
 * Presenter interface which helps with login/signup feature.
 *
 */

public interface HomePresenter extends Presenter {

	void setView(HomeView homeView);

	void onLoginSuccess();

	void onRegisterSuccess();

	void onLogoutSuccess();

}
