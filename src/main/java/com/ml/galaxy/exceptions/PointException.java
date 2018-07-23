package com.ml.galaxy.exceptions;

public class PointException extends Exception {
	
	private static final long serialVersionUID = 8094444465841637736L;

	public PointException() {
		super("Invalid point.");
	}
	
	public PointException(String msg) {
		super(msg);
	}
}
