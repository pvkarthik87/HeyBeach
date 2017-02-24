package com.karcompany.heybeach.service;

import android.support.v4.os.ResultReceiver;

import com.karcompany.heybeach.networking.ApiType;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public abstract class ApiRequest {

	ResultReceiver resultReceiver;
	ApiType apiType;

	public ApiRequest(ApiType apiType, ResultReceiver resultReceiver) {
		this.apiType = apiType;
		this.resultReceiver = resultReceiver;
	}

	public boolean requestEquals(ApiRequest request) {
		return apiType.equals(request.apiType);
	}
}
