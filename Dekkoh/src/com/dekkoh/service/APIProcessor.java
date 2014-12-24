package com.dekkoh.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.text.TextUtils;

import com.dekkoh.application.DekkohApplication;
import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.datamodel.Interest;
import com.dekkoh.datamodel.Question;
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

	/**
	 * Method to login user with Google
	 * 
	 * @param activity
	 * @param user_id
	 * @param token
	 * @param email
	 * @return DekkohUser
	 * @throws Exception
	 */
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

	/**
	 * Method to login user with Facebook
	 * 
	 * @param activity
	 * @param user_id
	 * @param token
	 * @return DekkohUser
	 * @throws Exception
	 */
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

	/**
	 * Method to get Users List
	 * 
	 * @param activity
	 * @param location
	 * @param role
	 * @param user
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param sort
	 * @return List<DekkohUser>
	 * @throws Exception
	 */
	public List<DekkohUser> getUsersList(Activity activity, String location,
			String role, String user, int offset, int limit,
			long fromTimestamp, long toTimestamp, String sort) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("location", location);
		requestJsonObject.put("role", role);
		requestJsonObject.put("user", user);
		if (offset > 0)
			requestJsonObject.put("offset", offset);
		if (limit > 0)
			requestJsonObject.put("limit", limit);
		if (fromTimestamp > 0)
			requestJsonObject.put("from", fromTimestamp);
		if (toTimestamp > 0)
			requestJsonObject.put("to", toTimestamp);
		if (sort != null)
			requestJsonObject.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.USER_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), requestJsonObject.toString());
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<DekkohUser>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}

	}

	/**
	 * Method to post Question
	 * 
	 * @param activity
	 * @param question
	 * @param location
	 * @param longitude
	 * @param latitude
	 * @param user_id
	 * @param image
	 * @param interest_id
	 * @param user_image
	 * @param timestamp
	 * @return Question
	 * @throws Exception
	 */
	public Question postQuestion(Activity activity, String question,
			String location, String longitude, String latitude, String user_id,
			String image, String interest_id, String user_image,
			String timestamp) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("question", question);
		requestJsonObject.put("location", location);
		requestJsonObject.put("longitude", longitude);
		requestJsonObject.put("latitude", latitude);
		requestJsonObject.put("user_id", user_id);
		requestJsonObject.put("image", image);
		requestJsonObject.put("interest_id", interest_id);
		requestJsonObject.put("user_image", user_image);
		requestJsonObject.put("timestamp", timestamp);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200) {
			return convertToObject(responseString, Question.class);
		}
		return null;
	}

	/**
	 * Method to get Questions List
	 * 
	 * @param activity
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param date
	 * @param location
	 * @param perimeter
	 * @param keyword
	 * @param unanswered
	 * @param user
	 * @param interest
	 * @param sort
	 * @return List<Question>
	 * @throws Exception
	 */
	public static List<Question> getQuestionList(Activity activity, int offset,
			int limit, long fromTimestamp, long toTimestamp, String date,
			String location, int perimeter, String keyword, Boolean unanswered,
			String user, String interest, String sort) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		if (offset > 0)
			requestJsonObject.put("offset", offset);
		if (limit > 0)
			requestJsonObject.put("limit", limit);
		if (fromTimestamp > 0)
			requestJsonObject.put("from", fromTimestamp);
		if (toTimestamp > 0)
			requestJsonObject.put("to", toTimestamp);
		if (date != null)
			requestJsonObject.put("date", date);
		if (location != null)
			requestJsonObject.put("location", location);
		if (location != null && perimeter > 0)
			requestJsonObject.put("perimeter", perimeter);
		if (keyword != null)
			requestJsonObject.put("keyword", keyword);
		if (unanswered != null)
			requestJsonObject.put("unanswered", unanswered);
		if (user != null)
			requestJsonObject.put("user", user);
		if (interest != null)
			requestJsonObject.put("interest", interest);
		if (sort != null)
			requestJsonObject.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), requestJsonObject.toString());
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to get Questions Count
	 * 
	 * @param activity
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param date
	 * @param location
	 * @param perimeter
	 * @param keyword
	 * @param unanswered
	 * @param user
	 * @param interest
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public static int getQuestionCount(Activity activity, int offset,
			int limit, long fromTimestamp, long toTimestamp, String date,
			String location, int perimeter, String keyword, Boolean unanswered,
			String user, String interest, String sort) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		if (offset > 0)
			requestJsonObject.put("offset", offset);
		if (limit > 0)
			requestJsonObject.put("limit", limit);
		if (fromTimestamp > 0)
			requestJsonObject.put("from", fromTimestamp);
		if (toTimestamp > 0)
			requestJsonObject.put("to", toTimestamp);
		if (date != null)
			requestJsonObject.put("date", date);
		if (location != null)
			requestJsonObject.put("location", location);
		if (location != null && perimeter > 0)
			requestJsonObject.put("perimeter", perimeter);
		if (keyword != null)
			requestJsonObject.put("keyword", keyword);
		if (unanswered != null)
			requestJsonObject.put("unanswered", unanswered);
		if (user != null)
			requestJsonObject.put("user", user);
		if (interest != null)
			requestJsonObject.put("interest", interest);
		if (sort != null)
			requestJsonObject.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_COUNT_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), requestJsonObject.toString());
		if (TextUtils.isEmpty(responseString)) {
			return 0;
		} else {
			return Integer.parseInt(responseString);
		}
	}

	/**
	 * Method to get Questions List by location
	 * 
	 * @param activity
	 * @param locationList
	 * @return List<Question>
	 * @throws Exception
	 */
	public static List<Question> getQuestionsByLocation(Activity activity,
			List<String> locationList) throws Exception {
		if (locationList == null || locationList.isEmpty()) {
			return null;
		}
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_LOCATIONS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), convertToJSON(locationList));
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to get Nearby Questions List
	 * 
	 * @param activity
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param date
	 * @param location
	 * @param perimeter
	 * @param sort
	 * @return List<Question>
	 * @throws Exception
	 */
	public static List<Question> getNearByQuestionsList(Activity activity,
			int offset, int limit, long fromTimestamp, long toTimestamp,
			String date, String location, int perimeter, String sort)
			throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		if (offset > 0)
			requestJsonObject.put("offset", offset);
		if (limit > 0)
			requestJsonObject.put("limit", limit);
		if (fromTimestamp > 0)
			requestJsonObject.put("from", fromTimestamp);
		if (toTimestamp > 0)
			requestJsonObject.put("to", toTimestamp);
		if (date != null)
			requestJsonObject.put("date", date);
		if (location != null)
			requestJsonObject.put("location", location);
		if (location != null && perimeter > 0)
			requestJsonObject.put("perimeter", perimeter);
		if (sort != null)
			requestJsonObject.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_NEARBY_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), requestJsonObject.toString());
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to get Question List by Interest
	 * 
	 * @param activity
	 * @param interest_id
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param sort
	 * @return List<Question>
	 * @throws Exception
	 */
	public static List<Question> getQuestionsByInterest(Activity activity,
			String interest_id, int offset, int limit, long fromTimestamp,
			long toTimestamp, String sort) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("interest_id", interest_id);
		if (offset > 0)
			requestJsonObject.put("offset", offset);
		if (limit > 0)
			requestJsonObject.put("limit", limit);
		if (fromTimestamp > 0)
			requestJsonObject.put("from", fromTimestamp);
		if (toTimestamp > 0)
			requestJsonObject.put("to", toTimestamp);
		if (sort != null)
			requestJsonObject.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_BY_INTEREST_SUFFIX
				+ interest_id;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), requestJsonObject.toString());
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to get Question List by User
	 * 
	 * @param activity
	 * @param user_id
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param sort
	 * @return List<Question>
	 * @throws Exception
	 */
	public static List<Question> getQuestionsByUser(Activity activity,
			String user_id, int offset, int limit, long fromTimestamp,
			long toTimestamp, String sort) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("user_id", user_id);
		if (offset > 0)
			requestJsonObject.put("offset", offset);
		if (limit > 0)
			requestJsonObject.put("limit", limit);
		if (fromTimestamp > 0)
			requestJsonObject.put("from", fromTimestamp);
		if (toTimestamp > 0)
			requestJsonObject.put("to", toTimestamp);
		if (sort != null)
			requestJsonObject.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.USER_QUESTIONS_SUFFIX
				+ user_id;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity), requestJsonObject.toString());
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to add new Interest
	 * 
	 * @param activity
	 * @param interest_name
	 * @param image
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean addNewInterests(Activity activity,
			String interest_name, String image) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("interest_name", interest_name);
		if (image != null)
			requestJsonObject.put("image", image);
		String serviceURL = getBaseURL() + APIURL.INTERESTS_SUFFIX;
		HTTPRequestHelper.processPostRequest(serviceURL,
				requestJsonObject.toString(), getAuthorizationToken(activity));
		if (HTTPRequestHelper.getResponseCode() == 200) {
			return true;
		}
		return false;

	}

	/**
	 * Method to get Interst List
	 * 
	 * @param activity
	 * @return List<Interest>
	 * @throws Exception
	 */
	public static List<Interest> getInteresetList(Activity activity)
			throws Exception {
		String serviceURL = getBaseURL() + APIURL.INTERESTS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity));
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Interest>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to convert Object to JSON
	 * 
	 * @param object
	 * @return String
	 */
	public static String convertToJSON(Object object) {
		final Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		Log.i(TAG, "convertToJSON : " + jsonString);
		return jsonString;
	}

	/**
	 * Method to convert JsonString to List Object
	 * 
	 * @param responseString
	 * @param typeOfT
	 * @return List<Object>
	 */
	public static <T> T convertToObject(String responseString, Type typeOfT) {
		Log.i(TAG, "convertToObject : " + responseString);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson.fromJson(responseString, typeOfT);
	}

	/**
	 * Method to convert JsonString to Object
	 * 
	 * @param responseString
	 * @param classOfT
	 * @return Object
	 */
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
