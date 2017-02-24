package com.karcompany.heybeach.service;

import android.support.v4.os.ResultReceiver;

import com.karcompany.heybeach.networking.ApiType;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class RegisterRequest extends ApiRequest {

	private String email;
	private String pwd;

	public RegisterRequest(String email, String pwd, ResultReceiver resultReceiver) {
		super(ApiType.REGISTER, resultReceiver);
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
		if(!(request instanceof RegisterRequest)) return false;
		RegisterRequest req = (RegisterRequest) request;
		return super.requestEquals(request) && email.equals(req.email) && pwd.equals(req.pwd);
	}

}
