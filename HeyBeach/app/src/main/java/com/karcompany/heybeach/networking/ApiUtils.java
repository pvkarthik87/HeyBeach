package com.karcompany.heybeach.networking;

import android.text.TextUtils;

import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.models.BeachMetaData;
import com.karcompany.heybeach.models.UserMetaData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class ApiUtils {

	private static final String PARAM_ID = "_id";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_URL = "url";
	private static final String PARAM_WIDTH = "width";
	private static final String PARAM_HEIGHT = "height";
	private static final String PARAM_EMAIL = "email";

	public static ArrayList<BeachMetaData> parseBeachListApiResponse(String response) {
		if(TextUtils.isEmpty(response)) return null;
			try {
				// Getting JSON Array node
				JSONArray beachListItems = new JSONArray(response);
				ArrayList<BeachMetaData> beachList = new ArrayList<>(beachListItems.length());

				// looping through All Contacts
				for (int i = 0; i < beachListItems.length(); i++) {
					JSONObject b = beachListItems.getJSONObject(i);
					BeachMetaData beachMetaData = new BeachMetaData();
					beachMetaData.setId(b.getString(PARAM_ID));
					beachMetaData.setName(b.getString(PARAM_NAME));
					beachMetaData.setUrl(b.getString(PARAM_URL));
					beachMetaData.setWidth(b.getString(PARAM_WIDTH));
					beachMetaData.setHeight(b.getString(PARAM_HEIGHT));
					beachList.add(beachMetaData);
				}
				return beachList;
			} catch (final JSONException e) {
				return null;
			}
	}

	public static UserMetaData parseFetchUserApiResponse(String response) {
		if(TextUtils.isEmpty(response)) return null;
		try {
			JSONObject jsonObject = new JSONObject(response);
			UserMetaData userMetaData = new UserMetaData();
			userMetaData.setId(jsonObject.getString(PARAM_ID));
			userMetaData.setEmail(jsonObject.getString(PARAM_EMAIL));
			return userMetaData;
		} catch (final JSONException e) {
			return null;
		}
	}

}
