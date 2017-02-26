package com.karcompany.heybeach.views.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.karcompany.heybeach.R;
import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.models.UserMetaData;
import com.karcompany.heybeach.presenters.UserProfilePresenter;
import com.karcompany.heybeach.presenters.UserProfilePresenterImpl;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.views.UserProfileView;

/**
 * Created by pvkarthik on 2017-02-24.
 */

public class UserProfileActivity extends BaseActivity implements UserProfileView {

	private View mProgressLyt;
	private TextView mProgressTxt;
	private TextView mUserIdTxtView;
	private TextView mUserEmailTxtView;

	private ApiResultReceiver mResultReceiver;

	private UserProfilePresenter mUserProfilePresenter;

	@Override
	protected void setUpPresenter() {
		mUserProfilePresenter = UserProfilePresenterImpl.getInstance();
		mUserProfilePresenter.setView(this);
	}

	// Setup the callback for when data is received from the service
	public void setupServiceReceiver() {
		mResultReceiver = new ApiResultReceiver(new Handler());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		setTitle(getString(R.string.user_profile));
		setUpUI();
	}

	private void setUpUI() {
		setupServiceReceiver();
	}

	@Override
	protected void bindViews(View view) {
		mProgressLyt = view.findViewById(R.id.progressLyt);
		mProgressTxt = (TextView)view.findViewById(R.id.progressText);
		mUserIdTxtView = (TextView)view.findViewById(R.id.userId);
		mUserEmailTxtView = (TextView)view.findViewById(R.id.userEmail);
	}

	@Override
	protected void unBindViews() {
		mProgressLyt = null;
		mProgressTxt = null;
	}

	@Override
	protected void onStart() {
		super.onStart();
		mUserProfilePresenter.onStart();
		loadUser();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mUserProfilePresenter.onResume();
		// This is where we specify what happens when data is received from the service
		mResultReceiver.setReceiver(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mUserProfilePresenter.onPause();
		mResultReceiver.setReceiver(null);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mUserProfilePresenter.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mUserProfilePresenter.onDestroy();
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == RESULT_OK) {
			ApiResponse response = resultData.getParcelable(ServiceHelper.EXTRA_RESPONSE);
			if(response != null) {
				switch (response.getApiType()) {
					case FETCH_PROFILE: {
						if (response.getResponseCode() == ApiResponse.SUCCESS) {
							UserMetaData userMetaData = (UserMetaData) response.getResponse();
							updateProfile(userMetaData);
						}
					}
					break;
				}
			}
		}
	}

	private void onLoadProgress() {
		mProgressTxt.setText(R.string.user_profile_progress);
		mProgressLyt.setVisibility(View.VISIBLE);
	}

	private void onLoadFinished() {
		mProgressLyt.setVisibility(View.GONE);
	}

	private void updateProfile(UserMetaData userMetaData) {
		onLoadFinished();
		if(userMetaData != null) {
			mUserIdTxtView.setText(userMetaData.getId());
			mUserEmailTxtView.setText(userMetaData.getEmail());
		}
	}

	private void loadUser() {
		onLoadProgress();
		mUserProfilePresenter.loadUserProfile(getApplicationContext(), mResultReceiver);
	}
}
