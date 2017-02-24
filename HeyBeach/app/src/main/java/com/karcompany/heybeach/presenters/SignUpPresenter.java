package com.karcompany.heybeach.presenters;

import com.karcompany.heybeach.mvputils.Presenter;
import com.karcompany.heybeach.views.SignUpView;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * Presenter interface which helps with signup feature.
 *
 */

public interface SignUpPresenter extends Presenter {

	void setView(SignUpView signUpView);

}
