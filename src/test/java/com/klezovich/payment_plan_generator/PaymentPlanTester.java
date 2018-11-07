package com.klezovich.payment_plan_generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import com.klezovich.payment_plan_generator.domain.LoanData;
import com.klezovich.payment_plan_generator.domain.PaymentPlan;
import com.klezovich.payment_plan_generator.domain.PaymentPlanGenerator;
import com.klezovich.payment_plan_generator.util.AppDateFormatter;

public class PaymentPlanTester {
	
	private static final String testPaymentPlanFileDir= "src//test//resources//test_payment_plans//";

	static File getTestPaymentPlanFile( LoanData ld ) {
		
		AppDateFormatter adf = new AppDateFormatter();
		
		String filePath = testPaymentPlanFileDir + "//PaymentPlan_"+ld.getLoanAmount()+"_"+ld.getNominalRate()+"_"+ld.getDuration()+"_"+adf.format(ld.getStartDate());
		return new File(filePath);
		
	}
	
	static public PaymentPlan readPaymentPlanFromCsv( File f ){
		
		PaymentPlan pp = new PaymentPlan();
		
		Scanner in;
		try {
			in = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		// Skipping the first line with headers;
        in.nextLine();
		
        while( in.hasNextLine() ) {
           String line = in.nextLine();
           
           if( line.isBlank() )
        	   break;
           
           PaymentPlan.MonthlyPaymentDetail mpd = parseMonthlyPaymentDetailFromCsv(line);
           pp.addMonthlyPayment(mpd);
        }
		
        in.close();
		return pp;
	}
	
	static PaymentPlan.MonthlyPaymentDetail parseMonthlyPaymentDetailFromCsv( String line ) {
		
		String[] tokens = line.split(",");
		
		AppDateFormatter apf = new AppDateFormatter();
		Date paymentDate;
		try {
			paymentDate = apf.getFormatter().parse(tokens[0]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		double paymentAmount = Double.valueOf(tokens[1]);
		double principal = Double.valueOf(tokens[2]);
		double interest = Double.valueOf(tokens[3]);
		double initialOutstandingPrincipal = Double.valueOf(tokens[4]);
		double remainingOutstandingPrincipal = Double.valueOf(tokens[5]);

		PaymentPlan.MonthlyPaymentDetail mpd = new PaymentPlan.MonthlyPaymentDetail();
		
		mpd.setPaymentDate(paymentDate);
        mpd.setPaymentAmount(paymentAmount);
        mpd.setPrincipal(principal);
        mpd.setInterest(interest);
        mpd.setInitialOutstandingPrincipal(initialOutstandingPrincipal);
		mpd.setRemainingOutstandingPrincipal(remainingOutstandingPrincipal);
        
		return mpd;
	}
	
	// This method is used for a quick local test of the class functionality
	public static void main(String[] args)  {
		
		System.out.println("Starting test");
		
		AppDateFormatter adf = new AppDateFormatter();
		Date date;
		try {
			date = adf.getFormatter().parse("2017-12-26");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println(date);
		
		LoanData ld = new LoanData(5000,0.05,24,date);
		
		PaymentPlanTester ppt = new PaymentPlanTester();
		
		File file = getTestPaymentPlanFile(ld);
		PaymentPlan ppFromFile = ppt.readPaymentPlanFromCsv( file );
		
		PaymentPlanGenerator ppg = new PaymentPlanGenerator(ld);
		PaymentPlan pp = ppg.getPaymentPlan();
		
		if( pp.equals(ppFromFile) )
			System.out.println("Plans match");
		else 
			System.out.println("Plans do not match");
		
		System.out.println("Generated plan");
		System.out.println(pp);
		System.out.println("Plan from file");
		System.out.println(ppFromFile);
		
	}
}
