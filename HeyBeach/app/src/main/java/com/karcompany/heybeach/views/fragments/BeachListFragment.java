package com.karcompany.heybeach.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karcompany.heybeach.R;
import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.models.BeachMetaData;
import com.karcompany.heybeach.presenters.BeachListPresenter;
import com.karcompany.heybeach.presenters.BeachListPresenterImpl;
import com.karcompany.heybeach.service.BeachResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.views.BeachListView;
import com.karcompany.heybeach.views.adapters.BeachListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by pvkarthik on 2017-02-20.
 */

public class BeachListFragment extends BaseFragment implements BeachListView, BeachResultReceiver.Receiver {

	private static final String TAG = DefaultLogger.makeLogTag(BeachListFragment.class);

	private RecyclerView mBeachRecyclerView;
	private BeachListAdapter mAdapter;
	private LinearLayoutManager mLayoutManager;

	private BeachListPresenter mBeachListPresenter;
	private long mVisibleThreshold = 5;

	private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			int totalItemCount = 0;
			int lastVisibleItem = 0;
			totalItemCount = mLayoutManager.getItemCount();
			lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			if (!mBeachListPresenter.isLoading()
					&& totalItemCount <= (lastVisibleItem + mVisibleThreshold) && !mAdapter.isDataLoadFinished()) {
				//End of the items load more data
				mBeachListPresenter.loadBeaches(getContext(), mResultReceiver);
			}
		}
	};

	private BeachResultReceiver mResultReceiver;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_beach_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	@Override
	protected void setUpPresenter() {
		mBeachListPresenter = BeachListPresenterImpl.getInstance();
		mBeachListPresenter.setView(this);
	}

	@Override
	protected void bindViews(View view) {
		mBeachRecyclerView = (RecyclerView) view.findViewById(R.id.beach_list);
	}

	@Override
	protected void unBindViews() {
		mBeachRecyclerView = null;
		mLayoutManager = null;
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpRecyclerView();
		setupServiceReceiver();
		mBeachRecyclerView.addOnScrollListener(mScrollListener);
	}

	private void setUpRecyclerView() {
		mAdapter = new BeachListAdapter();
		mLayoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		mBeachRecyclerView.setLayoutManager(mLayoutManager);
		mBeachRecyclerView.setAdapter(mAdapter);
	}

	// Setup the callback for when data is received from the service
	public void setupServiceReceiver() {
		mResultReceiver = new BeachResultReceiver(new Handler());
	}

	@Override
	public void onStart() {
		super.onStart();
		mBeachListPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mBeachListPresenter.onResume();
		// This is where we specify what happens when data is received from the service
		mResultReceiver.setReceiver(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		mBeachListPresenter.onPause();
		mResultReceiver.setReceiver(null);
	}

	@Override
	public void onStop() {
		super.onStop();
		mBeachListPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mBeachListPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mBeachRecyclerView.setAdapter(null);
		mBeachRecyclerView.removeOnScrollListener(mScrollListener);
		super.onDestroyView();
	}

	@Override
	public void onDataLoadFailed(int pageNo, String errorMsg) {

	}

	@Override
	public void onDataLoadFinished() {
		mAdapter.setDataFinished();
	}

	@Override
	public void onDataReceived(int pageNo, List<BeachMetaData> beachList) {
		DefaultLogger.d(TAG, "Page result-->"+pageNo);
		mAdapter.addData(beachList);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == RESULT_OK) {
			BeachListApiResponse beachListApiResponse = resultData.getParcelable(ServiceHelper.EXTRA_RESPONSE);
			if(beachListApiResponse != null) {
				mBeachListPresenter.onDataReceived(beachListApiResponse);
			}
		}
	}
}
