package com.ml.galaxy.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ml.galaxy.entities.ClimaticCondition;
import com.ml.galaxy.entities.TypeClimate;
import com.ml.galaxy.exceptions.GalaxyJobException;
import com.ml.galaxy.exceptions.ValidationException;
import com.ml.galaxy.exceptions.entities.ErrorMessage;
import com.ml.galaxy.job.IGalaxyJob;
import com.ml.galaxy.services.IGalaxyService;

@RestController
@RequestMapping("/clima")
public class ClimaticConditionController {

	@Autowired
	private IGalaxyService galaxyService;
	
	@Autowired
	private IGalaxyJob galaxyJob;
	
	@GetMapping("/")
	public ResponseEntity<?> getClima(@RequestParam(value="day") Integer day) {
		if (day == null) {
			day = -1;
		}
		
		try {
			Map<TypeClimate, ClimaticCondition> climaticCondition = galaxyService.getClimaticConditions(day, day);
			ClimaticCondition response = null;
			
			// Tiene que venir un unico elemento o ninguno.
			if (climaticCondition == null || climaticCondition.isEmpty()) {
				response = new ClimaticCondition();
				response.setTypeClimate(TypeClimate.NONE);
				response.setPeriods(null);
			} else {
				for (TypeClimate typeClimate : climaticCondition.keySet()) {
					response = climaticCondition.get(typeClimate);
				}
			}
			
			return ResponseEntity.ok(response);
		} catch (ValidationException ex) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setHttpStatus(HttpStatus.BAD_REQUEST.value());
			errorMessage.setCode(ex.getCode());
	        errorMessage.setMessage(ex.getMessage());
	        
			return ResponseEntity.badRequest().body(errorMessage);
		}				
	}
	
	@GetMapping("/prediction")
	public ResponseEntity<?> getPrediction() {
		try {
			Map<TypeClimate, ClimaticCondition> climaticCondition = galaxyJob.executeJob();
			
			return ResponseEntity.ok(climaticCondition);
		} catch (ValidationException ex) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setHttpStatus(HttpStatus.BAD_REQUEST.value());
			errorMessage.setCode(ex.getCode());
	        errorMessage.setMessage(ex.getMessage());
	        
			return ResponseEntity.badRequest().body(errorMessage);
		} catch (GalaxyJobException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setHttpStatus(HttpStatus.BAD_REQUEST.value());
			errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
	        errorMessage.setMessage(e.getMessage());
	        
			return ResponseEntity.badRequest().body(errorMessage);
		}				
	}
	
}
