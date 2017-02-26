package com.karcompany.heybeach.networking;

import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.service.ApiRequest;
import com.karcompany.heybeach.service.ApiResponse;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public interface TaskListener {

	void onTaskComplete(ApiRequest request, ApiResponse response);

}
