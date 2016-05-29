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
}
