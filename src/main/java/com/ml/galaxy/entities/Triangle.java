package com.ml.galaxy.entities;

import com.ml.galaxy.exceptions.StraightException;
import com.ml.galaxy.exceptions.TriangleException;

public class Triangle {

	private Point pointA;
	private Point pointB;
	private Point pointC;
	
	public Triangle(Point pointA, Point pointB, Point pointC) throws TriangleException {
		super();
		
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
		
		validatePoints();
	}

	private void validatePoints() throws TriangleException {
		if (this.pointA == null || this.pointB == null || this.pointC == null) {
			throw new TriangleException();
		}
				
		try {
			Straight straight = new Straight(this.pointA, this.pointB);
			if (straight.belongsToTheStraight(this.pointC)) {
				throw new TriangleException();
			}
		} catch (StraightException e) {
			throw new TriangleException();
		}		
	}
	
	public double getPerimeter() {
		return calculateDistance(this.pointA, this.pointB) 
				+ calculateDistance(this.pointB, this.pointC) 
				+ calculateDistance(this.pointC, this.pointA);
	}
	
	private double calculateDistance(Point p1, Point p2){
        return Math.sqrt(Math.pow((p2.getX() - p1.getX()),2) + Math.pow((p2.getY() - p1.getY()),2));
    }
	
	public boolean isInsideTheTriangle(Point point) {	
		boolean b1 = sign(point, this.pointA, this.pointB) < 0;
		boolean b2 = sign(point, this.pointB, this.pointC) < 0;
	    boolean b3 = sign(point, this.pointC, this.pointA) < 0;

	    return ((b1 == b2) && (b2 == b3));	   
	}
	
	public boolean isOnATriangleEdge(Point point) throws StraightException {
		Straight straightAB = new Straight(this.pointA, this.pointB);
		Straight straightBC = new Straight(this.pointB, this.pointC);
		Straight straightCA = new Straight(this.pointC, this.pointA);
		
		return (straightAB.belongsToTheStraight(point) 
				|| straightBC.belongsToTheStraight(point) 
				|| straightCA.belongsToTheStraight(point));
	}
	
	private double sign(Point point1, Point point2, Point point3) {
		return (point1.getX() - point3.getX()) * (point2.getY() - point3.getY()) 
				- (point2.getX() - point3.getX()) * (point1.getY() - point3.getY());
	}
}
