package com.ml.galaxy.exceptions;

public class ValidationException extends Exception {
	
	private static final long serialVersionUID = 7883684268249257666L;

	private int code;
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(ValidationMessage msg) {
		super(msg.getMessage());
		this.code = msg.getCode();
	}
	
	public int getCode() {
		return code;
	}
}
