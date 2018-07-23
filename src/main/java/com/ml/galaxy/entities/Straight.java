package com.ml.galaxy.entities;

import com.ml.galaxy.exceptions.PointException;
import com.ml.galaxy.exceptions.StraightException;

public class Straight {

	private Double slope;
	private Double intercept;
	
	public Straight(Point pointA, Point pointB) throws StraightException {
		super();
		
		validatePoints(pointA, pointB);
		buildStraight(pointA, pointB);
	}
	
	public Straight(double slope, double intercept) throws StraightException {
		super();
		setSlope(slope);
		setIntercept(intercept);
	}
	
	private void validatePoints(Point pointA, Point pointB) throws StraightException {
		if (pointA == null || pointB == null) {
			throw new StraightException();
		}
		
		if (pointA.getX() == pointB.getX() && pointA.getY() == pointB.getY()) {
			throw new StraightException();
		}
	}

	private void buildStraight(Point pointA, Point pointB) {
		this.slope = (pointA.getY() - pointB.getY()) / (pointA.getX() - pointB.getX());
		this.intercept = pointA.getY() - (this.slope * pointA.getX());
	}

	public Double getSlope() {
		return slope;
	}

	public void setSlope(Double slope) throws StraightException {
		if (slope == null) {
			throw new StraightException();
		}
		
		this.slope = slope;
	}

	public Double getIntercept() {
		return intercept;
	}

	public void setIntercept(Double intercept) throws StraightException {
		if (intercept == null) {
			throw new StraightException();
		}
		
		this.intercept = intercept;
	}
	
	public boolean passesThroughTheOriginOfCoordinates() {
		try {
			return belongsToTheStraight(new Point(0D, 0D));
		} catch (PointException e) {
			// Nothing
		}
		
		return false;
	}
	
	public boolean belongsToTheStraight(Point point) {
		return (point.getY() == ((this.slope * point.getX()) + this.intercept));
	}
}
