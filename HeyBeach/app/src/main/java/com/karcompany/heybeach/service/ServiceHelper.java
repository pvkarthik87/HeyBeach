package com.karcompany.heybeach.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by sammyer on 2017-02-22.
 */

public class ServiceHelper {

	public static final String EXTRA_RECEIVER = "EXTRA_RECEIVER";
	public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";

	public static final String ACTION_FETCH_BEACHS = "com.karcompany.heybeach.ACTION_FETCH_BEACHS";
	public static final String EXTRA_PAGE_NO = "EXTRA_PAGE_NO";

	public static final String ACTION_REGISTER = "com.karcompany.heybeach.ACTION_REGISTER";
	public static final String ACTION_LOGIN = "com.karcompany.heybeach.ACTION_LOGIN";
	public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
	public static final String EXTRA_PWD = "EXTRA_PWD";

	public static final String ACTION_LOGOUT = "com.karcompany.heybeach.ACTION_LOGOUT";

	public static void fetchBeaches(Context context, int pageNo, ApiResultReceiver resultReceiver) {
		if(context == null || pageNo < 0) return;
		Intent intent = new Intent(context, ApiService.class);
		intent.setAction(ACTION_FETCH_BEACHS);
		intent.putExtra(EXTRA_PAGE_NO, pageNo);
		intent.putExtra(EXTRA_RECEIVER, resultReceiver);
		context.startService(intent);
	}

	public static void register(Context context, String email, String pwd, ApiResultReceiver resultReceiver) {
		if(context == null || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) return;
		Intent intent = new Intent(context, ApiService.class);
		intent.setAction(ACTION_REGISTER);
		intent.putExtra(EXTRA_EMAIL, email);
		intent.putExtra(EXTRA_PWD, pwd);
		intent.putExtra(EXTRA_RECEIVER, resultReceiver);
		context.startService(intent);
	}

	public static void login(Context context, String email, String pwd, ApiResultReceiver resultReceiver) {
		if(context == null || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) return;
		Intent intent = new Intent(context, ApiService.class);
		intent.setAction(ACTION_LOGIN);
		intent.putExtra(EXTRA_EMAIL, email);
		intent.putExtra(EXTRA_PWD, pwd);
		intent.putExtra(EXTRA_RECEIVER, resultReceiver);
		context.startService(intent);
	}

	public static void logout(Context context, ApiResultReceiver resultReceiver) {
		if(context == null) return;
		Intent intent = new Intent(context, ApiService.class);
		intent.setAction(ACTION_LOGOUT);
		intent.putExtra(EXTRA_RECEIVER, resultReceiver);
		context.startService(intent);
	}

}
