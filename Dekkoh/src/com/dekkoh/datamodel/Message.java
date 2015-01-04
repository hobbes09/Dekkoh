package com.dekkoh.datamodel;

import com.google.gson.annotations.SerializedName;

public class Message {
	private String created_at;
	private String updated_at;
	private String message;
	private String[] recipients;
	private int star_count;
	private MessageID _id;

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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the star_count
	 */
	public int getStarCount() {
		return star_count;
	}

	/**
	 * @param star_count
	 *            the star_count to set
	 */
	public void setStarCount(int star_count) {
		this.star_count = star_count;
	}

	/**
	 * @return the messageID
	 */
	public String getMessageID() {
		return _id.getMessageID();
	}

	/**
	 * @param messageID
	 *            the messageID to set
	 */
	public void setMessageID(String messageID) {
		this._id.setMessageID(messageID);
	}

	/**
	 * @return the recipients
	 */
	public String[] getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients
	 *            the recipients to set
	 */
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	class MessageID {
		@SerializedName("$oid")
		private String messageID;

		/**
		 * @return the messageID
		 */
		public String getMessageID() {
			return messageID;
		}

		/**
		 * @param messageID
		 *            the messageID to set
		 */
		public void setMessageID(String messageID) {
			this.messageID = messageID;
		}
	}
}
