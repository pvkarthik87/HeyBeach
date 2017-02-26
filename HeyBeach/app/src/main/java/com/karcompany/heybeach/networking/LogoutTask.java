package com.karcompany.heybeach.networking;

import android.os.AsyncTask;

import com.karcompany.heybeach.models.DeleteApiResponse;
import com.karcompany.heybeach.models.RegisterApiResponse;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.LoginRequest;
import com.karcompany.heybeach.service.LogoutRequest;

import java.lang.ref.WeakReference;

import static com.karcompany.heybeach.service.ApiResponse.ERROR;
import static com.karcompany.heybeach.service.ApiResponse.SUCCESS;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class LogoutTask extends
		AsyncTask<LogoutRequest, Void, DeleteApiResponse> {

	private LogoutRequest mRequest;
	private WeakReference<TaskListener> listenerRef;

	public LogoutTask(LogoutRequest request, TaskListener taskListener) {
		mRequest = request;
		if (taskListener != null) {
			listenerRef = new WeakReference<>(taskListener);
		}
	}

	@Override
	protected DeleteApiResponse doInBackground(
			LogoutRequest... params) {
		if (params == null || params.length == 0) return null;
		LogoutRequest request = params[0];
		return ApiRepo.doLogout(request.getAccessToken());
	}

	@Override
	protected void onPostExecute(DeleteApiResponse deleteApiResponse) {
		if (getListener() != null) {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiType(ApiType.LOGOUT);
			if(deleteApiResponse != null) {
				apiResponse.setResponse(deleteApiResponse);
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
