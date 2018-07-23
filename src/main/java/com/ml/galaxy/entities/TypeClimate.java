package com.ml.galaxy.entities;

public enum TypeClimate {
	DROUGHT("Drought"),
	RAIN("Rain"),
	OPTIMAL_CONDITIONS("Optimal conditions"),
	NONE("None");
	
	private String value;
	
	private TypeClimate(String value) {
		this.value = value;		
	}

	public String getValue() {
		return value;
	}	
}
