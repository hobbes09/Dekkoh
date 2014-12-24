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
		public static final String QUESTIONS_LOCATIONS_SUFFIX = "/questions/locations";
		public static final String QUESTIONS_NEARBY_SUFFIX = "/questions/nearby";
		public static final String QUESTIONS_COUNT_SUFFIX = "/questions/count";
		public static final String QUESTIONS_BY_INTEREST_SUFFIX = "/questions_by_interest/";
		public static final String USER_QUESTIONS_SUFFIX = "/user/my_questions/";
		public static final String USER_FEED_SUFFIX = "/user/feed/";
		public static final String USER_ANSWERS_SUFFIX = "/user/my_answers/";
		public static final String USER_FOLLOWED_QUESTIONS_SUFFIX = "/questions/followed/";
		public static final String QUESTIONS_DETAIL_SUFFIX = "/questions/";
		public static final String FOLLOW_QUESTION_SUFFIX = "/question/follow/";
		public static final String UNFOLLOW_QUESTION_SUFFIX = "/question/unfollow/";
		public static final String FLAG_QUESTION_SUFFIX = "/question/flag/";
		public static final String UNFLAG_QUESTION_SUFFIX = "/question/unflag/";
		public static final String LIKE_ANSWER_SUFFIX = "/user/like_answer/";
		public static final String UNLIKE_ANSWER_SUFFIX = "/user/unlike_answer/";
		public static final String GROUP_ANSWERS_SUFFIX = "/answers";
		public static final String QUESTION_ANSWERS_SUFFIX = "/question_answers/";
		public static final String GROUP_MESSAGES_SUFFIX = "/messages";
		public static final String STAR_MESSAGES_SUFFIX = "/message/star/";
		public static final String MESSAGE_DETAILS_SUFFIX = "/messages/";
		public static final String USER_LOGIN_SUFFIX = "/user/login";
		public static final String USER_SUFFIX = "/users";
		public static final String USER_PROFILE_UPDATE_SUFFIX = "/user/profile_update/";
		public static final String USER_INTEREST_UPDATE_SUFFIX = "/user/interest_update/";
		public static final String USER_INTERESETS_SUFFIX = "/user/interests/";
		public static final String USER_CONNECTIONS_SUFFIX = "/user/connections/";
		public static final String FOLLOW_USER_SUFFIX = "/user/follow/";
		public static final String UNFOLLOW_USER_SUFFIX = "/user/unfollow/";
		public static final String USER_FOLLOWS_SUFFIX = "/user/follows/";
		public static final String USER_ALERTS_SUFFIX = "/users/##/alerts";
		public static final String ADMIN_SIGNUP_SUFFIX = "/admin/signup";
		public static final String ADMIN_SIGNIN_SUFFIX = "/admin/signin";
		public static final String DELETE_ALL_QUESTION_SUFFIX = "/question/delete_all";
		public static final String DELETE_ALL_ANSWER_SUFFIX = "/answer/delete_all";
		public static final String DELETE_ALL_USER_SUFFIX = "/user_del/delete_all";
	}
}
