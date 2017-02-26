package com.karcompany.heybeach.utils;

import android.text.TextUtils;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class CommonUtils {

	public static boolean isValidEmail(String target) {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

}
