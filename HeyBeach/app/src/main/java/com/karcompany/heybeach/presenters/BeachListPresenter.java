package com.karcompany.heybeach.presenters;

import android.content.Context;

import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.mvputils.Presenter;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.views.BeachListView;

/**
 * Created by pvkarthik on 2017-02-22.
 *
 * Presenter interface which helps in getting beaches from server.
 *
 */

public interface BeachListPresenter extends Presenter {

	void setView(BeachListView beachListView);

	void loadBeaches(Context ctx, ApiResultReceiver apiResultReceiver);

	boolean isLoading();

	void onDataReceived(BeachListApiResponse beachListApiResponse);

}
