package com.dekkoh.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.dekkoh.datamodel.Interest;
import com.dekkoh.service.APIConstants.APIURL;
import com.dekkoh.util.Constants;
import com.dekkoh.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class APIProcessor {
	private static final String TAG = "APIProcessor";

	public static boolean addNewInterests(JSONObject interestJsonObject)
			throws ClientProtocolException, IOException {
		String serviceURL = getBaseURL() + APIURL.GROUP_INTERESTS_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, interestJsonObject.toString());
		return false;

	}

	public List<Interest> getInteresetList() throws ClientProtocolException,
			IOException {
		String serviceURL = getBaseURL() + APIURL.GROUP_INTERESTS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL);
		Log.i(TAG, responseString);
		if (responseString.isEmpty() || responseString.length() <= 0) {
			return null;
		} else {
			Type listType = new TypeToken<List<Interest>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	// Don't delete! Even if not used!
	public static String convertToJSON(Object object) {
		final Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		Log.i(TAG, "convertToJSON : " + jsonString);
		return jsonString;
	}

	// Don't delete! Even if not used!
	public static <T> T convertToObject(String responseString, Type typeOfT) {
		Log.i(TAG, "convertToObject : " + responseString);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson.fromJson(responseString, typeOfT);
	}

	// Don't delete! Even if not used!
	public static <T> T convertToObject(String responseString, Class<T> classOfT) {
		Log.i(TAG, "convertToObject : " + responseString);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson.fromJson(responseString, classOfT);
	}

	public static String getBaseURL() {
		if (Constants.PRODUCTION) {
			return "";
		} else {
			return "";
		}
	}
}
