package com.karcompany.heybeach.presenters;

import com.karcompany.heybeach.mvputils.Presenter;
import com.karcompany.heybeach.views.LoginView;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * Presenter interface which helps with login feature.
 *
 */

public interface LoginPresenter extends Presenter {

	void setView(LoginView loginView);

}
