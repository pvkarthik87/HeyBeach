package com.karcompany.heybeach.networking;

import android.os.AsyncTask;

import com.karcompany.heybeach.models.RegisterApiResponse;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.RegisterRequest;

import java.lang.ref.WeakReference;

import static com.karcompany.heybeach.service.ApiResponse.ERROR;
import static com.karcompany.heybeach.service.ApiResponse.SUCCESS;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class RegisterTask extends
		AsyncTask<RegisterRequest, Void, RegisterApiResponse> {

	private RegisterRequest mRequest;
	private WeakReference<TaskListener> listenerRef;

	public RegisterTask(RegisterRequest request, TaskListener taskListener) {
		mRequest = request;
		if (taskListener != null) {
			listenerRef = new WeakReference<>(taskListener);
		}
	}

	@Override
	protected RegisterApiResponse doInBackground(
			RegisterRequest... params) {
		if (params == null || params.length == 0) return null;
		RegisterRequest request = params[0];
		return ApiRepo.doRegister(request.getEmail(), request.getPwd());
	}

	@Override
	protected void onPostExecute(RegisterApiResponse registerApiResponse) {
		if (getListener() != null) {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiType(ApiType.REGISTER);
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
