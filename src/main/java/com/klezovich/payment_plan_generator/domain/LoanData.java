package com.klezovich.payment_plan_generator.domain;

import java.text.ParseException;
import java.util.Date;

import com.klezovich.payment_plan_generator.util.AppDateFormatter;

public class LoanData {

	private int loanAmount;
	// 5% is stored like 0.05, 21% is stored like 0.21
	private double nominalRate;
	private int duration;
	private Date startDate;
	
	
	public LoanData(int loanAmount, double nominalRate, int duration, String dateStr ) throws ParseException {
		super();
		
		AppDateFormatter adf = new AppDateFormatter();
		
		this.loanAmount = loanAmount;
		this.nominalRate = nominalRate;
		this.duration = duration;
		this.startDate = adf.getFormatter().parse(dateStr);
	}
	
	public LoanData(int loanAmount, double nominalRate, int duration, Date startDate) {
		super();
		this.loanAmount = loanAmount;
		this.nominalRate = nominalRate;
		this.duration = duration;
		this.startDate = startDate;
	}
	
	
	
	@Override
	public String toString() {
		return "LoanData [loanAmount=" + loanAmount + ", nominalRate=" + nominalRate + "%, duration=" + duration
				+ " months, startDate=" + startDate + "]";
	}



	public int getLoanAmount() {
		return loanAmount;
	}



	public void setLoanAmount(int loanAmount) {
		this.loanAmount = loanAmount;
	}



	public double getNominalRate() {
		return nominalRate;
	}



	public void setNominalRate(double nominalRate) {
		this.nominalRate = nominalRate;
	}



	public int getDuration() {
		return duration;
	}



	public void setDuration(int duration) {
		this.duration = duration;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	
	
}
