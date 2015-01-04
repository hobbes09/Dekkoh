package com.dekkoh.datamodel;

import com.google.gson.annotations.SerializedName;

public class Interest {
	private String created_at;
	private String image_url;
	private String interest_name;
	private String updated_at;
	private boolean delete_flg;
	private InterestID _id = new InterestID();

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
	 * @return the image_url
	 */
	public String getImageUrl() {
		return image_url;
	}

	/**
	 * @param image_url
	 *            the image_url to set
	 */
	public void setImageUrl(String image_url) {
		this.image_url = image_url;
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
	 * @return the interestID
	 */
	public String getInterestID() {
		return _id.getInterestID();
	}

	/**
	 * @param interestID
	 *            the interestID to set
	 */
	public void setInterestID(String interestID) {
		this._id.setInterestID(interestID);
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

	class InterestID {
		@SerializedName("$oid")
		private String interestID;

		/**
		 * @return the interestID
		 */
		public String getInterestID() {
			return interestID;
		}

		/**
		 * @param interestID
		 *            the interestID to set
		 */
		public void setInterestID(String interestID) {
			this.interestID = interestID;
		}

	}
}
