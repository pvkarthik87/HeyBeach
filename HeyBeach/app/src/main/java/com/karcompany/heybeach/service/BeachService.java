package com.karcompany.heybeach.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.os.ResultReceiver;

import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.networking.BeachListFetchTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.karcompany.heybeach.service.ServiceHelper.EXTRA_PAGE_NO;
import static com.karcompany.heybeach.service.ServiceHelper.EXTRA_RECEIVER;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachService extends Service implements BeachListFetchTask.TaskListener {

	private static final String TAG = DefaultLogger.makeLogTag(BeachService.class);

	private static WeakReference<BeachService> serviceRef;

	private BeachFetchRequest mCurRequest;
	private List<BeachFetchRequest> mRequestQueue = new ArrayList<>();

	private BeachListFetchTask mBeachListFetchTask;

	private boolean isBound = false;
	// SERVICE BINDING
	private BeachServiceBinder binder = new BeachServiceBinder();

	public class BeachServiceBinder extends Binder {
		public BeachService getService() {
			return BeachService.this;
		}
	}

	@Override
	public void onCreate() {
		DefaultLogger.d(TAG, "onCreate");
		super.onCreate();
		serviceRef = new WeakReference<>(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		DefaultLogger.i(TAG, "onBind");
		isBound = true;
		return binder;
	}

	@Override
	public void onRebind(Intent intent) {
		isBound = true;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		DefaultLogger.i(TAG, "onUnbind");
		isBound = false;
		stopIfUnused();
		return false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			DefaultLogger.e(TAG, "onStartCommand - intent is null!", new IllegalArgumentException("onStartCommand Intent is null!"));
			return START_NOT_STICKY;
		}

		String action = intent.getAction();
		DefaultLogger.i(TAG, "onStartCommand - " + action);

		if (ServiceHelper.ACTION_FETCH_BEACHS.equals(action)) {
			fetchBeaches(intent.getIntExtra(EXTRA_PAGE_NO, 0), (ResultReceiver)intent.getParcelableExtra(EXTRA_RECEIVER));
		}

		return START_NOT_STICKY;
	}

	private void stopIfUnused() {
		boolean unused = (mCurRequest == null && mRequestQueue.size() == 0 && !isBound);
		DefaultLogger.i(TAG, "stopIfUnused " + unused);
		if (unused) stopSelf();
	}

	private void fetchBeaches(int pageNo, ResultReceiver resultReceiver) {
		DefaultLogger.d(TAG, "fetchBeaches =" + pageNo);
		BeachFetchRequest request = new BeachFetchRequest(pageNo, resultReceiver);
		addRequest(request);
	}

	private void addRequest(BeachFetchRequest request) {
		if (isDuplicateRequest(request)) {
			DefaultLogger.w(TAG, "Duplicate request -- exiting");
			return;
		}

		if (mCurRequest == null)
			processRequest(request);
		else {
			DefaultLogger.d(TAG, "Request queued");
			mRequestQueue.add(request);
		}
	}

	private boolean isDuplicateRequest(BeachFetchRequest request) {
		if (requestEquals(request, mCurRequest)) return true;
		for (BeachFetchRequest req : mRequestQueue) {
			if (requestEquals(request, req)) return true;
		}
		return false;
	}

	private boolean requestEquals(BeachFetchRequest req1, BeachFetchRequest req2) {
		if (req1 == null || req2 == null) return false;
		else return req1.requestEquals(req2);
	}

	private void processRequest(BeachFetchRequest request) {
		DefaultLogger.d(TAG, "processRequest : " + request.getPageNo());
		mCurRequest = request;
		mBeachListFetchTask = new BeachListFetchTask(request, this);
		mBeachListFetchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
	}

	private void processNext() {
		if (mRequestQueue.size() > 0) {
			processRequest(mRequestQueue.remove(0));
		} else {
			mCurRequest = null;
			stopIfUnused();
		}
	}

	@Override
	public void onTaskComplete(BeachFetchRequest request, BeachListApiResponse beachListApiResponse) {
		if(mCurRequest != null && requestEquals(request, mCurRequest)) {
			ResultReceiver resultReceiver = request.mResultReceiver;
			// To send a message to the Activity, create a pass a Bundle
			Bundle bundle = new Bundle();
			bundle.putParcelable(ServiceHelper.EXTRA_RESPONSE, beachListApiResponse);
			// Here we call send passing a resultCode and the bundle of extras
			resultReceiver.send(Activity.RESULT_OK, bundle);
		}
		processNext();
	}
}
