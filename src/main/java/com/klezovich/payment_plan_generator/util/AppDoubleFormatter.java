package com.klezovich.payment_plan_generator.util;

import java.text.DecimalFormat;

public class AppDoubleFormatter {

	 private DecimalFormat df = new DecimalFormat("#.##");

	 String format( Double d ) {
		return df.format(d);
	 }
	
}
