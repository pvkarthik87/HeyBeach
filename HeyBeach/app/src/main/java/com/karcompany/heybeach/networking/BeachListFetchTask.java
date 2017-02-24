package com.karcompany.heybeach.networking;

import android.os.AsyncTask;

import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.BeachFetchRequest;

import java.lang.ref.WeakReference;

import static com.karcompany.heybeach.service.ApiResponse.ERROR;
import static com.karcompany.heybeach.service.ApiResponse.SUCCESS;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachListFetchTask extends
		AsyncTask<BeachFetchRequest, Void, BeachListApiResponse> {

	private BeachFetchRequest mRequest;
	private WeakReference<TaskListener> listenerRef;

	public BeachListFetchTask(BeachFetchRequest request, TaskListener taskListener) {
		mRequest = request;
		if (taskListener != null) {
			listenerRef = new WeakReference<>(taskListener);
		}
	}

	@Override
	protected BeachListApiResponse doInBackground(
			BeachFetchRequest... params) {
		if (params == null || params.length == 0) return null;
		return ApiRepo.fetchBeaches(params[0].getPageNo());
	}

	@Override
	protected void onPostExecute(BeachListApiResponse beachListApiResponse) {
		if (getListener() != null) {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiType(ApiType.FETCH_BEACHES);
			if(beachListApiResponse != null) {
				apiResponse.setResponse(beachListApiResponse);
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
