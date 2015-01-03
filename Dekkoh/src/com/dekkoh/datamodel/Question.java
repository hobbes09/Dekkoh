package com.dekkoh.datamodel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Question {
	protected int answer_count;
	protected String follow_count;
	protected String coordinates;
	protected String created_at;
	protected String delete_flg;
	protected String image;
	protected String interest_name;
	protected String location;
	protected String question;
	protected String updated_at;
	protected String user_image;
	protected String user_name;
	protected InterestId interest_id = new InterestId();
	protected UserId user_id = new UserId();
	protected QuestionId _id = new QuestionId();
	protected List<Follower> followers = new ArrayList<Follower>();

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
	public String getFollow_count() {
		return follow_count;
	}

	/**
	 * @param follow_count
	 *            the follow_count to set
	 */
	public void setFollow_count(String follow_count) {
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
	public String getUser_image() {
		return user_image;
	}

	/**
	 * @param user_image
	 *            the user_image to set
	 */
	public void setUser_image(String user_image) {
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

	class InterestId {
		@SerializedName("$oid")
		protected String interestId;

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

	class QuestionId {
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
}
