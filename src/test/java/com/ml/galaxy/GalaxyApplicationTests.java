package com.ml.galaxy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ml.galaxy.entities.ClimaticCondition;
import com.ml.galaxy.entities.TypeClimate;
import com.ml.galaxy.exceptions.ValidationException;
import com.ml.galaxy.services.IGalaxyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GalaxyApplicationTests {

	@Autowired
	private IGalaxyService galaxyService;
	
	@Test
	public void answerCallIndividualSuccessfulTest() {
		boolean hasError = false;
		try {
			Map<TypeClimate, ClimaticCondition> climaticCondition = galaxyService.getClimaticConditions(566, 566);
			if (climaticCondition == null || climaticCondition.size() != 1) {
				hasError = true;
			} else {
				assertNotNull(climaticCondition.get(TypeClimate.RAIN));				
			}			
		} catch (ValidationException e) {
			hasError = true;
		}
		
		assertFalse(hasError);		
	}

}
