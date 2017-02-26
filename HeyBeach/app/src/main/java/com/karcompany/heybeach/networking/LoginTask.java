package com.karcompany.heybeach.networking;

import android.os.AsyncTask;

import com.karcompany.heybeach.models.RegisterApiResponse;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.LoginRequest;
import com.karcompany.heybeach.service.RegisterRequest;

import java.lang.ref.WeakReference;

import static com.karcompany.heybeach.service.ApiResponse.ERROR;
import static com.karcompany.heybeach.service.ApiResponse.SUCCESS;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class LoginTask extends
		AsyncTask<LoginRequest, Void, RegisterApiResponse> {

	private LoginRequest mRequest;
	private WeakReference<TaskListener> listenerRef;

	public LoginTask(LoginRequest request, TaskListener taskListener) {
		mRequest = request;
		if (taskListener != null) {
			listenerRef = new WeakReference<>(taskListener);
		}
	}

	@Override
	protected RegisterApiResponse doInBackground(
			LoginRequest... params) {
		if (params == null || params.length == 0) return null;
		LoginRequest request = params[0];
		return ApiRepo.doLogin(request.getEmail(), request.getPwd());
	}

	@Override
	protected void onPostExecute(RegisterApiResponse registerApiResponse) {
		if (getListener() != null) {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiType(ApiType.LOGIN);
			if(registerApiResponse != null) {
				apiResponse.setResponse(registerApiResponse);
				apiResponse.setResponseCode(SUCCESS);
			} else {
				apiResponse.setResponseCode(ERROR);
			}
			getListener().onTaskComplete(mRequest, apiResponse);
		}
	}

	private TaskListener getListener() {
		if (listenerRef == null) return null;
		else return listenerRef.get();
	}
}
