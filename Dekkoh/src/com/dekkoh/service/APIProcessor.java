package com.dekkoh.service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.text.TextUtils;

import com.dekkoh.application.DekkohApplication;
import com.dekkoh.datamodel.Answer;
import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.datamodel.DekkohUserConnection;
import com.dekkoh.datamodel.Interest;
import com.dekkoh.datamodel.Message;
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
	public static DekkohUser loginUserWithGoogle(Activity activity,
			String user_id, String token, String email, String objectId)
			throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("provider", "Google");
		requestJsonObject.put("user_id", user_id);
		requestJsonObject.put("token", token);
		requestJsonObject.put("email", email);
		if (objectId != null) {
			requestJsonObject.put("objectId", objectId);
		} else {
			requestJsonObject.put("objectId", "uPJgT5nXke");
		}
		Map<String, String> responseHeaderMap = new HashMap<String, String>();
		String serviceURL = getBaseURL() + APIURL.USER_LOGIN_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(), responseHeaderMap);
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {

			SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
					.getInstance(activity);
			sharedPreferenceManager.save(
					SharedPreferenceConstants.AUTHORIZATION_TOCKEN,
					responseHeaderMap.get("Token"));
			DekkohUser dekkohUser = convertToObject(responseString,
					DekkohUser.class);
			sharedPreferenceManager.save(
					SharedPreferenceConstants.DEKKOH_USER_ID,
					dekkohUser.getDekkohUserID());
			sharedPreferenceManager.save(
					SharedPreferenceConstants.DEKKOH_USER_EMAIL,
					dekkohUser.getEmail());
			sharedPreferenceManager.save(
					SharedPreferenceConstants.DEKKOH_USER_NAME,
					dekkohUser.getName());
			sharedPreferenceManager.save(
					SharedPreferenceConstants.DEKKOH_USER_PROFILEPIC,
					dekkohUser.getProfilePic());
			if (dekkohUser.getInterestIds().size() == 0) {
				sharedPreferenceManager.save(
						SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS,
						false);
				sharedPreferenceManager.save(
						SharedPreferenceConstants.DEKKOH_USER_INTERESTS, "");

			} else {
				List<DekkohUser.InterestID> listInterestId = dekkohUser
						.getInterestIds();
				String interestIdString = "";
				for (int i = 0; i < listInterestId.size(); i++) {
					interestIdString += listInterestId.get(i).getInterestID()
							+ ",";
				}
				if (interestIdString.length() > 0) {
					interestIdString = interestIdString.substring(0,
							interestIdString.length() - 1);
				}
				sharedPreferenceManager.save(
						SharedPreferenceConstants.DEKKOH_USER_INTERESTS,
						interestIdString);
				sharedPreferenceManager.save(
						SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS,
						true);
			}
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
	 * @param objectId
	 * @return DekkohUser
	 * @throws Exception
	 */
	public static DekkohUser loginUserWithFacebook(Activity activity,
			String user_id, String token, String objectId) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("provider", "Facebook");
		requestJsonObject.put("user_id", user_id);
		requestJsonObject.put("token", token);
		if (objectId != null) {
			requestJsonObject.put("objectId", objectId);
		} else {
			requestJsonObject.put("objectId", "uPJgT5nXke");
		}
		Map<String, String> responseHeaderMap = new HashMap<String, String>();
		String serviceURL = getBaseURL() + APIURL.USER_LOGIN_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(), responseHeaderMap);
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {

			SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
					.getInstance(activity);
			sharedPreferenceManager.save(
					SharedPreferenceConstants.AUTHORIZATION_TOCKEN,
					responseHeaderMap.get("Token"));
			DekkohUser dekkohUser = convertToObject(responseString,
					DekkohUser.class);
			sharedPreferenceManager.save(
					SharedPreferenceConstants.DEKKOH_USER_ID,
					dekkohUser.getDekkohUserID());
			sharedPreferenceManager.save(
					SharedPreferenceConstants.DEKKOH_USER_EMAIL,
					dekkohUser.getEmail());
			sharedPreferenceManager.save(
					SharedPreferenceConstants.DEKKOH_USER_NAME,
					dekkohUser.getName());
			if (dekkohUser.getInterestIds().size() == 0) {
				sharedPreferenceManager.save(
						SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS,
						false);
				sharedPreferenceManager.save(
						SharedPreferenceConstants.DEKKOH_USER_PROFILEPIC,
						dekkohUser.getProfilePic());
			} else {
				sharedPreferenceManager.save(
						SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS,
						true);
			}
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
	public static List<DekkohUser> getUsersList(Activity activity,
			String location, String role, String user, int offset, int limit,
			long fromTimestamp, long toTimestamp, String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("location", location);
		requestHeader.put("role", role);
		requestHeader.put("user", user);
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.USERS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<DekkohUser>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}

	}

	/**
	 * Method to get Users Count
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
	public static int getUserCount(Activity activity, String location,
			String role, String user, int offset, int limit,
			long fromTimestamp, long toTimestamp, String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("location", location);
		requestHeader.put("role", role);
		requestHeader.put("user", user);
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.USER_COUNT_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return 0;
		} else {
			return Integer.parseInt(responseString);
		}

	}

	/**
	 * Method to get user images
	 * 
	 * @param activity
	 * @param userIDArray
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public static HashMap<String, String> getUsersImages(Activity activity,
			String[] userIDArray) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put(
				"users",
				Arrays.toString(userIDArray).replaceAll("[", "")
						.replaceAll("]", ""));
		String serviceURL = getBaseURL() + APIURL.USER_IMAGES_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			HashMap<String, String> userImagesHashMap = new HashMap<String, String>();
			JSONObject responseJsonObject = new JSONObject(responseString);
			Iterator<String> iter = responseJsonObject.keys();
			while (iter.hasNext()) {
				String key = iter.next();
				String value = responseJsonObject.getString(key);
				userImagesHashMap.put(key, value);
			}
			return userImagesHashMap;
		}
	}

	/**
	 * Method to get user locations
	 * 
	 * @param activity
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public static String[] getUsersLocations(Activity activity)
			throws Exception {
		String serviceURL = getBaseURL() + APIURL.USER_LOCATION_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				getAuthorizationToken(activity));
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			JSONArray responseJsonArray = new JSONArray(responseString);
			String[] locationArray = new String[responseJsonArray.length()];
			for (int i = 0; i < responseJsonArray.length(); i++) {
				locationArray[i] = responseJsonArray.getString(i);
			}
			return locationArray;
		}
	}

	/**
	 * Method to update userProfile
	 * 
	 * @param activity
	 * @param user_id
	 * @param gender
	 * @param name
	 * @param home_city
	 * @param profile_pic
	 * @param home_neighbourhood
	 * @return DekkohUser
	 * @throws Exception
	 */
	public static DekkohUser updateUserProfile(Activity activity,
			String gender, String name, String home_city, String profile_pic,
			String home_neighbourhood) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("user_id", getUserId(activity));
		requestJsonObject.put("gender", gender);
		requestJsonObject.put("name", name);
		requestJsonObject.put("home_city", home_city);
		requestJsonObject.put("profile_pic", profile_pic);
		requestJsonObject.put("home_neighbourhood", home_neighbourhood);
		Map<String, String> responseHeaderMap = new HashMap<String, String>();
		String serviceURL = getBaseURL() + APIURL.USER_PROFILE_UPDATE_SUFFIX
				+ getUserId(activity);
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(), responseHeaderMap);
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
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
	 * Method to update User Interest
	 * 
	 * @param activity
	 * @param user_id
	 * @param interestIdArray
	 * @return DekkohUser
	 * @throws Exception
	 */
	public static DekkohUser updateUserInterest(Activity activity,
			String[] interestIdArray) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("user_id", getUserId(activity));
		requestJsonObject.put("interest_id", Arrays.toString(interestIdArray)
				.replaceAll("\"", ""));
		Map<String, String> responseHeaderMap = getJSONRequestHeader(getAuthorizationToken(activity));
		String serviceURL = getBaseURL() + APIURL.USER_INTEREST_UPDATE_SUFFIX
				+ getUserId(activity);
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(), responseHeaderMap);
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
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
	 * Method to return User Connections
	 * 
	 * @param activity
	 * @param user_id
	 * @return List<DekkohUserConnection>
	 * @throws Exception
	 */
	public static List<DekkohUserConnection> getUserConnections(
			Activity activity, String user_id) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.USER_CONNECTIONS_SUFFIX
				+ user_id;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<DekkohUserConnection>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}

	}

	/**
	 * Method to follow user connection
	 * 
	 * @param activity
	 * @param connection_id
	 * @param user_id
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean followUser(Activity activity, String connection_id,
			String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("connection_id", connection_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("connection_id", connection_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.FOLLOW_USER_SUFFIX
				+ connection_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				if (responseString.equalsIgnoreCase("true")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to unFollow user connection
	 * 
	 * @param activity
	 * @param connection_id
	 * @param user_id
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean unFollowUser(Activity activity, String connection_id,
			String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("connection_id", connection_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("connection_id", connection_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.UNFOLLOW_USER_SUFFIX
				+ connection_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				if (responseString.equalsIgnoreCase("true")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to return User Connections
	 * 
	 * @param activity
	 * @param user_id
	 * @return List<DekkohUserConnection>
	 * @throws Exception
	 */
	public static List<DekkohUserConnection> getUserFollowsConnections(
			Activity activity, String user_id) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.USER_FOLLOWS_SUFFIX + user_id;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<DekkohUserConnection>>() {
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
	public static Question postQuestion(Activity activity, String question,
			String location, String longitude, String latitude, String image,
			String interest_id, String user_image, String timestamp)
			throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("question", question);
		requestJsonObject.put("location", location);
		requestJsonObject.put("longitude", longitude);
		requestJsonObject.put("latitude", latitude);
		requestJsonObject.put("user_id", getUserId(activity));
		requestJsonObject.put("image", image);
		requestJsonObject.put("interest_id", interest_id);
		requestJsonObject.put("user_image", user_image);
		requestJsonObject.put("timestamp", timestamp);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(),
				getAuthorizationToken(activity));
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				return convertToObject(responseString, Question.class);
			}
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
	public static List<Question> getQuestions(Activity activity, int offset,
			int limit, long fromTimestamp, long toTimestamp, String date,
			String location, int perimeter, String keyword, Boolean unanswered,
			String user, String[] interest, String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(date))
			requestHeader.put("date", date);
		if (!TextUtils.isEmpty(location))
			requestHeader.put("location", location);
		if (!TextUtils.isEmpty(location) && perimeter > 0)
			requestHeader.put("perimeter", perimeter + "");
		if (!TextUtils.isEmpty(keyword))
			requestHeader.put("keyword", keyword);
		if (unanswered != null)
			requestHeader.put("unanswered", unanswered + "");
		if (!TextUtils.isEmpty(user))
			requestHeader.put("user", user);
		if (interest != null && interest.length > 0)
			requestHeader.put(
					"interest",
					Arrays.toString(interest).replaceAll("[", "")
							.replaceAll("]", ""));
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			List<Question> questionsList = convertToObject(responseString,
					listType);
			Collections.sort(questionsList, new Comparator<Question>() {

				@Override
				public int compare(Question object1, Question object2) {
					Date date1 = object1.getDate();
					Date date2 = object2.getDate();
					return date1.compareTo(date2);
				}
			});
			Collections.reverse(questionsList);
			return questionsList;
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
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(date))
			requestHeader.put("date", date);
		if (!TextUtils.isEmpty(location))
			requestHeader.put("location", location);
		if (!TextUtils.isEmpty(location) && perimeter > 0)
			requestHeader.put("perimeter", perimeter + "");
		if (!TextUtils.isEmpty(keyword))
			requestHeader.put("keyword", keyword);
		if (unanswered != null)
			requestHeader.put("unanswered", unanswered + "");
		if (!TextUtils.isEmpty(user))
			requestHeader.put("user", user);
		if (!TextUtils.isEmpty(interest))
			requestHeader.put("interest", interest);
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_COUNT_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return 0;
		} else {
			return Integer.parseInt(responseString);
		}
	}

	/**
	 * Method to get locations of Questions
	 * 
	 * @param activity
	 * @param locationList
	 * @return List<String>
	 * @throws Exception
	 */
	public static List<String> getQuestionsLocation(Activity activity,
			List<String> locationList) throws Exception {
		if (locationList == null || locationList.isEmpty()) {
			return null;
		}
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_LOCATIONS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<String>>() {
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
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(date))
			requestHeader.put("date", date);
		if (!TextUtils.isEmpty(location))
			requestHeader.put("location", location);
		if (!TextUtils.isEmpty(location) && perimeter > 0)
			requestHeader.put("perimeter", perimeter + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_NEARBY_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			List<Question> questionsList = convertToObject(responseString,
					listType);
			Collections.sort(questionsList, new Comparator<Question>() {

				@Override
				public int compare(Question object1, Question object2) {
					Date date1 = object1.getDate();
					Date date2 = object2.getDate();
					return date1.compareTo(date2);
				}
			});
			Collections.reverse(questionsList);
			return questionsList;
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
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("interest_id", interest_id);
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_BY_INTEREST_SUFFIX
				+ interest_id;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			List<Question> questionsList = convertToObject(responseString,
					listType);
			Collections.sort(questionsList, new Comparator<Question>() {

				@Override
				public int compare(Question object1, Question object2) {
					Date date1 = object1.getDate();
					Date date2 = object2.getDate();
					return date1.compareTo(date2);
				}
			});
			Collections.reverse(questionsList);
			return questionsList;
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
	public static List<Question> getUserQuestionList(Activity activity,
			int offset, int limit, long fromTimestamp, long toTimestamp,
			String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user_id", getUserId(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.USER_QUESTIONS_SUFFIX
				+ getUserId(activity);
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			List<Question> questionsList = convertToObject(responseString,
					listType);
			Collections.sort(questionsList, new Comparator<Question>() {

				@Override
				public int compare(Question object1, Question object2) {
					Date date1 = object1.getDate();
					Date date2 = object2.getDate();
					return date1.compareTo(date2);
				}
			});
			Collections.reverse(questionsList);
			return questionsList;
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
	public static List<Question> getUserHomeFeed(Activity activity, int offset,
			int limit) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user_id", getUserId(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		String serviceURL = getBaseURL() + APIURL.USER_FEED_SUFFIX
				+ getUserId(activity);
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			List<Question> questionsList = convertToObject(responseString,
					listType);
			Collections.sort(questionsList, new Comparator<Question>() {

				@Override
				public int compare(Question object1, Question object2) {
					Date date1 = object1.getDate();
					Date date2 = object2.getDate();
					return date1.compareTo(date2);
				}
			});
			Collections.reverse(questionsList);
			return questionsList;
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
	public static List<Question> getUserAnswersList(Activity activity,
			int offset, int limit, String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user_id", getUserId(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.USER_ANSWERS_SUFFIX
				+ getUserId(activity);
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to get Question Details
	 * 
	 * @param activity
	 * @param question_id
	 * @return Question
	 * @throws Exception
	 */
	public static Question getQuestionDetail(Activity activity,
			String question_id) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("question_id", question_id);
		String serviceURL = getBaseURL() + APIURL.QUESTIONS_DETAIL_SUFFIX
				+ question_id;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			return convertToObject(responseString, Question.class);
		}
	}

	/**
	 * Method to Follow a Question
	 * 
	 * @param activity
	 * @param question_id
	 * @param user_id
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean followQuestion(Activity activity, String question_id,
			String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("question_id", question_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("question_id", question_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.FOLLOW_QUESTION_SUFFIX
				+ question_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				if (responseString.equalsIgnoreCase("true")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to UnFollow a Question
	 * 
	 * @param activity
	 * @param question_id
	 * @param user_id
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean unFollowQuestion(Activity activity,
			String question_id, String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("question_id", question_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("question_id", question_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.UNFOLLOW_QUESTION_SUFFIX
				+ question_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				if (responseString.equalsIgnoreCase("true")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to flag a question
	 * 
	 * @param activity
	 * @param question_id
	 * @param user_id
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean flagQuestion(Activity activity, String question_id,
			String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("question_id", question_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("question_id", question_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.FLAG_QUESTION_SUFFIX
				+ question_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				if (responseString.equalsIgnoreCase("true")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to unflag a question
	 * 
	 * @param activity
	 * @param question_id
	 * @param user_id
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean unFlagQuestion(Activity activity, String question_id,
			String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("question_id", question_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("question_id", question_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.UNFLAG_QUESTION_SUFFIX
				+ question_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				if (responseString.equalsIgnoreCase("true")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to Check if a question has been flagged or not
	 * 
	 * @param activity
	 * @param question_id
	 * @param user_id
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean checkQuestionFlag(Activity activity,
			String question_id, String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("question_id", question_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("question_id", question_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.CHECK_QUESTION_FLAG_SUFFIX
				+ question_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				if (responseString.equalsIgnoreCase("true")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to get questions followed by a particular user
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
	public static List<Question> getUserFollowedQuestionList(Activity activity,
			int offset, int limit, long fromTimestamp, long toTimestamp,
			String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user_id", getUserId(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL()
				+ APIURL.USER_FOLLOWED_QUESTIONS_SUFFIX + getUserId(activity);
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Question>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to Post an answer
	 * 
	 * @param activity
	 * @param answer
	 * @param question_id
	 * @param user_id
	 * @param image
	 * @param location
	 * @param timestamp
	 * @return Answer
	 * @throws Exception
	 */
	public static Answer postAnswer(Activity activity, String answer,
			String question_id, String image, String location, String timestamp)
			throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("answer", answer);
		requestJsonObject.put("question_id", question_id);
		requestJsonObject.put("user_id", getUserId(activity));
		requestJsonObject.put("image", image);
		requestJsonObject.put("location", location);
		requestJsonObject.put("timestamp", timestamp);
		String serviceURL = getBaseURL() + APIURL.ANSWERS_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, getAuthorizationToken(activity),
				requestJsonObject.toString());
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			return convertToObject(responseString, Answer.class);
		}
	}

	/**
	 * Method to Get all the answers or only the answers which matches some
	 * criteria
	 * 
	 * @param activity
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param sort
	 * @param keyword
	 * @return List<Answer>
	 * @throws Exception
	 */
	public static List<Answer> getAnswers(Activity activity, int offset,
			int limit, long fromTimestamp, long toTimestamp, String sort,
			String keyword) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		if (!TextUtils.isEmpty(keyword))
			requestHeader.put("keyword", keyword);
		String serviceURL = getBaseURL() + APIURL.ANSWERS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Answer>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to Get all the answers count or only the answers count which
	 * matches some criteria
	 * 
	 * @param activity
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param sort
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public static int getAnswersCount(Activity activity, int offset, int limit,
			long fromTimestamp, long toTimestamp, String sort, String keyword)
			throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		if (!TextUtils.isEmpty(keyword))
			requestHeader.put("keyword", keyword);
		String serviceURL = getBaseURL() + APIURL.ANSWERS_COUNT_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return 0;
		} else {
			return Integer.parseInt(responseString);
		}
	}

	public static List<Answer> getAnswersWithQuestionID(Activity activity,
			String question_id, int offset, int limit, long fromTimestamp,
			long toTimestamp, String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("question_id", question_id);
		requestHeader.put("user_id", getUserId(activity));
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.ANSWERS_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Answer>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	/**
	 * Method to Like an answer
	 * 
	 * @param activity
	 * @param answer_id
	 * @param user_id
	 * @return int
	 * @throws Exception
	 */
	public static int likeAnswer(Activity activity, String answer_id,
			String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("answer_id", answer_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("answer_id", answer_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.LIKE_ANSWER_SUFFIX
				+ answer_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				return Integer.parseInt(responseString);
			}
		}
		return 0;
	}

	/**
	 * Method to unLike an answer
	 * 
	 * @param activity
	 * @param answer_id
	 * @param user_id
	 * @return int
	 * @throws Exception
	 */
	public static int unLikeAnswer(Activity activity, String answer_id,
			String user_id) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("answer_id", answer_id);
		requestJsonObject.put("user_id", user_id);
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("answer_id", answer_id);
		requestHeader.put("user_id", user_id);
		String serviceURL = getBaseURL() + APIURL.UNLIKE_ANSWER_SUFFIX
				+ answer_id + "/" + user_id;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestHeader, requestJsonObject.toString());
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
			if (!TextUtils.isEmpty(responseString)) {
				return Integer.parseInt(responseString);
			}
		}
		return 0;
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
		if (!TextUtils.isEmpty(image))
			requestJsonObject.put("image", image);
		String serviceURL = getBaseURL() + APIURL.INTERESTS_SUFFIX;
		HTTPRequestHelper.processPostRequest(serviceURL,
				requestJsonObject.toString(), getAuthorizationToken(activity));
		if (HTTPRequestHelper.getResponseCode() == 200
				|| HTTPRequestHelper.getResponseCode() == 201) {
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
	 * Method to Post a message
	 * 
	 * @param activity
	 * @param message
	 * @param recipients
	 * @return Message
	 * @throws Exception
	 */
	public static Message postMessage(Activity activity, String message,
			String[] recipients) throws Exception {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("message", message);
		requestJsonObject.put("recipients", Arrays.toString(recipients)
				.replaceAll("[", "").replaceAll("]", ""));
		String serviceURL = getBaseURL() + APIURL.MESSAGES_SUFFIX;
		String responseString = HTTPRequestHelper.processPostRequest(
				serviceURL, requestJsonObject.toString(),
				getAuthorizationToken(activity));
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			return convertToObject(responseString, Message.class);
		}
	}

	/**
	 * Method to Get all messages
	 * 
	 * @param activity
	 * @param user
	 * @param offset
	 * @param limit
	 * @param fromTimestamp
	 * @param toTimestamp
	 * @param sort
	 * @return List<Message>
	 * @throws Exception
	 */
	public static List<Message> getMessages(Activity activity, String user,
			int offset, int limit, long fromTimestamp, long toTimestamp,
			String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user", user);
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.MESSAGES_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Message>>() {
			}.getType();
			return convertToObject(responseString, listType);
		}
	}

	public static List<Message> getMessagesCount(Activity activity,
			String user, int offset, int limit, long fromTimestamp,
			long toTimestamp, String sort) throws Exception {
		Map<String, String> requestHeader = getJSONRequestHeader(getAuthorizationToken(activity));
		requestHeader.put("user", user);
		if (offset > 0)
			requestHeader.put("offset", offset + "");
		if (limit > 0)
			requestHeader.put("limit", limit + "");
		if (fromTimestamp > 0)
			requestHeader.put("from", fromTimestamp + "");
		if (toTimestamp > 0)
			requestHeader.put("to", toTimestamp + "");
		if (!TextUtils.isEmpty(sort))
			requestHeader.put("sort", sort);
		String serviceURL = getBaseURL() + APIURL.MESSAGES_COUNT_SUFFIX;
		String responseString = HTTPRequestHelper.processGetRequest(serviceURL,
				requestHeader);
		if (TextUtils.isEmpty(responseString)) {
			return null;
		} else {
			Type listType = new TypeToken<List<Message>>() {
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
		return "Token token=\""
				+ SharedPreferenceManager.getInstance(activity).getString(
						SharedPreferenceConstants.AUTHORIZATION_TOCKEN) + "\"";
	}

	public static String getBaseURL() {
		if (Constants.PRODUCTION) {
			return APIURL.PROD_SERVICE_URL;
		} else {
			return APIURL.QA_SERVICE_URL;
		}
	}

	public static Map<String, String> getJSONRequestHeader(
			String authorizationToken) {
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");
		requestHeaders.put("Content-type", "application/json");
		if (authorizationToken != null) {
			requestHeaders.put("Authorization", authorizationToken);
		}
		return requestHeaders;
	}

	public static Map<String, String> getJSONRequestHeader() {
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");
		requestHeaders.put("Content-type", "application/json");
		return requestHeaders;
	}

	public static String getUserId(Activity activity) {
		SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
				.getInstance(activity);
		return sharedPreferenceManager
				.getString(SharedPreferenceConstants.DEKKOH_USER_ID);
	}
}
