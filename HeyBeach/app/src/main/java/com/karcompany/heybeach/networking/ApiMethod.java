package com.karcompany.heybeach.networking;

/**
 * Created by pvkarthik on 2017-02-24.
 */

public enum ApiMethod {
	GET("GET"),
	POST("POST"),
	DELETE("DELETE");

	private final String methodName;

	private ApiMethod(String value) {
		methodName = value;
	}

	public String getMethodName() {
		return methodName;
	}
}
