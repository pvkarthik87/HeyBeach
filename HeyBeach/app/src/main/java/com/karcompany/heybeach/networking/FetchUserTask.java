package com.karcompany.heybeach.networking;

import android.os.AsyncTask;

import com.karcompany.heybeach.models.DeleteApiResponse;
import com.karcompany.heybeach.models.UserMetaData;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.FetchUserRequest;
import com.karcompany.heybeach.service.LogoutRequest;

import java.lang.ref.WeakReference;

import static com.karcompany.heybeach.service.ApiResponse.ERROR;
import static com.karcompany.heybeach.service.ApiResponse.SUCCESS;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class FetchUserTask extends
		AsyncTask<FetchUserRequest, Void, UserMetaData> {

	private FetchUserRequest mRequest;
	private WeakReference<TaskListener> listenerRef;

	public FetchUserTask(FetchUserRequest request, TaskListener taskListener) {
		mRequest = request;
		if (taskListener != null) {
			listenerRef = new WeakReference<>(taskListener);
		}
	}

	@Override
	protected UserMetaData doInBackground(
			FetchUserRequest... params) {
		if (params == null || params.length == 0) return null;
		FetchUserRequest request = params[0];
		return ApiRepo.fetchUser(request.getAccessToken());
	}

	@Override
	protected void onPostExecute(UserMetaData userMetaData) {
		if (getListener() != null) {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiType(ApiType.FETCH_PROFILE);
			if(userMetaData != null) {
				apiResponse.setResponse(userMetaData);
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
