package com.karcompany.heybeach.networking;

import android.os.AsyncTask;

import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.service.BeachFetchRequest;

import java.lang.ref.WeakReference;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachListFetchTask extends
		AsyncTask<BeachFetchRequest, Void, BeachListApiResponse> {

	public interface TaskListener {

		void onTaskComplete(BeachFetchRequest request, BeachListApiResponse beachListApiResponse);

	}

	private BeachFetchRequest mRequest;
	private WeakReference<TaskListener> listenerRef;

	public BeachListFetchTask(BeachFetchRequest request, TaskListener taskListener) {
		mRequest = request;
		if (taskListener != null) {
			listenerRef = new WeakReference<TaskListener>(taskListener);
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
		if(beachListApiResponse != null) {
			if (getListener() != null) {
				getListener().onTaskComplete(mRequest, beachListApiResponse);
			}
		}
	}

	private TaskListener getListener() {
		if (listenerRef == null) return null;
		else return listenerRef.get();
	}
}
