package com.goldentwo.utils.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

	public static Date stringToDate(int year, int month, int day) {
		try  {
			return new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-"+day);
		} catch (ParseException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public static Date convertToDateWithTime(int year, int month, int day, int hours, int minutes, int seconds) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds);
		} catch (ParseException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public static String dateToMySqlDateTimeString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
}
