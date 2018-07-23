package com.ml.galaxy.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClimaticCondition implements Serializable {
	
	private static final long serialVersionUID = -8483187278925450114L;

	private TypeClimate typeClimate;
	private Integer peakDayOfRain;
	private Double rainIntensity;
	private List<Integer> periods;
	
	public ClimaticCondition() {
		super();
	}

	public TypeClimate getTypeClimate() {
		return typeClimate;
	}

	public void setTypeClimate(TypeClimate typeClimate) {
		this.typeClimate = typeClimate;
	}

	public Integer getQuantityPeriods() {
		return (periods == null ? 0 : periods.size());
	}

	public Integer getPeakDayOfRain() {
		return peakDayOfRain;
	}

	public void setPeakDayOfRain(Integer peakDayOfRain) {
		this.peakDayOfRain = peakDayOfRain;
	}

	public Double getRainIntensity() {
		return rainIntensity;
	}

	public void setRainIntensity(Double rainIntensity) {
		this.rainIntensity = rainIntensity;
	}
	
	public void setPeriods(List<Integer> periods) {
		this.periods = periods;
	}

	public List<Integer> getPeriods() {
		return periods;
	}

	public void addPeriod(int period) {
		if (this.periods == null) {
			this.periods = new ArrayList<Integer>();
		}
		
		this.periods.add(period);
	}
	
	public void addPeriods(List<Integer> periods) {
		if (periods == null || periods.isEmpty()) {
			return ;
		}
		
		if (this.periods == null) {
			this.periods = new ArrayList<Integer>();
		}
		
		this.periods.addAll(periods);
	}
}
