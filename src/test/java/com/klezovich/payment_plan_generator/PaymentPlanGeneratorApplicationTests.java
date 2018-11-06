package com.klezovich.payment_plan_generator;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.klezovich.payment_plan_generator.domain.LoanData;
import com.klezovich.payment_plan_generator.domain.PaymentPlan;
import com.klezovich.payment_plan_generator.domain.PaymentPlanGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentPlanGeneratorApplicationTests {

	PaymentPlanTester ppt = new PaymentPlanTester();
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testOne() throws Exception {
     	
		LoanData ld = new LoanData(5000,0.05,24,"2017-12-26");
		
		PaymentPlanTester ppt = new PaymentPlanTester();
		
		File file = ppt.getTestPaymentPlanFile(ld);
		PaymentPlan ppFromFile = ppt.readPaymentPlanFromCsv( file );
		
		PaymentPlanGenerator ppg = new PaymentPlanGenerator(ld);
		PaymentPlan pp = ppg.getPaymentPlan();
		
		if( !pp.equals(ppFromFile) )
			fail("Payment plans do not match for"+ ld);
		
	}
	
	@Test
	public void testTwo() throws ParseException {
		
        LoanData ld = new LoanData(5000,0.05,24,"2017-12-26");
        LoanData ld1 = new LoanData(5000,0.05,24,"2018-12-26");
		
		PaymentPlanTester ppt = new PaymentPlanTester();
		
		File file = ppt.getTestPaymentPlanFile(ld);
		PaymentPlan ppFromFile = ppt.readPaymentPlanFromCsv( file );
		
		PaymentPlanGenerator ppg = new PaymentPlanGenerator(ld1);
		PaymentPlan pp = ppg.getPaymentPlan();
		
		if( pp.equals(ppFromFile) )
			fail("Payment plans for different start date match");
		
	}
	
	@Test
	public void testThree() throws Exception {
     	
		LoanData ld = new LoanData(1000,0.05,1,"2016-11-23");
		
		PaymentPlanTester ppt = new PaymentPlanTester();
		
		File file = ppt.getTestPaymentPlanFile(ld);
		PaymentPlan ppFromFile = ppt.readPaymentPlanFromCsv( file );
		
		PaymentPlanGenerator ppg = new PaymentPlanGenerator(ld);
		PaymentPlan pp = ppg.getPaymentPlan();
		
		if( !pp.equals(ppFromFile) )
			fail("Payment plans do not match for"+ ld);
		
	}
	
	@Test
	public void testFour() throws Exception {
     	
		LoanData ld = new LoanData(4550,0.1,7,"2015-01-01");
		
		PaymentPlanTester ppt = new PaymentPlanTester();
		
		File file = ppt.getTestPaymentPlanFile(ld);
		PaymentPlan ppFromFile = ppt.readPaymentPlanFromCsv( file );
		
		PaymentPlanGenerator ppg = new PaymentPlanGenerator(ld);
		PaymentPlan pp = ppg.getPaymentPlan();
		
		if( !pp.equals(ppFromFile) )
			fail("Payment plans do not match for"+ ld);
		
	}
	
}
