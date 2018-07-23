package com.ml.galaxy.exceptions;

public class GalaxyJobException extends Exception {
	
	private static final long serialVersionUID = 3847054585572723157L;

	public GalaxyJobException() {
		super("An error occurred during the execution of the task.");
	}
	
	public GalaxyJobException(String msg) {
		super(msg);
	}
}
