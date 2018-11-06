package com.klezovich.payment_plan_generator.domain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentPlanJsonConvereter {

	PaymentPlan pp;
	List<MonthlyPaymentDetailJson> mpdJsonList = new ArrayList<>();
	
	static class MonthlyPaymentDetailJson{
		public String borrowerPaymentAmount;
		public Date date;
		public String initialOutstandingPrinciple;
		public String interest;
		public String principal;
		public String remainingOutstandingPrinciple;
		@Override
		public String toString() {
			return "MonthlyPaymentDetailJson [borrowerPaymentAmount=" + borrowerPaymentAmount + ", date=" + date
					+ ", initialOutstandingPrinciple=" + initialOutstandingPrinciple + ", interest=" + interest
					+ ", principal=" + principal + ", remainingOutstandingPrinciple=" + remainingOutstandingPrinciple
					+ "]";
		}
		
		
	}
	
	public PaymentPlanJsonConvereter( PaymentPlan pp ){
		this.pp = pp;
	}
	
	public Object getJsonPaymentPlan() {
		
		List<PaymentPlan.MonthlyPaymentDetail> liMpd = pp.getMonthlyPayments();
		
		for( PaymentPlan.MonthlyPaymentDetail mpd : liMpd ) {
			MonthlyPaymentDetailJson mpdJson = convertMpdToJsonMpd(mpd);
			mpdJsonList.add(mpdJson);
		}
		
		return mpdJsonList;
	}
	
	private MonthlyPaymentDetailJson convertMpdToJsonMpd( PaymentPlan.MonthlyPaymentDetail mpd ) {
		MonthlyPaymentDetailJson mpdJson = new MonthlyPaymentDetailJson();
		DecimalFormat df = new DecimalFormat("0.00");
		
		mpdJson.borrowerPaymentAmount = df.format(mpd.getPaymentAmount());
		mpdJson.date = mpd.getPaymentDate();
		mpdJson.initialOutstandingPrinciple = df.format(mpd.getInitialOutstandingPrincipal());
		System.out.println("Norm:"+mpd.getInitialOutstandingPrincipal()+"Json:"+mpdJson.initialOutstandingPrinciple);
		mpdJson.interest = df.format(mpd.getInterest());
		mpdJson.principal = df.format( mpd.getPrincipal() );
		mpdJson.remainingOutstandingPrinciple = df.format( Math.abs(mpd.getRemainingOutstandingPrincipal()) );
		
		return mpdJson;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( MonthlyPaymentDetailJson mpdJson : mpdJsonList ) {
			sb.append(mpdJson.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
