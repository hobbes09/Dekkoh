package com.dekkoh.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;

import com.dekkoh.application.DekkohApplication;
import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.datamodel.Interest;
import com.dekkoh.service.APIConstants.APIURL;
import com.dekkoh.util.Constants;
import com.dekkoh.util.Constants.SharedPreferenceConstants;
import com.dekkoh.util.Log;
import com.dekkoh.util.SharedPreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class APIProcessor {
	private static final String TAG = "APIProcessor";

	public DekkohUser loginUserWithGoogle(Activity activity, String user_id,
			String token, String email) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("provider", "Google");
		requestJsonObject.put("user_id", user_id);
		requestJsonObject.put("token", token);
		requestJsonObject.put("email", email);
		Map<String, String> responseHeaderMap = new HashMap<String, String>();
		String serviceURL = getBaseURL() + APIURL.USER_LOGIN_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(), responseHeaderMap);
		if (HTTPRequestHelper.getResponseCode() == 200) {

			SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
					.getInstance(activity);
			sharedPreferenceManager.save(
					SharedPreferenceConstants.AUTHORIZATION_TOCKEN,
					responseHeaderMap.get("Token"));
			DekkohUser dekkohUser = convertToObject(responseString,
					DekkohUser.class);
			DekkohApplication dekkohApplication = (DekkohApplication) activity
					.getApplication();
			dekkohApplication.setDekkohUser(dekkohUser);
			return dekkohUser;
		}
		return null;

	}

	public DekkohUser loginUserWithFacebook(Activity activity, String user_id,
			String token) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("provider", "Google");
		requestJsonObject.put("user_id", user_id);
		requestJsonObject.put("token", token);
		Map<String, String> responseHeaderMap = new HashMap<String, String>();
		String serviceURL = getBaseURL() + APIURL.USER_LOGIN_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(), responseHeaderMap);
		if (HTTPRequestHelper.getResponseCode() == 200) {

			SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
					.getInstance(activity);
			sharedPreferenceManager.save(
					SharedPreferenceConstants.AUTHORIZATION_TOCKEN,
					responseHeaderMap.get("Token"));
			DekkohUser dekkohUser = convertToObject(responseString,
					DekkohUser.class);
			DekkohApplication dekkohApplication = (DekkohApplication) activity
					.getApplication();
			dekkohApplication.setDekkohUser(dekkohUser);
			return dekkohUser;
		}
		return null;

	}

	public List<DekkohUser> getUsers(Activity activity, String location,
			String role, String user, int offset, int limit, long from,
			long to, String sort) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("location", location);
		requestJsonObject.put("role", role);
		requestJsonObject.put("user", user);
		requestJsonObject.put("offset", offset);
		requestJsonObject.put("limit", limit);
		requestJsonObject.put("from", from);
		requestJsonObject.put("to", to);
		requestJsonObject.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.USER_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), requestJsonObject.toString());
		if (responseString.isEmpty() || responseString.length() <= 0) {
			return null;
		} else {
			Type listType = new TypeToken<List<DekkohUser>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}

	}

	public static boolean addNewInterests(Activity activity,
			JSONObject interestJsonObject) throws Exception {
		String serviceURL = getBaseURL() + APIURL.GROUP_INTERESTS_SUFFIX;
		HTTPRequestHelper.processPostRequest(serviceURL,
				interestJsonObject.toString(), getAuthorizationToken(activity));
		if (HTTPRequestHelper.getResponseCode() == 200) {
			return true;
		}
		return false;

	}

	public static List<Interest> getInteresetList(Activity activity)
			throws Exception {
		String serviceURL = getBaseURL() + APIURL.GROUP_INTERESTS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity));
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

	public static String getAuthorizationToken(Activity activity) {
		return SharedPreferenceManager.getInstance(activity).getString(
				SharedPreferenceConstants.AUTHORIZATION_TOCKEN);
	}

	public static String getBaseURL() {
		if (Constants.PRODUCTION) {
			return APIURL.PROD_SERVICE_URL;
		} else {
			return APIURL.QA_SERVICE_URL;
		}
	}
}
