package com.ml.galaxy.exceptions;

public class StraightException extends Exception {
	
	private static final long serialVersionUID = 6366675530143658224L;

	public StraightException() {
		super("Invalid straight.");
	}
	
	public StraightException(String msg) {
		super(msg);
	}
}
