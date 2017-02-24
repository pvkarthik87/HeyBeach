package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.mvputils.Presenter;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.views.UserProfileView;

/**
 * Created by pvkarthik on 2017-02-24.
 *
 * Presenter interface which helps to load user profile.
 *
 */

public interface UserProfilePresenter extends Presenter {

	void setView(UserProfileView userProfileView);

	void loadUserProfile(Context ctx, ApiResultReceiver apiResultReceiver);

}
