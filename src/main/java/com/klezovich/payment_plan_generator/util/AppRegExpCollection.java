package com.klezovich.payment_plan_generator.util;

public class AppRegExpCollection {

	private static final String dateFormatRegExp = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	
	public static String getDateFormatRegExp() {
		return dateFormatRegExp;
	}
}
