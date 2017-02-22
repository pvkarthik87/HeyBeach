package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.models.BeachMetaData;
import com.karcompany.heybeach.service.BeachResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.views.BeachListView;

import java.util.List;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachListPresenterImpl implements BeachListPresenter {

	private static BeachListPresenterImpl mInstance;

	private BeachListView mView;

	private boolean mIsLoading;

	private int mCurrPageNo = -1;

	private BeachListPresenterImpl() {

	}

	public static BeachListPresenterImpl getInstance() {
		if (mInstance == null) {
			synchronized (BeachListPresenterImpl.class) {
				if (mInstance == null) {
					mInstance = new BeachListPresenterImpl();
				}
			}
		}
		return mInstance;
	}

	@Override
	public void setView(BeachListView beachListView) {
		mView = beachListView;
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
	}

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	@Override
	public void loadBeaches(Context ctx, BeachResultReceiver beachResultReceiver) {
		synchronized (this) {
			if (!mIsLoading) {
				mIsLoading = true;
				mCurrPageNo++;
				ServiceHelper.fetchBeaches(ctx, mCurrPageNo, beachResultReceiver);
			}
		}
	}

	@Override
	public void onDataReceived(BeachListApiResponse beachListApiResponse) {
		if(beachListApiResponse != null) {
			mIsLoading = false;
			List<BeachMetaData> beachList = beachListApiResponse.getBeachList();
			if(beachList == null || beachList.size() == 0) {
				if (mView != null) {
					mView.onDataLoadFinished();
				}
			} else {
				if (mView != null) {
					mView.onDataReceived(beachListApiResponse.getPageNo(), beachListApiResponse.getBeachList());
				}
			}
		}
	}
}
