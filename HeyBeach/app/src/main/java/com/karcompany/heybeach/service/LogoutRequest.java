package com.karcompany.heybeach.service;

import android.support.v4.os.ResultReceiver;

import com.karcompany.heybeach.networking.ApiType;

/**
 * Created by pvkarthik on 2017-02-24.
 */

public class LogoutRequest extends ApiRequest {

	private String mAccessToken;

	public LogoutRequest(String accessToken, ResultReceiver resultReceiver) {
		super(ApiType.LOGOUT, resultReceiver);
		mAccessToken = accessToken;
	}

	public String getAccessToken() {
		return mAccessToken;
	}

	@Override
	public boolean requestEquals(ApiRequest request) {
		if(!(request instanceof LogoutRequest)) return false;
		return super.requestEquals(request);
	}

}
