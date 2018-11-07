package com.klezovich.payment_plan_generator.domain;

public class LoanDataValidator {

	boolean valid;
	String error;
	LoanData ld;
	
	public LoanDataValidator( LoanData ld ) {
		this.ld = ld;
	}
	
	public boolean validate() {
		boolean valid = true;
		
	
		if( ld.getLoanAmount() < 0 ) {
		   this.error = "Loan amount cannot be negative. Supplied value:" + ld.getLoanAmount();
		   this.valid = false;
		   return this.valid;
		}
		
		if( ld.getNominalRate() < 0 ) {
			this.error = "Nominal rate cannot be negative. Supplied value" + ld.getNominalRate();
			this.valid = false;
			return this.valid;
		}
		
		if( ld.getDuration() <= 0 ) {
			this.error = "The duration must be 1 or more. Supplied value" + ld.getDuration();
			this.valid = false;
			return this.valid;
		}
		
		
		this.valid = true;
		return this.valid;
	}
	
	
	
	public String getErrorMsg() {
		return error;
	}
	
}
