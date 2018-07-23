package com.ml.galaxy.services;

import java.util.Map;

import com.ml.galaxy.entities.ClimaticCondition;
import com.ml.galaxy.entities.TypeClimate;
import com.ml.galaxy.exceptions.ValidationException;

public interface IGalaxyService {

	Map<TypeClimate, ClimaticCondition> getClimaticConditions(int dayFrom, int dayUntil) throws ValidationException;
}
