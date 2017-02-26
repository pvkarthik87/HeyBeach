package com.karcompany.heybeach.views;

import com.karcompany.heybeach.models.BeachMetaData;

import java.util.List;

/**
 * Created by pvkarthik on 2017-02-22.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface BeachListView {

	void onDataReceived(int pageNo, List<BeachMetaData> beachList);

	void onDataLoadFinished();

	void onDataLoadFailed(int pageNo, String errorMsg);

}
