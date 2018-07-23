package com.ml.galaxy.entities;

public enum Wise {
	// Sentido antihorario tomado como positivo.
	COUNTER_CLOCKWISE(1),
	CLOCKWISE(-1);
	
	private int value;
	
	private Wise(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
