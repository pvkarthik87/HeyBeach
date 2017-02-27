package com.karcompany.heybeach.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.karcompany.heybeach.config.Constants;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class KeyValueUtils {

	public static boolean isLoggedIn(Context ctx) {
		if(ctx == null) return false;
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		String token =  sharedPreferences.getString(Constants.KEY_ACCESS_TOKEN, "");
		return !TextUtils.isEmpty(token);
	}

	public static void updateKey(Context context, String key, String value) {
		if(context == null || TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) return;
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		sharedPreferences.edit().putString(key, value).apply();
	}

	public static String getKey(Context ctx, String key) {
		if(ctx == null || TextUtils.isEmpty(key)) return "";
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext());
		return sharedPreferences.getString(Constants.KEY_ACCESS_TOKEN, "");
	}

	public static void removeKey(Context context, String key) {
		if(context == null || TextUtils.isEmpty(key)) return;
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		sharedPreferences.edit().remove(key).apply();
	}

}
