package com.ml.galaxy.entities;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("betasoide")
public class Betasoide extends Planet {
	
	private static final long serialVersionUID = 7731585916173922632L;

	public Betasoide() {
		super(Betasoide.class.getSimpleName(), TypePlanet.BETASOIDE);
	}
}
