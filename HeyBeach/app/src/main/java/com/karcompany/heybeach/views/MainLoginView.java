package com.karcompany.heybeach.views;

import com.karcompany.heybeach.config.Constants;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * View interface which presenter uses to notify events.
 */

public interface MainLoginView {

	void updateTabs(Constants.Tab[] tabs);

	void onRegisterProgress();

	void onRegisterSuccess();

	void onLoginProgress();

	void onLoginSuccess();

}
