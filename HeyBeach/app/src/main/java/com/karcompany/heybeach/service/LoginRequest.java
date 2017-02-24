package com.karcompany.heybeach.service;

import android.support.v4.os.ResultReceiver;

import com.karcompany.heybeach.networking.ApiType;

/**
 * Created by pvkarthik on 2017-02-24.
 */

public class LoginRequest extends ApiRequest {

	private String email;
	private String pwd;

	public LoginRequest(String email, String pwd, ResultReceiver resultReceiver) {
		super(ApiType.LOGIN, resultReceiver);
		this.email = email;
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	@Override
	public boolean requestEquals(ApiRequest request) {
		if(!(request instanceof LoginRequest)) return false;
		LoginRequest req = (LoginRequest) request;
		return super.requestEquals(request) && email.equals(req.email) && pwd.equals(req.pwd);
	}

}
