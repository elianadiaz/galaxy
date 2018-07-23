package com.ml.galaxy.exceptions;

public enum ValidationMessage {
	INVALID_RANGE(1, "");
	
	private int code;
	private String message;
	
	private ValidationMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
