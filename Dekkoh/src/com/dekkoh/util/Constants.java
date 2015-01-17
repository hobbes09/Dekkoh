package com.dekkoh.util;

public class Constants {
	public static final boolean PRODUCTION = false;

	public static class SharedPreferenceConstants {
		public static final String AUTHORIZATION_TOCKEN = "AUTHORIZATION_TOCKEN";
		public static final String DEKKOH_USER_ID = "DEKKOH_USER_ID";
		public static final String DEKKOH_USER_EMAIL = "DEKKOH_USER_EMAIL";
		public static final String DEKKOH_USER_NAME = "DEKKOH_USER_NAME";
		public static final String DEKKOH_USER_HAVE_INTERESTS = "DEKKOH_USER_HAVE_INTERESTS";;
		public static final String DEKKOH_USER_PROFILEPIC = "DEKKOH_USER_PROFILEPIC";
		public static final String DEKKOH_USER_INTERESTS = "DEKKOH_USER_INTERESTS";
	}

	public static class DekkohExceptionErrorCodes {
		public static final String DEFAULT_ERROR_CODE = "E001";
		public static final String NETWOR_ERROR_CODE = "E002";
		public static final String NETWOR_TIMEOUT_CODE = "E009";
	}
}
