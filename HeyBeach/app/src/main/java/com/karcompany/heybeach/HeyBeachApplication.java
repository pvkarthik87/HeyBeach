package com.karcompany.heybeach;

/**
 * Created by pvkarthik on 2017-02-20.
 *
 * Android Application.
 */

import android.app.Application;

import com.karcompany.heybeach.security.SecUtil;

public class HeyBeachApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SecUtil.init(getApplicationContext());
	}
}

