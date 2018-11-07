package com.klezovich.payment_plan_generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

import com.klezovich.payment_plan_generator.util.AppDateFormatter;
import com.klezovich.payment_plan_generator.util.AppRegExpCollection;

public class TestMakeHelper {

	private static final String dateFormatRegExp = AppRegExpCollection.getDateFormatRegExp();

	public static void main(String[] args) throws FileNotFoundException {

		Date startDate = null;
		String dateString;
		AppDateFormatter apf = new AppDateFormatter();

		File f = new File("tmp");
		Scanner in = new Scanner(f);

		try {
			dateString = in.next(dateFormatRegExp);
			System.out.println(dateString);
			startDate = apf.getFormatter().parse(dateString);
		} catch (Exception e) {
			System.out.println("Incorrect date format, please use YYYY-MM-DD, for example '2017-12-26'");
		}

		in.nextLine();
		System.out.println(apf.format(startDate));

		while (in.hasNextLine()) {
			// beg balance, int, principla, remaining
			// $4,550.00 $37.92 $633.93 $3,916.07

			DecimalFormat decForm = new DecimalFormat("0.00");

			String line = in.nextLine();
			// System.out.println(line);

			line = line.replaceAll("\\$", "");
			line = line.replaceAll(",", "");
			line = line.replaceAll("\\t", " ");
			line = line.trim().replaceAll(" +", " ");
			// line = line.replace("[\\t]+"," ");
			// System.out.println(line);
			String[] tokens = line.split(" ");

			String tok0 = tokens[0];
			char[] ca = tok0.toCharArray();

			/*
			 * System.out.println("Printing chars"); for(char c : ca ) { int cv = (int) c;
			 * System.out.println( c+":"+cv ); } System.out.println("Done printing chars");
			 */

			// System.out.println("Tokens");
			// for(String t: tokens )
			// System.out.println(t);

			int monthNum = Integer.valueOf(tokens[0]);

			LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			startLocalDate = startLocalDate.plusMonths(monthNum);
			Date date = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

			double initialOutstandingPrincipal = Double.valueOf(tokens[1]);
			double interest = Double.valueOf(tokens[2]);
			double principal = Double.valueOf(tokens[3]);
			double remainingOutstandingPrincipal = Double.valueOf(tokens[4]);

			double payment = interest + principal;

			String resStr = "";
			resStr += apf.format(date) + ",";
			resStr += decForm.format(principal + interest) + ",";
			resStr += decForm.format(principal) + ",";
			resStr += decForm.format(interest) + ",";
			resStr += decForm.format(initialOutstandingPrincipal) + ",";
			resStr += (decForm.format(Math.abs(remainingOutstandingPrincipal)));

			System.out.println(resStr);

		}

		in.close();
		System.out.println("Done");

	}
}
