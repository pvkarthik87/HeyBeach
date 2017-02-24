package com.karcompany.heybeach.service;

import android.support.v4.os.ResultReceiver;

import com.karcompany.heybeach.networking.ApiType;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachFetchRequest extends ApiRequest {

	private int pageNo;

	public BeachFetchRequest(int pageNo, ResultReceiver resultReceiver) {
		super(ApiType.FETCH_BEACHES, resultReceiver);
		this.pageNo = pageNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	@Override
	public boolean requestEquals(ApiRequest request) {
		if(!(request instanceof BeachFetchRequest)) return false;
		BeachFetchRequest req = (BeachFetchRequest) request;
		return super.requestEquals(request) && pageNo == req.pageNo;
	}

}
