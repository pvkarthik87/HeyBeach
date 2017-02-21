package com.karcompany.heybeach.views.activities;

import android.os.Bundle;
import android.view.View;

import com.karcompany.heybeach.R;

/**
 * Created by pvkarthik on 2017-02-20.
 */

public class BeachListActivity extends BaseActivity {

	@Override
	protected void setUpPresenter() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beach_list);
		setTitle(getString(R.string.app_name));
		enableBackBtn(false);
	}

	@Override
	protected void bindViews(View view) {

	}

	@Override
	protected void unBindViews() {

	}
}
