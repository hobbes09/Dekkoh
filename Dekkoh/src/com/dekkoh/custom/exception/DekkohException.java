package com.dekkoh.custom.exception;

import java.io.Serializable;

public class DekkohException extends RuntimeException implements Serializable {

	private String errorCode;

	private String errorMsg;

	public DekkohException(String errorCode) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = "Default error message";
	}

	public DekkohException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
