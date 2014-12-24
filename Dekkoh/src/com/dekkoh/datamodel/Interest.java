package com.dekkoh.datamodel;

import com.google.gson.annotations.SerializedName;

public class Interest {
	private String created_at;
	private String delete_flg;
	private String image_url;
	private String interest_name;
	private String updated_at;
	private InterestID _id = new InterestID();

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
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}

	/**
	 * @param image_url
	 *            the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
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
