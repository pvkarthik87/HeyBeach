package com.karcompany.heybeach.views;

/**
 * Created by pvkarthik on 2017-02-24.
 *
 * View interface which presenter uses to notify events.
 */

public interface HomeView {

	void onLoginSuccess();

	void onRegisterSuccess();

	void onLogoutProgress();

	void onLogoutSuccess();

}
