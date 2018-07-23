package com.ml.galaxy.entities;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("ferengi")
public class Ferengi extends Planet {
	
	private static final long serialVersionUID = 7538999607221300794L;

	public Ferengi() {
		super(Ferengi.class.getSimpleName(), TypePlanet.FERENGI);
	}

}
