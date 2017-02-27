package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.mvputils.Presenter;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.views.MainLoginView;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * Presenter interface which helps with login/signup feature.
 *
 */

public interface MainLoginPresenter extends Presenter {

	void setView(MainLoginView mainLoginView);

	void register(Context ctx, String email, String pwd, ApiResultReceiver apiResultReceiver);

	void login(Context ctx, String email, String pwd, ApiResultReceiver apiResultReceiver);

	void onLoginSuccess();

	void onRegisterSuccess();

}
