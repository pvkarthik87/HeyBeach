package com.karcompany.heybeach.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.os.ResultReceiver;

import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.models.RegisterApiResponse;
import com.karcompany.heybeach.networking.BeachListFetchTask;
import com.karcompany.heybeach.networking.FetchUserTask;
import com.karcompany.heybeach.networking.LoginTask;
import com.karcompany.heybeach.networking.LogoutTask;
import com.karcompany.heybeach.networking.RegisterTask;
import com.karcompany.heybeach.networking.TaskListener;
import com.karcompany.heybeach.storage.KeyValueUtils;

import java.util.ArrayList;
import java.util.List;

import static com.karcompany.heybeach.service.ServiceHelper.EXTRA_EMAIL;
import static com.karcompany.heybeach.service.ServiceHelper.EXTRA_PAGE_NO;
import static com.karcompany.heybeach.service.ServiceHelper.EXTRA_PWD;
import static com.karcompany.heybeach.service.ServiceHelper.EXTRA_RECEIVER;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class ApiService extends Service implements TaskListener {

	private static final String TAG = DefaultLogger.makeLogTag(ApiService.class);

	private ApiRequest mCurRequest;
	private List<ApiRequest> mRequestQueue = new ArrayList<>();

	private boolean isBound = false;
	// SERVICE BINDING
	private BeachServiceBinder binder = new BeachServiceBinder();

	public class BeachServiceBinder extends Binder {
		public ApiService getService() {
			return ApiService.this;
		}
	}

	@Override
	public void onCreate() {
		DefaultLogger.d(TAG, "onCreate");
		super.onCreate();
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

		if (ServiceHelper.ACTION_REGISTER.equals(action)) {
			doRegister(intent.getStringExtra(EXTRA_EMAIL), intent.getStringExtra(EXTRA_PWD), (ResultReceiver)intent.getParcelableExtra(EXTRA_RECEIVER));
		}

		if (ServiceHelper.ACTION_LOGIN.equals(action)) {
			doLogin(intent.getStringExtra(EXTRA_EMAIL), intent.getStringExtra(EXTRA_PWD), (ResultReceiver)intent.getParcelableExtra(EXTRA_RECEIVER));
		}

		if (ServiceHelper.ACTION_LOGOUT.equals(action)) {
			doLogout((ResultReceiver)intent.getParcelableExtra(EXTRA_RECEIVER));
		}

		if (ServiceHelper.ACTION_FETCH_PROFILE.equals(action)) {
			fetchUserDetails((ResultReceiver)intent.getParcelableExtra(EXTRA_RECEIVER));
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

	private void doRegister(String email, String pwd, ResultReceiver resultReceiver) {
		DefaultLogger.d(TAG, "doRegister");
		RegisterRequest request = new RegisterRequest(email, pwd, resultReceiver);
		addRequest(request);
	}

	private void doLogin(String email, String pwd, ResultReceiver resultReceiver) {
		DefaultLogger.d(TAG, "doLogin");
		LoginRequest request = new LoginRequest(email, pwd, resultReceiver);
		addRequest(request);
	}

	private void doLogout(ResultReceiver resultReceiver) {
		if(KeyValueUtils.isLoggedIn(getApplicationContext())) {
			DefaultLogger.d(TAG, "doLogout");
			LogoutRequest request = new LogoutRequest(KeyValueUtils.getKey(getApplicationContext(), Constants.KEY_ACCESS_TOKEN), resultReceiver);
			addRequest(request);
		}
	}

	private void fetchUserDetails(ResultReceiver resultReceiver) {
		if(KeyValueUtils.isLoggedIn(getApplicationContext())) {
			DefaultLogger.d(TAG, "fetchUserDetails");
			FetchUserRequest request = new FetchUserRequest(KeyValueUtils.getKey(getApplicationContext(), Constants.KEY_ACCESS_TOKEN), resultReceiver);
			addRequest(request);
		}
	}

	private void addRequest(ApiRequest request) {
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

	private boolean isDuplicateRequest(ApiRequest request) {
		if (requestEquals(request, mCurRequest)) return true;
		for (ApiRequest req : mRequestQueue) {
			if (requestEquals(request, req)) return true;
		}
		return false;
	}

	private boolean requestEquals(ApiRequest req1, ApiRequest req2) {
		if (req1 == null || req2 == null) return false;
		else return req1.requestEquals(req2);
	}

	private void processRequest(ApiRequest request) {
		mCurRequest = request;
		switch (request.apiType) {
			case FETCH_BEACHES: {
				BeachFetchRequest beachFetchRequest = (BeachFetchRequest) request;
				DefaultLogger.d(TAG, "processRequest : BeachFetchRequest->" + beachFetchRequest.getPageNo());
				BeachListFetchTask beachListFetchTask = new BeachListFetchTask(beachFetchRequest, this);
				beachListFetchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, beachFetchRequest);
			}
			break;

			case REGISTER: {
				RegisterRequest registerRequest = (RegisterRequest) request;
				DefaultLogger.d(TAG, "processRequest : RegisterRequest");
				RegisterTask registerTask = new RegisterTask(registerRequest, this);
				registerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, registerRequest);
			}
			break;

			case LOGIN: {
				LoginRequest loginRequest = (LoginRequest) request;
				DefaultLogger.d(TAG, "processRequest : LoginRequest");
				LoginTask loginTask = new LoginTask(loginRequest, this);
				loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loginRequest);
			}
			break;

			case LOGOUT: {
				LogoutRequest logoutRequest = (LogoutRequest) request;
				DefaultLogger.d(TAG, "processRequest : LogoutRequest");
				LogoutTask logoutTask = new LogoutTask(logoutRequest, this);
				logoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, logoutRequest);
			}
			break;

			case FETCH_PROFILE: {
				FetchUserRequest fetchUserRequest = (FetchUserRequest) request;
				DefaultLogger.d(TAG, "processRequest : fetchUserRequest");
				FetchUserTask fetchUserTask = new FetchUserTask(fetchUserRequest, this);
				fetchUserTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, fetchUserRequest);
			}
			break;
		}
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
	public void onTaskComplete(ApiRequest request, ApiResponse response) {
		if(mCurRequest != null && requestEquals(request, mCurRequest)) {
			if(response.getResponseCode() == ApiResponse.SUCCESS) {
				switch (response.getApiType()) {
					case REGISTER:
					case LOGIN:
						saveResponse(response);
						break;
					case LOGOUT:
						clearUserData();
						break;
				}
			}
			ResultReceiver resultReceiver = request.resultReceiver;
			// To send a message to the Activity, create a pass a Bundle
			Bundle bundle = new Bundle();
			bundle.putParcelable(ServiceHelper.EXTRA_RESPONSE, response);
			// Here we call send passing a resultCode and the bundle of extras
			resultReceiver.send(Activity.RESULT_OK, bundle);
		}
		processNext();
	}

	private void saveResponse(ApiResponse apiResponse) {
		String authToken = ((RegisterApiResponse)apiResponse.getResponse()).getAuthToken();
		KeyValueUtils.updateKey(getApplicationContext(), Constants.KEY_ACCESS_TOKEN, authToken);
	}

	private void clearUserData() {
		KeyValueUtils.removeKey(getApplicationContext(), Constants.KEY_ACCESS_TOKEN);
	}
}
