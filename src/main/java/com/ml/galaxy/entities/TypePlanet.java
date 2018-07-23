package com.ml.galaxy.entities;

public enum TypePlanet {
	FERENGI(1, Wise.CLOCKWISE, 500),
	BETASOIDE(3, Wise.CLOCKWISE, 2000),
	VULCANO(5, Wise.COUNTER_CLOCKWISE, 1000);
	
	private double angularSpeedDegreesPerDay;
	private Wise wise;
	private double distanceToSun;
	
	private TypePlanet(double angularSpeedDegreesPerDay, Wise wise, double distanceToSun) {
		this.angularSpeedDegreesPerDay = angularSpeedDegreesPerDay;
		this.wise = wise;
		this.distanceToSun = distanceToSun;
	}

	public double getAngularSpeedDegreesPerDay() {
		return angularSpeedDegreesPerDay;
	}

	public Wise getWise() {
		return wise;
	}

	public double getDistanceToSun() {
		return distanceToSun;
	}
}
