package com.ml.galaxy.job;

import java.util.Map;

import com.ml.galaxy.entities.ClimaticCondition;
import com.ml.galaxy.entities.TypeClimate;
import com.ml.galaxy.exceptions.GalaxyJobException;
import com.ml.galaxy.exceptions.ValidationException;

public interface IGalaxyJob {

	Map<TypeClimate, ClimaticCondition> executeJob() throws ValidationException, GalaxyJobException;

}
