package com.dekkoh.service;

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
		public static final String QA_SERVICE_URL = "http://104.131.64.246:3000";
		public static final String PROD_SERVICE_URL = "http://104.131.64.246:3000";

		public static final String INTERESTS_SUFFIX = "/interests";
		public static final String QUESTIONS_SUFFIX = "/questions";
		public static final String QUESTION_SUFFIX = "/question";
		public static final String ANSWERS_SUFFIX = "/answers";
		public static final String ANSWER_SUFFIX = "/answer";
		public static final String USERS_SUFFIX = "/users";
		public static final String USER_SUFFIX = "/user";

		public static final String QUESTIONS_BY_INTEREST_SUFFIX = "/questions_by_interest/";
		public static final String QUESTION_ANSWERS_SUFFIX = "/question_answers/";
		public static final String MESSAGES_SUFFIX = "/messages";
		public static final String STAR_MESSAGES_SUFFIX = "/message/star/";
		public static final String MESSAGE_DETAILS_SUFFIX = "/messages/";
		public static final String ADMIN_SIGNUP_SUFFIX = "/admin/signup";
		public static final String ADMIN_SIGNIN_SUFFIX = "/admin/signin";
		public static final String MESSAGES_COUNT_SUFFIX = MESSAGES_SUFFIX
				+ "/count";
		public static final String QUESTIONS_LOCATIONS_SUFFIX = QUESTIONS_SUFFIX
				+ "/locations";
		public static final String QUESTIONS_NEARBY_SUFFIX = QUESTIONS_SUFFIX
				+ "/nearby";
		public static final String QUESTIONS_COUNT_SUFFIX = QUESTIONS_SUFFIX
				+ "/count";
		public static final String USER_LOGIN_SUFFIX = USER_SUFFIX + "/login";
		public static final String USER_COUNT_SUFFIX = USERS_SUFFIX + "/count";
		public static final String USER_QUESTIONS_SUFFIX = USER_SUFFIX
				+ "/my_questions/";
		public static final String USER_FEED_SUFFIX = USER_SUFFIX + "/feed/";
		public static final String USER_ANSWERS_SUFFIX = USER_SUFFIX
				+ "/my_answers/";
		public static final String USER_FOLLOWED_QUESTIONS_SUFFIX = QUESTIONS_SUFFIX
				+ "/followed/";
		public static final String QUESTIONS_DETAIL_SUFFIX = QUESTIONS_SUFFIX
				+ "/";
		public static final String FOLLOW_QUESTION_SUFFIX = QUESTION_SUFFIX
				+ "/follow/";
		public static final String UNFOLLOW_QUESTION_SUFFIX = QUESTION_SUFFIX
				+ "/unfollow/";
		public static final String FLAG_QUESTION_SUFFIX = QUESTION_SUFFIX
				+ "/flag/";
		public static final String UNFLAG_QUESTION_SUFFIX = QUESTION_SUFFIX
				+ "/unflag/";
		public static final String CHECK_QUESTION_FLAG_SUFFIX = QUESTIONS_SUFFIX
				+ "/flag/check/";
		public static final String LIKE_ANSWER_SUFFIX = USER_SUFFIX
				+ "/like_answer/";
		public static final String UNLIKE_ANSWER_SUFFIX = USER_SUFFIX
				+ "/unlike_answer/";
		public static final String ANSWERS_COUNT_SUFFIX = ANSWERS_SUFFIX
				+ "/count";
		public static final String DELETE_ALL_QUESTION_SUFFIX = QUESTION_SUFFIX
				+ "/delete_all";
		public static final String USER_IMAGES_SUFFIX = USERS_SUFFIX
				+ "/images";
		public static final String USER_LOCATION_SUFFIX = USERS_SUFFIX
				+ "/locations";
		public static final String USER_PROFILE_UPDATE_SUFFIX = USER_SUFFIX
				+ "/profile_update/";
		public static final String USER_INTEREST_UPDATE_SUFFIX = USER_SUFFIX
				+ "/interest_update/";
		public static final String USER_INTERESETS_SUFFIX = USER_SUFFIX
				+ "/interests/";
		public static final String USER_CONNECTIONS_SUFFIX = USER_SUFFIX
				+ "/connections/";
		public static final String FOLLOW_USER_SUFFIX = USER_SUFFIX
				+ "/follow/";
		public static final String UNFOLLOW_USER_SUFFIX = USER_SUFFIX
				+ "/unfollow/";
		public static final String USER_FOLLOWS_SUFFIX = USER_SUFFIX
				+ "/follows/";
		public static final String USER_ALERTS_SUFFIX = USERS_SUFFIX
				+ "/##/alerts";
	}
}
