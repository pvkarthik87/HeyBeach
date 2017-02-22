package com.karcompany.heybeach.networking;

import android.net.Uri;

import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.models.BeachMetaData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by pvkarthik on 2017-02-20.
 *
 * REST Client which communicates to server to perform some operations
 */

public class ApiRepo {

	private static final String TAG = DefaultLogger.makeLogTag(ApiRepo.class);

	private static final String PARAM_PAGE = "page";

	private static final String BEACH_URL = "beaches";

	private static String getBaseUrl(String url) {
		return Constants.SERVER_BASE_URL + url;
	}

	public static BeachListApiResponse fetchBeaches(int pageNo) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put(PARAM_PAGE, ""+pageNo);
		String rawResponse = doGetRequest(getBaseUrl(BEACH_URL), paramsMap);
		ArrayList<BeachMetaData> beachList = ApiUtils.parseBeachListApiResponse(rawResponse);
		BeachListApiResponse beachListApiResponse = new BeachListApiResponse();
		beachListApiResponse.setPageNo(pageNo);
		beachListApiResponse.setBeachList(beachList);
		return beachListApiResponse;
	}

	private static String doGetRequest(String baseUrl, Map<String, String> keyValuePair) {
		try {

			Uri.Builder builder = Uri.parse(baseUrl)
					.buildUpon();

			if(keyValuePair != null) {
				for (Map.Entry<String, String> entry : keyValuePair.entrySet()) {
					builder.appendQueryParameter(entry.getKey(), entry.getValue());
				}
			}

			String finalUrl = builder.build().toString();

			URL url = new URL(finalUrl); // here is your URL path
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int responseCode=conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {

				BufferedReader in=new BufferedReader(new
						InputStreamReader(
						conn.getInputStream()));

				StringBuilder sb = new StringBuilder("");
				String line;
				while((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				return sb.toString();
			}
			else {
				return null;
			}
		}
		catch(Exception e){
			return null;
		}
	}

}
