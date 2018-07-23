package com.ml.galaxy.entities;

import java.io.Serializable;

import com.ml.galaxy.exceptions.PointException;

public abstract class Planet implements Serializable {
	
	private static final long serialVersionUID = 8014984320228068090L;

	private String name;
	private TypePlanet typePlanet;
	
	public Planet(String name, TypePlanet typePlanet) {
		super();
		this.name = name;
		this.typePlanet = typePlanet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypePlanet getTypePlanet() {
		return typePlanet;
	}

	public void setTypePlanet(TypePlanet typePlanet) {
		this.typePlanet = typePlanet;
	}
	
	public double getAngularSpeedDegreesPerDay() {
		return this.typePlanet.getAngularSpeedDegreesPerDay();
	}

	public Wise getWise() {
		return this.typePlanet.getWise();
	}

	public double getDistanceToSun() {
		return this.typePlanet.getDistanceToSun();
	}
	
    public Point getPosicion(int day) throws PointException {
    	// http://www.mathpages.com/home/kmath161/kmath161.htm
        double x = this.getDistanceToSun() * Math.cos(getAngle(day));
        double y = this.getDistanceToSun() * Math.sin(getAngle(day));
        
        return new Point(x,y);
    }
    
    private double getAngle(int dia){		
        double degrees = (this.getWise().getValue() * dia * this.getAngularSpeedDegreesPerDay());
        return Math.toRadians(degrees);
    }
}
