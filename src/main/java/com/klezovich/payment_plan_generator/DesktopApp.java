package com.klezovich.payment_plan_generator;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.klezovich.payment_plan_generator.domain.LoanData;
import com.klezovich.payment_plan_generator.domain.PaymentPlan;
import com.klezovich.payment_plan_generator.domain.PaymentPlanGenerator;
import com.klezovich.payment_plan_generator.util.AppDateFormatter;
import com.klezovich.payment_plan_generator.util.PaymentPlanPrinter;

/**
 * Hello world!
 *
 */
public class DesktopApp {
	
	// Name of the directory into which the file is saved
	private static final Path outFileDir = Paths.get(System.getProperty("user.dir")+"\\output");
	
	//Regular expression for reading the loan start date from input
	private static final String dateFormatRegExp = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	
	public static void main(String[] args) {
			
			System.out.println("Please provide the following input parameters separated by a space");
			System.out.println("Loan amount (integer), nominal rate as percentage (double), duration (in months), start date (YYYY-MM-DD) ");
			System.out.println("Sample input:'5000 5.0 24 2017-12-29'");
			
			Scanner in = new Scanner(System.in);
			
			int loanAmount = in.nextInt();
			double nominalRate = in.nextDouble();
			int duration = in.nextInt();
			
			Date startDate=null;
			String dateString;
			AppDateFormatter apf = new AppDateFormatter();
			
			try {
			  dateString = in.next(dateFormatRegExp);
			  startDate = apf.getFormatter().parse(dateString);
			}catch( Exception e ) {
				System.out.println("Incorrect date format, please use YYYY-MM-DD, for example '2017-12-26'" );
			}
			
			// It is better if internally we store the nominal rate as a fraction, 
			// for example 5% is stored as 0.05, 21% is stored as 0.21. This simplifies calculations
			LoanData loanData = new LoanData(loanAmount, nominalRate*0.01, duration, startDate);
			
			//System.out.println("\n\n");
			//System.out.println(loanData);
			
			PaymentPlanGenerator ppg = new PaymentPlanGenerator(loanData);
			PaymentPlan pp = ppg.getPaymentPlan();
			PaymentPlanPrinter ppp = new PaymentPlanPrinter(pp, loanData);
			
			//System.out.println(pp);
			System.out.println("\n\nThe payment plan for these parameters is:\n");
			System.out.println(ppp.getPaymentPlanCSV());
			
			
			//System.out.println(outFileDir);
			File outFile = ppp.printPaymentPlan( outFileDir );
			System.out.println("Output saved to file:" + outFile );
			
			in.close();
		}

	
}
