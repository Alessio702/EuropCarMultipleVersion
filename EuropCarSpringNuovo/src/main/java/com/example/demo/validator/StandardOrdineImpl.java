package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StandardOrdineImpl implements ConstraintValidator<StandardOrdine, String>{
	
	private String ordPrefix;
	
	@Override 
	public void initialize(StandardOrdine std) {
		ordPrefix = std.errore();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean ordine = false;
		if(value != null) {
			ordine = ordPrefix.equals(value.substring(0, 4));
		}
		return ordine;
	}
	
	

}
