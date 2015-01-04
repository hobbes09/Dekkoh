package com.dekkoh.datamodel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Question {
	private int answer_count;
	private int follow_count;
	private String coordinates;
	private String created_at;
	private String image;
	private String interest_name;
	private String location;
	private String question;
	private String updated_at;
	private String user_image;
	private String user_name;
	private boolean delete_flg;
	private InterestId interest_id = new InterestId();
	private UserId user_id = new UserId();
	private QuestionId _id = new QuestionId();
	private List<Follower> followers = new ArrayList<Follower>();

	/**
	 * @return the answer_count
	 */
	public int getAnswerCount() {
		return answer_count;
	}

	/**
	 * @param answer_count
	 *            the answer_count to set
	 */
	public void setAnswerCount(int answer_count) {
		this.answer_count = answer_count;
	}

	/**
	 * @return the follow_count
	 */
	public int getFollowCount() {
		return follow_count;
	}

	/**
	 * @param follow_count
	 *            the follow_count to set
	 */
	public void setFollowCount(int follow_count) {
		this.follow_count = follow_count;
	}

	/**
	 * @return the created_at
	 */
	public String getCreatedAt() {
		return created_at;
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreatedAt(String created_at) {
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
	 * @return the interest_name
	 */
	public String getInterestName() {
		return interest_name;
	}

	/**
	 * @param interest_name
	 *            the interest_name to set
	 */
	public void setInterestName(String interest_name) {
		this.interest_name = interest_name;
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
	public String getUpdatedAt() {
		return updated_at;
	}

	/**
	 * @param updated_at
	 *            the updated_at to set
	 */
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * @return the user_name
	 */
	public String getUserName() {
		return user_name;
	}

	/**
	 * @param user_name
	 *            the user_name to set
	 */
	public void setUserName(String user_name) {
		this.user_name = user_name;
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

	/**
	 * @return the coordinates
	 */
	public String getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates
	 *            the coordinates to set
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the user_image
	 */
	public String getUserImage() {
		return user_image;
	}

	/**
	 * @param user_image
	 *            the user_image to set
	 */
	public void setUserImage(String user_image) {
		this.user_image = user_image;
	}

	/**
	 * @return the followers
	 */
	public List<Follower> getFollowers() {
		return followers;
	}

	/**
	 * @param followers
	 *            the followers to set
	 */
	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
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

	class Follower {
		private String name = "";
		private String profile_pic_url = "";
		private FollowerId _id = new FollowerId();

		/**
		 * @return the followerId
		 */
		public String getFollowerId() {
			return _id.getFollowerId();
		}

		/**
		 * @param followerId
		 *            the followerId to set
		 */
		public void setFollowerId(String followerId) {
			this._id.setFollowerId(followerId);
		}

		/**
		 * @return the profile_pic_url
		 */
		public String getProfile_pic_url() {
			return profile_pic_url;
		}

		/**
		 * @param profile_pic_url
		 *            the profile_pic_url to set
		 */
		public void setProfile_pic_url(String profile_pic_url) {
			this.profile_pic_url = profile_pic_url;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		class FollowerId {
			@SerializedName("$oid")
			private String followerId = "";

			/**
			 * @return the followerId
			 */
			public String getFollowerId() {
				return followerId;
			}

			/**
			 * @param followerId
			 *            the followerId to set
			 */
			public void setFollowerId(String followerId) {
				this.followerId = followerId;
			}

		}

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Question other = (Question) obj;
		if (this.getQuestionId() != other.getQuestionId()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 47 * hash + this.getQuestionId().hashCode();
		return hash;
	}
}
