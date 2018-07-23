package com.ml.galaxy.entities;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("vulcano")
public class Vulcano extends Planet {

	private static final long serialVersionUID = -2644628309038576999L;

	public Vulcano() {
		super(Vulcano.class.getSimpleName(), TypePlanet.VULCANO);
	}

}
