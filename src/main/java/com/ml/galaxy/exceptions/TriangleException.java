package com.ml.galaxy.exceptions;

public class TriangleException extends Exception {

	private static final long serialVersionUID = -7295546822689240122L;

	public TriangleException() {
		super("Invalid triangle.");
	}
	
	public TriangleException(String msg) {
		super(msg);
	}
}
