package com.klezovich.payment_plan_generator.domain;

public class LoanDataValidationResult {

	boolean valid;
	String error;
	LoanData ld;
	
	public LoanDataValidationResult( LoanData ld ) {
		this.ld = ld;
	}
	
	public boolean validate() {
		boolean valid = true;
		
		this.valid = valid;
		return this.valid;
	}
	
	public String getErrorMsg() {
		return error;
	}
	
}
