package com.dekkoh.service;

import com.dekkoh.util.Constants;

public final class APIConstants {
	public static final int TIME_OUT = 60000;
	public static final String PROPERTY_JSON = "json";

	public static final class APIRequestAction {

	}

	public static final class APIRequestKey {
	}

	public static final class APIResponseKey {

	}

	public static final class APIURL {
		public static String BASE_URL = getBaseURL();

		public static final String GROUP_INTERESTS_URL = BASE_URL
				+ "/interests";

		private static String getBaseURL() {
			if (Constants.PRODUCTION) {
				return "";
			} else {
				return "";
			}
		}

	}
}
