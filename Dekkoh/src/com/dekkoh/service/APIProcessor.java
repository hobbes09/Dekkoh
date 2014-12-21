package com.dekkoh.service;

import java.lang.reflect.Type;

import com.dekkoh.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APIProcessor {
	private static final String TAG = "APIProcessor";

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
}
