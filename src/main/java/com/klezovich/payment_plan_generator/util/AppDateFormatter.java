package com.klezovich.payment_plan_generator.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppDateFormatter {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public String format( Date d ) {
		return df.format(d);
	}
	
	public SimpleDateFormat getFormatter() {
		return df;
	}
}
