package com.karcompany.heybeach.views.activities;

/**
 * Created by pvkarthik on 2017-02-20.
 *
 * BaseActivity which all other activities should inherit.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.karcompany.heybeach.R;

/**
 * BaseActivity will be extended by every activity in the app, and it hides
 * common logic for concrete activities, like toolbar.
 */
public abstract class BaseActivity extends AppCompatActivity {

	protected boolean isActivityVisible;

	private Toolbar mToolbar;

	protected abstract void setUpPresenter();

	protected abstract void bindViews(View view);

	protected abstract void unBindViews();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpPresenter();
	}

	protected void onResume() {
		super.onResume();
		isActivityVisible = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isActivityVisible = false;
	}

	@Override
	public void setContentView(int layoutResID) {
		View view = getLayoutInflater().inflate(layoutResID, null);
		super.setContentView(view);
		bindCommonViews(view);
		configureToolbar();
	}

	@Override
	protected void onDestroy() {
		unBindCommonViews();
		super.onDestroy();
	}

	private void bindCommonViews(View view) {
		mToolbar = (Toolbar) view.findViewById(R.id.mainToolbar);
		bindViews(view);
	}

	private void unBindCommonViews() {
		unBindViews();
		mToolbar = null;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	private void configureToolbar() {
		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
			ActionBar actionBar = getSupportActionBar();
			if(actionBar == null) return;
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}

	public void enableBackBtn(boolean show) {
		if (mToolbar != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(show);
		}
	}
}
