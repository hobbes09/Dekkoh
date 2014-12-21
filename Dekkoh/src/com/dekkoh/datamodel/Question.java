package com.dekkoh.datamodel;

import com.google.gson.annotations.SerializedName;

public class Question {
	private int answer_count;
	private int follow_count;
	private String created_at;
	private String delete_flg;
	private String image;
	private String interest_name;
	private String latitude;
	private String location;
	private String longitude;
	private String question;
	private String updated_at;
	private String user_name;
	private InterestId interest_id = new InterestId();
	private UserId user_id = new UserId();
	private QuestionId _id = new QuestionId();

	/**
	 * @return the answer_count
	 */
	public int getAnswer_count() {
		return answer_count;
	}

	/**
	 * @param answer_count
	 *            the answer_count to set
	 */
	public void setAnswer_count(int answer_count) {
		this.answer_count = answer_count;
	}

	/**
	 * @return the follow_count
	 */
	public int getFollow_count() {
		return follow_count;
	}

	/**
	 * @param follow_count
	 *            the follow_count to set
	 */
	public void setFollow_count(int follow_count) {
		this.follow_count = follow_count;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the delete_flg
	 */
	public String getDelete_flg() {
		return delete_flg;
	}

	/**
	 * @param delete_flg
	 *            the delete_flg to set
	 */
	public void setDelete_flg(String delete_flg) {
		this.delete_flg = delete_flg;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the interest_name
	 */
	public String getInterest_name() {
		return interest_name;
	}

	/**
	 * @param interest_name
	 *            the interest_name to set
	 */
	public void setInterest_name(String interest_name) {
		this.interest_name = interest_name;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}

	/**
	 * @param updated_at
	 *            the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name
	 *            the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return the interest_id
	 */
	public InterestId getInterest_id() {
		return interest_id;
	}

	/**
	 * @param interest_id
	 *            the interest_id to set
	 */
	public void setInterest_id(InterestId interest_id) {
		this.interest_id = interest_id;
	}

	/**
	 * @return the user_id
	 */
	public UserId getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id
	 *            the user_id to set
	 */
	public void setUser_id(UserId user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the _id
	 */
	public QuestionId get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(QuestionId _id) {
		this._id = _id;
	}

	/**
	 * @return the interestId
	 */
	public String getInterestId() {
		return interest_id.getInterestId();
	}

	/**
	 * @param interestId
	 *            the interestId to set
	 */
	public void setInterestId(String interestId) {
		this.interest_id.setInterestId(interestId);
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return user_id.getUserId();
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.user_id.setUserId(userId);
	}

	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return _id.getQuestionId();
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this._id.setQuestionId(questionId);
	}

	class InterestId {
		@SerializedName("$oid")
		private String interestId;

		/**
		 * @return the interestId
		 */
		public String getInterestId() {
			return interestId;
		}

		/**
		 * @param interestId
		 *            the interestId to set
		 */
		public void setInterestId(String interestId) {
			this.interestId = interestId;
		}
	}

	class UserId {
		@SerializedName("$oid")
		private String userId;

		/**
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * @param userId
		 *            the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}

	}

	class QuestionId {
		@SerializedName("$oid")
		private String questionId;

		/**
		 * @return the questionId
		 */
		public String getQuestionId() {
			return questionId;
		}

		/**
		 * @param questionId
		 *            the questionId to set
		 */
		public void setQuestionId(String questionId) {
			this.questionId = questionId;
		}
	}
}
