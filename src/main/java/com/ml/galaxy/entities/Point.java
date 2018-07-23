package com.ml.galaxy.entities;

import java.io.Serializable;

import com.ml.galaxy.exceptions.PointException;

public class Point implements Serializable {
	
	private static final long serialVersionUID = 3966597196613955038L;

	private Double x;
	private Double y;
	
	public Point(Double x, Double y) throws PointException {
		super();
		setX(x);
		setY(y);
	}

	public Double getX() {
		return x;
	}
	
	public void setX(Double x) throws PointException {
		if (x == null) {
			throw new PointException();
		}
		
		this.x = x;
	}
	
	public Double getY() {
		return y;
	}
	
	public void setY(Double y) throws PointException {
		if (y == null) {
			throw new PointException();
		}
		
		this.y = y;
	}
}
