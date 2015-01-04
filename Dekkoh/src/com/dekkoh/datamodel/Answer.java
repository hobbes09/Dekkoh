package com.dekkoh.datamodel;

import com.google.gson.annotations.SerializedName;

public class Answer {
	private String answer;
	private String created_at;
	private String image;
	private String like_count;
	private String location;
	private String updated_at;
	private String user_name;
	private boolean liked;
	private boolean delete_flg;
	private AnswerId _id;
	private UserId user_id;
	private QusestionId question_id;

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
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
	 * @return the like_count
	 */
	public String getLike_count() {
		return like_count;
	}

	/**
	 * @param like_count
	 *            the like_count to set
	 */
	public void setLike_count(String like_count) {
		this.like_count = like_count;
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

	public String getQuestionId() {
		return question_id.getQuestionId();
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.question_id.setQuestionId(questionId);
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
	 * @return the answerId
	 */
	public String getAnswerId() {
		return _id.getAnswerId();
	}

	/**
	 * @param answerId
	 *            the answerId to set
	 */
	public void setAnswerId(String answerId) {
		this._id.setAnswerId(answerId);
	}

	/**
	 * @return the delete_flg
	 */
	public boolean isDeleted() {
		return delete_flg;
	}

	/**
	 * @param delete_flg
	 *            the delete_flg to set
	 */
	public void setDeleted(boolean delete_flg) {
		this.delete_flg = delete_flg;
	}

	/**
	 * @return the liked
	 */
	public boolean isLiked() {
		return liked;
	}

	/**
	 * @param liked
	 *            the liked to set
	 */
	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	class QusestionId {
		@SerializedName("$oid")
		protected String questionId;

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

	class UserId {
		@SerializedName("$oid")
		protected String userId;

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

	class AnswerId {
		@SerializedName("$oid")
		protected String answerId;

		/**
		 * @return the answerId
		 */
		public String getAnswerId() {
			return answerId;
		}

		/**
		 * @param answerId
		 *            the answerId to set
		 */
		public void setAnswerId(String answerId) {
			this.answerId = answerId;
		}

	}

}
