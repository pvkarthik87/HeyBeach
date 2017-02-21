package com.karcompany.heybeach.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.heybeach.R;

/**
 * Created by pvkarthik on 2017-02-20.
 */

public class BeachListFragment extends BaseFragment {

	private RecyclerView mBeachRecyclerView;

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

	}

	@Override
	protected void bindViews(View view) {
		mBeachRecyclerView = (RecyclerView) view.findViewById(R.id.beach_list);
	}

	@Override
	protected void unBindViews() {
		mBeachRecyclerView = null;
	}

	private void setUpUI(Bundle savedInstanceState) {

	}

}
