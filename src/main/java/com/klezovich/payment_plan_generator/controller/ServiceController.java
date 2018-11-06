package com.klezovich.payment_plan_generator.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.klezovich.payment_plan_generator.domain.LoanData;
import com.klezovich.payment_plan_generator.domain.PaymentPlan;
import com.klezovich.payment_plan_generator.domain.PaymentPlanGenerator;
import com.klezovich.payment_plan_generator.domain.PaymentPlanJsonConvereter;
import com.klezovich.payment_plan_generator.util.AppDateFormatter;

@Controller
public class ServiceController {

	private static final String dateFormatRegExp = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	
	@PostMapping("/generate-plan")
	@ResponseBody
	public Object sendPaymentPlan(
			@RequestParam(name="loanAmount", required=true) Integer loanAmount,
			@RequestParam(name="nominalRate", required=false) Double nominalRate,
			@RequestParam(name="duration", required=true) Integer duration,
			@RequestParam(name="startDate", required=true) String startDateStr
			) {
		
		String dateString = startDateStr.substring(0, 10);
		AppDateFormatter adf = new AppDateFormatter();
		Date startDate;
		
		try {
			startDate = adf.getFormatter().parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		LoanData ld= new LoanData(loanAmount,nominalRate*0.01,duration, startDate);
		
		
		PaymentPlanGenerator ppg = new PaymentPlanGenerator(ld);
		PaymentPlan pp = ppg.getPaymentPlan();
		System.out.println(pp);
		PaymentPlanJsonConvereter ppJsonConv = new PaymentPlanJsonConvereter(pp);
		Object jsonPaymentPlan = ppJsonConv.getJsonPaymentPlan();
		System.out.println(jsonPaymentPlan);
		return jsonPaymentPlan;
		
	}
}
