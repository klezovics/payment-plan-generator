package com.klezovich.payment_plan_generator.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.klezovich.payment_plan_generator.util.AppDateFormatter;
import com.klezovich.payment_plan_generator.util.AppDoubleFormatter;
import com.klezovich.payment_plan_generator.domain.PaymentPlan;

public class PaymentPlan {
	
	private List<MonthlyPaymentDetail> monthlyPayments = new LinkedList<MonthlyPaymentDetail>();
	
	public static class MonthlyPaymentDetail{
		
		private Date paymentDate;
		private double paymentAmount; 
		private double principal; 
		private double interest; 
		private double initialOutstandingPrincipal;
		private double remainingOutstandingPrincipal;
		
		public boolean equals(Object o) {

			if (o == this) {
				return true;
			}

			if (!(o instanceof PaymentPlan.MonthlyPaymentDetail)) {
				return false;
			}

			PaymentPlan.MonthlyPaymentDetail mpd = (PaymentPlan.MonthlyPaymentDetail) o;

			if (!mpd.getPaymentDate().equals(this.paymentDate))
				return false;

			if (mpd.getPaymentAmount() - this.paymentAmount >= 0.01 )
				return false;

			if (mpd.getPrincipal() - this.principal >= 0.01 )
				return false;

			if (mpd.getInterest() - this.interest >= 0.01 )
				return false;

			if (mpd.getInitialOutstandingPrincipal() - this.initialOutstandingPrincipal >= 0.01 )
				return false;

			if (mpd.getRemainingOutstandingPrincipal() - this.remainingOutstandingPrincipal >= 0.01 )
				return false;

			return true;
		}

		
		public String toCSV() {
			
			AppDateFormatter dateFormat = new AppDateFormatter();
			AppDoubleFormatter df = new AppDoubleFormatter(); 
			
			return dateFormat.format(paymentDate) +","+df.format(paymentAmount)+","+df.format(principal)+","+df.format(interest)+","+df.format(initialOutstandingPrincipal)+","+df.format(Math.abs(remainingOutstandingPrincipal));
		}
		
		
		
		@Override
		public String toString() {
			
			AppDateFormatter dateFormatter = new AppDateFormatter();
			AppDoubleFormatter df = new AppDoubleFormatter(); 

			
			return "MonthlyPaymentDetail "
					+ "["
					+ "paymentDate=" + dateFormatter.format(paymentDate) + ", "
					+ "paymentAmount=" + df.format(paymentAmount) + ", "
					+ "principal=" + df.format(principal) + ", "
					+ "interest=" + df.format(interest) + ", "
					+ "initialOutstandingPrincipal="+ df.format(initialOutstandingPrincipal) + ", "
					// Math.abs() is used here, because occassionally during the last payment 
					// remainingOutstandingPrincipal is something like 10^-11, but with a minus sign. 
					// This is the easies way to remove it. 
					+ "remainingOutstandingPrincipal=" + df.format( Math.abs(remainingOutstandingPrincipal) )
					+ "]";
		}
		
		
		public void calcPaymentDate( Date startDate, int monthNum ) {
			
			LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			startLocalDate = startLocalDate.plusMonths(monthNum);
			Date date = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

			this.paymentDate = date;
		}
		
		public Date getPaymentDate() {
			return paymentDate;
		}
		public void setPaymentDate(Date paymentDate) {
			this.paymentDate = paymentDate;
		}
		public double getPaymentAmount() {
			return paymentAmount;
		}
		public void setPaymentAmount(double paymentAmount) {
			this.paymentAmount = paymentAmount;
		}
		public double getPrincipal() {
			return principal;
		}
		public void setPrincipal(double principal) {
			this.principal = principal;
		}
		public double getInterest() {
			return interest;
		}
		public void setInterest(double interest) {
			this.interest = interest;
		}
		public double getInitialOutstandingPrincipal() {
			return initialOutstandingPrincipal;
		}
		public void setInitialOutstandingPrincipal(double initialOutstandingPrincipal) {
			this.initialOutstandingPrincipal = initialOutstandingPrincipal;
		}
		public double getRemainingOutstandingPrincipal() {
			return remainingOutstandingPrincipal;
		}
		public void setRemainingOutstandingPrincipal(double remainingOutstandingPrincipal) {
			this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
		}
		
		
		
	}
	
	
	public void addMonthlyPayment( MonthlyPaymentDetail mpd ) {
		monthlyPayments.add(mpd);
	}
	
	public MonthlyPaymentDetail getPaymentForMonth( int monthNum ) {
		return monthlyPayments.get(monthNum-1);
	}
	
	public List<MonthlyPaymentDetail> getMonthlyPayments(){
		return monthlyPayments;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( MonthlyPaymentDetail mpd : monthlyPayments ) {
			sb.append(mpd);
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof PaymentPlan)) {
			return false;
		}

		PaymentPlan pp = (PaymentPlan) o;

		List<PaymentPlan.MonthlyPaymentDetail> mpdList = pp.getMonthlyPayments();

		int mpdListLen = mpdList.size();
		int thisMpdListLen = this.monthlyPayments.size();

		if (mpdListLen != thisMpdListLen)
			return false;

		for (int monthNum = 1; monthNum < mpdListLen; monthNum++) {

			PaymentPlan.MonthlyPaymentDetail mpd = mpdList.get(monthNum - 1);
			PaymentPlan.MonthlyPaymentDetail thisMpd = monthlyPayments.get(monthNum - 1);

			if (!mpd.equals(thisMpd))
				return false;
		}

		return true;
	}

	
	
	

}
