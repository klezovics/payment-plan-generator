package com.klezovich.payment_plan_generator.domain;

import java.util.Date;

public class PaymentPlanGenerator {

	private LoanData ld;
	private double annuity;

	private double interest;

	public PaymentPlanGenerator(LoanData ld) {
		this.ld = ld;
		this.annuity = calculateAnnuity(ld.getNominalRate(), (double)ld.getLoanAmount(), ld.getDuration() );
	}

	public PaymentPlan getPaymentPlan() {

		PaymentPlan pp = new PaymentPlan();

		//System.out.println("Annuity:" + annuity);

		double initialOutstandingPrinciple = (double) ld.getLoanAmount();

		for (int monthNum = 1; monthNum <= ld.getDuration(); monthNum++) {

			double interest = calculateInterest(initialOutstandingPrinciple, ld.getNominalRate());
			double principal = calculatePrincipal(initialOutstandingPrinciple, interest, this.annuity);
			double remainingOutstandingPrincipal = initialOutstandingPrinciple - principal;

			PaymentPlan.MonthlyPaymentDetail mpd = new PaymentPlan.MonthlyPaymentDetail();

			mpd.calcPaymentDate(ld.getStartDate(), monthNum);
			mpd.setPaymentAmount(principal + interest);
			mpd.setPrincipal(principal);
			mpd.setInterest(interest);
			mpd.setInitialOutstandingPrincipal(initialOutstandingPrinciple);
			mpd.setRemainingOutstandingPrincipal(remainingOutstandingPrincipal);

			pp.addMonthlyPayment(mpd);

			initialOutstandingPrinciple = remainingOutstandingPrincipal;
		}

		return pp;
	}

	private double calculatePrincipal(double initialOutstandingPrinciple, double interest, double annuity) {

		double principal = 0;
		
		if (interest > initialOutstandingPrinciple)
			principal = initialOutstandingPrinciple;
		else
			principal = annuity - interest;

		return principal;
	}

	private double calculateInterest(double initialOutstandingPrinciple, double nominalRate) {
		return nominalRate * 30 * initialOutstandingPrinciple / 360;
	}

	private double calculateAnnuity( double nominalRate, double loanAmount, int duration) {

		//  nominalRate is for a year and the formula below requires a rate per month
		double ratePerPeriod = (double) (nominalRate) / 12;

		// Annuity calculation formula taken from here -
		// http://financeformulas.net/Annuity_Payment_Formula.html
		return (ratePerPeriod * loanAmount) / (1 - Math.pow(1 + ratePerPeriod, -duration ) );
	}

}
