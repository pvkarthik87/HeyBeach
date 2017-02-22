package com.karcompany.heybeach.service;

import android.support.v4.os.ResultReceiver;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachFetchRequest {

	private int pageNo;
	ResultReceiver mResultReceiver;

	public BeachFetchRequest(int pageNo, ResultReceiver resultReceiver) {
		this.pageNo = pageNo;
		mResultReceiver = resultReceiver;
	}

	public int getPageNo() {
		return pageNo;
	}

	public boolean requestEquals(BeachFetchRequest req) {
		return pageNo == req.pageNo;
	}

}
