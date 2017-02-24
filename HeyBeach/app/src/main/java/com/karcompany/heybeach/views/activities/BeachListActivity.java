package com.karcompany.heybeach.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.karcompany.heybeach.R;
import com.karcompany.heybeach.models.RegisterApiResponse;
import com.karcompany.heybeach.presenters.BeachListPresenterImpl;
import com.karcompany.heybeach.presenters.HomePresenter;
import com.karcompany.heybeach.presenters.HomePresenterImpl;
import com.karcompany.heybeach.security.SecUtil;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.storage.KeyValueUtils;
import com.karcompany.heybeach.views.HomeView;
import com.karcompany.heybeach.views.fragments.UserLoginDialog;

/**
 * Created by pvkarthik on 2017-02-20.
 */

public class BeachListActivity extends BaseActivity implements HomeView, ApiResultReceiver.Receiver {

	private static final String LOGIN_DLG_TAG = "LOGIN_DLG_TAG";

	private UserLoginDialog mUserLoginDialog;
	private View mProgressLyt;
	private TextView mProgressTxt;

	private ApiResultReceiver mResultReceiver;

	private HomePresenter mHomePresenter;

	@Override
	protected void setUpPresenter() {
		mHomePresenter = HomePresenterImpl.getInstance();
		mHomePresenter.setView(this);
	}

	// Setup the callback for when data is received from the service
	public void setupServiceReceiver() {
		mResultReceiver = new ApiResultReceiver(new Handler());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beach_list);
		setTitle(getString(R.string.app_name));
		enableBackBtn(false);
		setUpUI();
	}

	private void setUpUI() {
		setupServiceReceiver();
	}

	@Override
	protected void bindViews(View view) {
		mProgressLyt = view.findViewById(R.id.progressLyt);
		mProgressTxt = (TextView)view.findViewById(R.id.progressText);
	}

	@Override
	protected void unBindViews() {
		mProgressLyt = null;
		mProgressTxt = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_beach_list, menu);
		MenuItem loginItem = menu.findItem(R.id.action_login);
		MenuItem profileItem = menu.findItem(R.id.action_myprofile);
		MenuItem logoutItem = menu.findItem(R.id.action_logout);
		if(KeyValueUtils.isLoggedIn(getApplicationContext())) {
			loginItem.setVisible(false);
			profileItem.setVisible(true);
			logoutItem.setVisible(true);
		} else {
			loginItem.setVisible(true);
			profileItem.setVisible(false);
			logoutItem.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_login: {
				goToLoginView();
				return true;
			}

			case R.id.action_myprofile: {
				goToProfileView();
				return true;
			}

			case R.id.action_logout: {
				showLogoutConfirmDlg();
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void goToLoginView() {
		mUserLoginDialog = UserLoginDialog.newInstance();
		mUserLoginDialog.show(getSupportFragmentManager(), LOGIN_DLG_TAG);
	}

	private void goToProfileView() {

	}

	private void showLogoutConfirmDlg() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						//Yes button clicked
						doLogout();
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						//No button clicked
						break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.logout).setMessage(R.string.logout_confirmation_msg).setPositiveButton(R.string.yes, dialogClickListener)
				.setNegativeButton(R.string.no, dialogClickListener).show();
	}

	private void doLogout() {
		onLogoutProgress();
		ServiceHelper.logout(getApplicationContext(), mResultReceiver);
	}

	private void onLogoutProgress() {
		mProgressTxt.setText(R.string.logout_progress);
		mProgressLyt.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mHomePresenter.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mHomePresenter.onResume();
		// This is where we specify what happens when data is received from the service
		mResultReceiver.setReceiver(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mHomePresenter.onPause();
		mResultReceiver.setReceiver(null);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mHomePresenter.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHomePresenter.onDestroy();
	}

	@Override
	public void onRegisterSuccess() {
		Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
		invalidateOptionsMenu();
	}

	@Override
	public void onLogoutSuccess() {
		Toast.makeText(this, getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
		mProgressLyt.setVisibility(View.GONE);
		invalidateOptionsMenu();
	}

	@Override
	public void onLoginSuccess() {
		Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
		invalidateOptionsMenu();
	}

	private void onLogoutFailed() {
		Toast.makeText(this, getString(R.string.logout_failure), Toast.LENGTH_SHORT).show();
		mProgressLyt.setVisibility(View.GONE);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == RESULT_OK) {
			ApiResponse response = resultData.getParcelable(ServiceHelper.EXTRA_RESPONSE);
			if(response != null) {
				switch (response.getApiType()) {
					case LOGOUT: {
						if (response.getResponseCode() == ApiResponse.SUCCESS) {
							onLogoutSuccess();
						} else {
							onLogoutFailed();
						}
					}
					break;
				}
			}
		}
	}
}
