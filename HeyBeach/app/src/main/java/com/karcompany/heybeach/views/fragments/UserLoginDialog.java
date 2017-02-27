package com.karcompany.heybeach.views.fragments;

/**
 * Created by pvkarthik on 2017-02-23.
 *
 * User Login dialog which allows user to login/register.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.karcompany.heybeach.R;
import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.presenters.MainLoginPresenter;
import com.karcompany.heybeach.presenters.MainLoginPresenterImpl;
import com.karcompany.heybeach.uiutils.SmartFragmentStatePagerAdapter;
import com.karcompany.heybeach.views.MainLoginView;

import java.util.ArrayList;
import java.util.List;

public class UserLoginDialog extends BaseDialogFragment implements MainLoginView {

	public static UserLoginDialog newInstance() {
		return new UserLoginDialog();
	}

	private static final String TAG = DefaultLogger.makeLogTag(UserLoginDialog.class);

	private static class LoginViewPagerAdapter extends SmartFragmentStatePagerAdapter {

		private List<Constants.Tab> mTabList;

		public LoginViewPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			mTabList = new ArrayList<>(4);
		}

		@Override
		public Fragment getItem(int position) {
			Constants.Tab tab = mTabList.get(position);
			switch (tab.getType()) {
				case LOGIN: {
					return UserLoginFragment.newInstance();
				}

				case SINUP: {
					return UserSignUpFragment.newInstance();
				}
			}
			return null;
		}

		@Override
		public int getCount() {
			return mTabList.size();
		}

		public void setTabs(Constants.Tab[] tabs) {
			if (tabs != null && tabs.length > 0) {
				mTabList.clear();
				for(Constants.Tab tab : tabs) {
					mTabList.add(tab);
				}
				notifyDataSetChanged();
			}
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void clear() {
			mTabList.clear();
			notifyDataSetChanged();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Constants.Tab tabItem = mTabList.get(position);
			switch (tabItem.getType()) {
				case LOGIN: {
					return "Login";
				}

				case SINUP: {
					return "Register";
				}
			}
			return "";
		}
	}

	private MainLoginPresenter mMainLoginPresenter;

	private ViewPager mViewPager;
	private TabLayout mTabLyt;
	private View mProgressLyt;
	private TextView mProgressTxt;

	private LoginViewPagerAdapter mLoginViewPagerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_user_login, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		getDialog().setCanceledOnTouchOutside(true);
		mLoginViewPagerAdapter = new LoginViewPagerAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mLoginViewPagerAdapter);
		mTabLyt.setupWithViewPager(mViewPager);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		// request a window without the title
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	@Override
	protected void bindViews(View view) {
		mViewPager = (ViewPager)view.findViewById(R.id.loginViewPager);
		mTabLyt = (TabLayout)view.findViewById(R.id.tab_layout);
		mProgressLyt = view.findViewById(R.id.progressLyt);
		mProgressTxt = (TextView)view.findViewById(R.id.progressText);
	}

	@Override
	protected void unBindViews() {
		mViewPager = null;
		mTabLyt = null;
		mProgressLyt = null;
		mProgressTxt = null;
	}

	@Override
	protected void setUpPresenter() {
		mMainLoginPresenter = MainLoginPresenterImpl.getInstance();
		mMainLoginPresenter.setView(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mMainLoginPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mMainLoginPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMainLoginPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mMainLoginPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		mMainLoginPresenter.onDestroy();
		mLoginViewPagerAdapter.clear();
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		mViewPager.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void updateTabs(Constants.Tab[] tabs) {
		mLoginViewPagerAdapter.setTabs(tabs);
	}

	@Override
	public void onRegisterProgress() {
		mProgressTxt.setText(R.string.register_progress);
		mProgressLyt.setVisibility(View.VISIBLE);
		getDialog().setCanceledOnTouchOutside(false);
	}

	@Override
	public void onLoginProgress() {
		mProgressTxt.setText(R.string.login_progress);
		mProgressLyt.setVisibility(View.VISIBLE);
		getDialog().setCanceledOnTouchOutside(false);
	}

	@Override
	public void onLoginSuccess() {
		dismiss();
	}

	@Override
	public void onLoginFailed() {
		Toast.makeText(getContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
		mProgressLyt.setVisibility(View.GONE);
		getDialog().setCanceledOnTouchOutside(true);
	}

	@Override
	public void onRegisterSuccess() {
		dismiss();
	}

	@Override
	public void onRegisterFailed() {
		Toast.makeText(getContext(), getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
		mProgressLyt.setVisibility(View.GONE);
		getDialog().setCanceledOnTouchOutside(true);
	}
}
