package com.karcompany.heybeach.service;

import android.content.Context;
import android.content.Intent;

/**
 * Created by sammyer on 2017-02-22.
 */

public class ServiceHelper {

	public static final String ACTION_FETCH_BEACHS = "com.karcompany.heybeach.ACTION_FETCH_BEACHS";
	public static final String EXTRA_PAGE_NO = "EXTRA_PAGE_NO";
	public static final String EXTRA_RECEIVER = "EXTRA_RECEIVER";
	public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";

	public static void fetchBeaches(Context context, int pageNo, BeachResultReceiver resultReceiver) {
		if(context == null || pageNo < 0) return;
		Intent intent = new Intent(context, BeachService.class);
		intent.setAction(ACTION_FETCH_BEACHS);
		intent.putExtra(EXTRA_PAGE_NO, pageNo);
		intent.putExtra(EXTRA_RECEIVER, resultReceiver);
		context.startService(intent);
	}

}
