package com.karcompany.heybeach.networking;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.karcompany.heybeach.config.Constants;
import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.models.BeachMetaData;
import com.karcompany.heybeach.models.DeleteApiResponse;
import com.karcompany.heybeach.models.RegisterApiResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_PASSWORD = "password";
	private static final String HEADER_ATOKEN = "x-auth";

	private static final String BEACH_URL = "beaches";
	private static final String REGISTER_URL = "user/register";
	private static final String LOGIN_URL = "user/login";
	private static final String LOGOUT_URL = "user/logout";


	public static String getBaseUrl(String url) {
		return Constants.SERVER_BASE_URL + url;
	}

	public static BeachListApiResponse fetchBeaches(int pageNo) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put(PARAM_PAGE, ""+pageNo);
		String rawResponse = doGetRequest(getBaseUrl(BEACH_URL), paramsMap);
		ArrayList<BeachMetaData> beachList = ApiUtils.parseBeachListApiResponse(rawResponse);
		if(beachList == null) return null;
		BeachListApiResponse beachListApiResponse = new BeachListApiResponse();
		beachListApiResponse.setPageNo(pageNo);
		beachListApiResponse.setBeachList(beachList);
		return beachListApiResponse;
	}

	public static RegisterApiResponse doRegister(String email, String pwd) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put(PARAM_EMAIL, email);
		paramsMap.put(PARAM_PASSWORD, pwd);
		String authToken = doPostRequest(getBaseUrl(REGISTER_URL), paramsMap);
		if(TextUtils.isEmpty(authToken)) return null;
		RegisterApiResponse registerApiResponse = new RegisterApiResponse();
		registerApiResponse.setAuthToken(authToken);
		return registerApiResponse;
	}

	public static RegisterApiResponse doLogin(String email, String pwd) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put(PARAM_EMAIL, email);
		paramsMap.put(PARAM_PASSWORD, pwd);
		String authToken = doPostRequest(getBaseUrl(LOGIN_URL), paramsMap);
		if(TextUtils.isEmpty(authToken)) return null;
		RegisterApiResponse registerApiResponse = new RegisterApiResponse();
		registerApiResponse.setAuthToken(authToken);
		return registerApiResponse;
	}

	public static DeleteApiResponse doLogout(String authToken) {
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put(HEADER_ATOKEN, authToken);
		String deleteResponse = doDeleteRequest(getBaseUrl(LOGOUT_URL), headersMap);
		if(deleteResponse == null) return null;
		DeleteApiResponse deleteApiResponse = new DeleteApiResponse();
		return deleteApiResponse;
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
			conn.setRequestProperty("Cache-Control", "no-cache");
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

	private static String doPostRequest(String baseUrl, Map<String, String> keyValuePair) {
		try {
			JSONObject postDataParams = new JSONObject();
			if(keyValuePair != null) {
				for (Map.Entry<String, String> entry : keyValuePair.entrySet()) {
					postDataParams.put(entry.getKey(), entry.getValue());
				}
			}

			URL url = new URL(baseUrl); // here is your URL path

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setReadTimeout(Constants.CONN_READ_TIMEOUT);
			conn.setConnectTimeout(Constants.CONN_TIMEOUT);
			conn.setRequestMethod(ApiMethod.POST.getMethodName());
			conn.setDoInput(true);
			conn.setDoOutput(true);

			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			writer.write(postDataParams.toString());

			writer.flush();
			writer.close();
			os.close();
			int responseCode=conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				return conn.getHeaderField("x-auth");
			}
			else {
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
		}
		catch(Exception e){
			return null;
		}
	}

	private static String doDeleteRequest(String baseUrl, Map<String, String> headerMap) {
		try {
			URL url = new URL(baseUrl); // here is your URL path

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Cache-Control", "no-cache");
			if(headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			conn.setReadTimeout(Constants.CONN_READ_TIMEOUT);
			conn.setConnectTimeout(Constants.CONN_TIMEOUT);
			conn.setRequestMethod(ApiMethod.DELETE.getMethodName());
			conn.setDoOutput(true);
			conn.connect();

			int responseCode=conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				return "";
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
