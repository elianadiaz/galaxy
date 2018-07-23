package com.ml.galaxy.job;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ml.galaxy.entities.ClimaticCondition;
import com.ml.galaxy.entities.TypeClimate;
import com.ml.galaxy.exceptions.GalaxyJobException;
import com.ml.galaxy.exceptions.ValidationException;
import com.ml.galaxy.services.IGalaxyService;

@Component
public class GalaxyJob implements IGalaxyJob {

	private static final Logger log = LoggerFactory.getLogger(GalaxyJob.class);
	
	@Autowired
	private IGalaxyService galaxyService;		

	@Override
	public Map<TypeClimate, ClimaticCondition> executeJob() throws ValidationException, GalaxyJobException {
		// 10 years (1 year = 365 days)
		
		ExecutorService executor = Executors.newWorkStealingPool();
		List<Callable<Map<TypeClimate, ClimaticCondition>>> callables = Arrays.asList(
			    callable(1, 365),
			    callable(365 + 1, 365*2),
			    callable(365 * 2 + 1, 365 * 3),
			    callable(365 * 3 + 1, 365 * 4),
			    callable(365 * 4 + 1, 365 * 5),
			    callable(365 * 5 + 1, 365 * 6),
			    callable(365 * 6 + 1, 365 * 7),
			    callable(365 * 7 + 1, 365 * 8),
			    callable(365 * 8 + 1, 365 * 9),
			    callable(365 * 9 + 1, 365 * 10)			    
				);
		
		Map<TypeClimate, ClimaticCondition> climaticConditions = null;
		List<Future<Map<TypeClimate, ClimaticCondition>>> futures = null;
		try {
			futures = executor.invokeAll(callables);
		    
		    executor.shutdown();
		} catch (InterruptedException e) {
			throw new GalaxyJobException("An error occurred during the execution of the task: " + e.getMessage());			
		} finally {
		    if (!executor.isTerminated() && futures == null) {
		    	executor.shutdownNow();
		    } else {
		    	climaticConditions = new HashMap<TypeClimate, ClimaticCondition>();
		    	
		    	for (Future<Map<TypeClimate, ClimaticCondition>> future : futures) {
		    		// Llamar al metodo get() bloquea el hilo actual y espera hasta que el invocable finalice
		    		try {
						Map<TypeClimate, ClimaticCondition> oneClimaticCondition = future.get();
						
						for (TypeClimate oneTypeClimateFuture : oneClimaticCondition.keySet()) {
							ClimaticCondition climaticConditionFuture = oneClimaticCondition.get(oneTypeClimateFuture);
							ClimaticCondition climaticCondition = climaticConditions.get(oneTypeClimateFuture);
							if (climaticCondition == null) {
								climaticConditions.put(oneTypeClimateFuture, climaticConditionFuture);
							} else {
								// Actualizo el que ya esta guardo con los nuevos valores.
								climaticCondition.addPeriods(climaticConditionFuture.getPeriods());
								if (climaticConditionFuture.getRainIntensity() > climaticCondition.getRainIntensity()) {
									climaticCondition.setPeakDayOfRain(climaticConditionFuture.getPeakDayOfRain());
									climaticCondition.setRainIntensity(climaticConditionFuture.getRainIntensity());
								}								
							}
						}
						
					} catch (InterruptedException e) {
						log.warn("Error al obtener un futuro: {}.", e.getMessage());
					} catch (ExecutionException e) {
						log.warn("Error al obtener un futuro: {}.", e.getMessage());
					}
				}		    	
		    }
		}
		
		return climaticConditions;
	}
	
	private Callable<Map<TypeClimate, ClimaticCondition>> callable(int dayFrom, int dayUntil) {
	    return () -> {
	        return galaxyService.getClimaticConditions(dayFrom, dayUntil);
	    };
	}
}
