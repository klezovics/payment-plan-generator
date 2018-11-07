package com.klezovich.payment_plan_generator.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.klezovich.payment_plan_generator.domain.LoanData;
import com.klezovich.payment_plan_generator.domain.PaymentPlan;
import com.klezovich.payment_plan_generator.domain.PaymentPlan.MonthlyPaymentDetail;

public class PaymentPlanPrinter {

	private PaymentPlan pp;
	private LoanData ld;

	public PaymentPlanPrinter(PaymentPlan pp, LoanData ld) {
		this.pp = pp;
		this.ld = ld;
	}

	public String getPaymentPlanCSV() {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"PaymentDate,PaymentAmount,Principal,Interest,initialOutstandingPrincipal,remainingOutstandingPrincipal\n");

		for (MonthlyPaymentDetail mpd : pp.getMonthlyPayments()) {
			sb.append(mpd.toCSV());
			sb.append("\n");
		}

		return sb.toString();
	}

	public File printPaymentPlan(Path targetDir) {

		String fileName = calcPaymentPlanFileName();

		File paymentPlanFile = new File(targetDir.toFile(), fileName);
		// System.out.println(file.toString());

		if (paymentPlanFile.exists()) {
			paymentPlanFile.delete();
		}

		try {
			paymentPlanFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		writePaymentPlanToFile(paymentPlanFile);
		
		return paymentPlanFile;
	}

	private String calcPaymentPlanFileName() {

		AppDateFormatter adf = new AppDateFormatter();
		AppDoubleFormatter aDoubleF = new AppDoubleFormatter();
		
		String fileName = "PaymentPlan_" + ld.getLoanAmount() + "_" + aDoubleF.format(ld.getNominalRate()) + "_" + ld.getDuration() + "_"
				+ adf.format(ld.getStartDate()) + ".csv";
		return fileName;
	}
	
	private boolean writePaymentPlanToFile( File paymentPlanFile ) {
		
		String fileContents = getPaymentPlanCSV();

		byte[] strToBytes = fileContents.getBytes();

		try {
			Files.write(paymentPlanFile.toPath(), strToBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
