package com.dekkoh.datamodel;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DekkohUser implements Serializable{
	private String created_at;
	private String e_mail;
	private String gender;
	private String home_city;
	private String home_neighbourhood;
	private String name;
	private String password_digest;
	private String profile_pic;
	private String provider;
	private String provider_id;
	private String role;
	private String updated_at;
	private boolean delete_flg;
	private List<InterestID> interest_ids;
	private DekkohUserID _id = new DekkohUserID();

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
	 * @return the e_mail
	 */
	public String getEmail() {
		return e_mail;
	}

	/**
	 * @param e_mail
	 *            the e_mail to set
	 */
	public void setEmail(String e_mail) {
		this.e_mail = e_mail;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the home_city
	 */
	public String getHomeCity() {
		return home_city;
	}

	/**
	 * @param home_city
	 *            the home_city to set
	 */
	public void setHomeCity(String home_city) {
		this.home_city = home_city;
	}

	/**
	 * @return the home_neighbourhood
	 */
	public String getHomeNeighbourhood() {
		return home_neighbourhood;
	}

	/**
	 * @param home_neighbourhood
	 *            the home_neighbourhood to set
	 */
	public void setHomeNeighbourhood(String home_neighbourhood) {
		this.home_neighbourhood = home_neighbourhood;
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

	/**
	 * @return the password_digest
	 */
	public String getPassword_digest() {
		return password_digest;
	}

	/**
	 * @param password_digest
	 *            the password_digest to set
	 */
	public void setPassword_digest(String password_digest) {
		this.password_digest = password_digest;
	}

	/**
	 * @return the profile_pic
	 */
	public String getProfilePic() {
		return profile_pic;
	}

	/**
	 * @param profile_pic
	 *            the profile_pic to set
	 */
	public void setProfilePic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the provider_id
	 */
	public String getProviderId() {
		return provider_id;
	}

	/**
	 * @param provider_id
	 *            the provider_id to set
	 */
	public void setProviderId(String provider_id) {
		this.provider_id = provider_id;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
	 * @return the dekkohUserID
	 */
	public String getDekkohUserID() {
		return this._id.getDekkohUserID();
	}

	/**
	 * @param dekkohUserID
	 *            the dekkohUserID to set
	 */
	public void setDekkohUserID(String dekkohUserID) {
		this._id.setDekkohUserID(dekkohUserID);
	}

	/**
	 * @return the interest_ids
	 */
	public List<InterestID> getInterestIds() {
		return interest_ids;
	}

	/**
	 * @param interest_ids
	 *            the interest_ids to set
	 */
	public void setInterestIds(List<InterestID> interest_ids) {
		this.interest_ids = interest_ids;
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

	class DekkohUserID {
		@SerializedName("$oid")
		private String dekkohUserID;

		/**
		 * @return the dekkohUserID
		 */
		public String getDekkohUserID() {
			return dekkohUserID;
		}

		/**
		 * @param dekkohUserID
		 *            the dekkohUserID to set
		 */
		public void setDekkohUserID(String dekkohUserID) {
			this.dekkohUserID = dekkohUserID;
		}

	}

	public class InterestID {
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DekkohUser other = (DekkohUser) obj;
		if (this.getDekkohUserID() != other.getDekkohUserID()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 47 * hash + this.getDekkohUserID().hashCode();
		return hash;
	}
}
