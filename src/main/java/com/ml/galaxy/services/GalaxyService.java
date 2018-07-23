package com.ml.galaxy.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ml.galaxy.entities.ClimaticCondition;
import com.ml.galaxy.entities.Planet;
import com.ml.galaxy.entities.Point;
import com.ml.galaxy.entities.Straight;
import com.ml.galaxy.entities.Triangle;
import com.ml.galaxy.entities.TypeClimate;
import com.ml.galaxy.exceptions.PointException;
import com.ml.galaxy.exceptions.StraightException;
import com.ml.galaxy.exceptions.TriangleException;
import com.ml.galaxy.exceptions.ValidationException;

@Service
public class GalaxyService implements IGalaxyService {
	
	@Autowired
    @Qualifier("betasoide")
	private Planet betasoide;

	@Autowired
    @Qualifier("ferengi")
	private Planet ferengi;

	@Autowired
    @Qualifier("vulcano")
	private Planet vulcano;
	
	@Override
	public Map<TypeClimate, ClimaticCondition> getClimaticConditions(int dayFrom, int dayUntil) throws ValidationException {		
		if (dayFrom > dayUntil || dayFrom < 0 || dayUntil < 0) {
			throw new ValidationException("Invalid day range.");
		}
		
		Map<TypeClimate, ClimaticCondition> climaticConditions = new HashMap<TypeClimate, ClimaticCondition>();
		Point pointOfSun = null;
		for (; dayFrom <= dayUntil; dayFrom++) {			
			try {
				/*
				 * Cuando los tres planetas estan alineados entre si y a su vez alineados con respecto al Sol, el
				 * sistema solar experimenta un periodo de sequia.
				 */
				if (meetNonalignmentConditions(dayFrom, betasoide, ferengi, vulcano, true)) {
					ClimaticCondition drought = climaticConditions.get(TypeClimate.DROUGHT);
					
					if (drought == null) {
						drought = new ClimaticCondition();
						drought.setTypeClimate(TypeClimate.DROUGHT);
						climaticConditions.put(TypeClimate.DROUGHT, drought);
					}
					
					drought.addPeriod(dayFrom);
					
					continue;
				}
				
				/*
				 * Las condiciones optimas de presion y temperatura se dan cuando los tres planetas estan
				 * alineados entre si pero no estan alineados con el Sol.
				 */
				if (meetNonalignmentConditions(dayFrom, betasoide, ferengi, vulcano, false)) {
					ClimaticCondition optimalConditions = climaticConditions.get(TypeClimate.OPTIMAL_CONDITIONS);
					
					if (optimalConditions == null) {
						optimalConditions = new ClimaticCondition();
						optimalConditions.setTypeClimate(TypeClimate.OPTIMAL_CONDITIONS);
						climaticConditions.put(TypeClimate.OPTIMAL_CONDITIONS, optimalConditions);
					}
					
					optimalConditions.addPeriod(dayFrom);
					
					continue;
				}
				
				/*
				 * Cuando los tres planetas no estan alineados, forman entre si un triangulo. Es sabido que en el
				 * momento en el que el sol se encuentra dentro del triangulo, el sistema solar experimenta un
				 * periodo de lluvia, teniendo este, un pico de intensidad cuando el perimetro del triangulo esta en
				 * su maximo.
				 */
				
				// El Sol se encuentra en el centro del sistema de referencia, o sea, en el (0,0).
				if (pointOfSun == null) {
					try {
						pointOfSun = new Point(0D, 0D);
					} catch (PointException e1) {
						throw new ValidationException("There was a problem getting the coordinates where the Sun is located.");
					}
				}
				
				PeriodOfRain periodOfRain = null;
				try {
					periodOfRain = getPeriodOfRain(dayFrom, betasoide, ferengi, vulcano, pointOfSun);
				} catch (TriangleException e) {
					// El triangulo formado para el dia X es incorrecto. Puede no ser un triangulo.
					continue ;
				}
				
				if (periodOfRain != null && periodOfRain.isRaining()) {
					ClimaticCondition rain = climaticConditions.get(TypeClimate.RAIN);
					
					if (rain == null) {
						rain = new ClimaticCondition();
						rain.setTypeClimate(TypeClimate.RAIN);						
						climaticConditions.put(TypeClimate.RAIN, rain);
					}
					
					rain.addPeriod(dayFrom);
					if (rain.getRainIntensity() == null || periodOfRain.getRainIntensity() > rain.getRainIntensity()) {
						rain.setPeakDayOfRain(dayFrom);
						rain.setRainIntensity(periodOfRain.getRainIntensity());
					}									
				}				
			} catch (PointException e) {
				throw new ValidationException("A planet contains incorrect coordinates.");
			} catch (StraightException e) {
				throw new ValidationException("Can't create a line correctly.");
			}			
		}
		
		return climaticConditions;
	}
	
	private boolean meetNonalignmentConditions(int day, Planet planetA, Planet planetB, Planet planetC, 
			boolean mustGoThroughTheSun) throws PointException, StraightException {
		Point pointA = planetA.getPosicion(day);
		Point pointB = planetB.getPosicion(day);
		
		Straight straight = new Straight(pointA, pointB);
		
		boolean passesThroughTheOriginOfCoordinates = straight.passesThroughTheOriginOfCoordinates();
		if ((mustGoThroughTheSun && !passesThroughTheOriginOfCoordinates)
				|| (!mustGoThroughTheSun && passesThroughTheOriginOfCoordinates)) {
			return false;
		}
		
		// Verifico si el otro planeta esta en la recta
		if (straight.belongsToTheStraight(planetC.getPosicion(day))) {
			return true;
		}
		
		return false;
	}
	
	private PeriodOfRain getPeriodOfRain(int day, Planet planetA, Planet planetB, Planet planetC, Point pointOfSun) 
			throws TriangleException, PointException {
		Triangle triangle = new Triangle(planetA.getPosicion(day), planetB.getPosicion(day), planetC.getPosicion(day));
		
		PeriodOfRain periodOfRain = new PeriodOfRain();
		// Verifico si el Sol se encuentra dentro del triangulo.
		if (!triangle.isInsideTheTriangle(pointOfSun)) {
			periodOfRain.setRaining(false);
			
			return periodOfRain;
		}
		
		periodOfRain.setRaining(true);
		periodOfRain.setRainIntensity(triangle.getPerimeter());
		
		return periodOfRain;
	}
	
	private class PeriodOfRain {
		private boolean isRaining;
		private double rainIntensity;
		
		public PeriodOfRain() {
			super();
			this.rainIntensity = 0;
		}

		public boolean isRaining() {
			return isRaining;
		}

		public void setRaining(boolean isRaining) {
			this.isRaining = isRaining;
		}

		public double getRainIntensity() {
			return rainIntensity;
		}

		public void setRainIntensity(double rainIntensity) {
			this.rainIntensity = rainIntensity;
		}
	}
}
