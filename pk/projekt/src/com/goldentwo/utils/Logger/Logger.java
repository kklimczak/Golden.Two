package com.goldentwo.utils.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	
	private Class classValue;

	public Logger(Class classValue) {
		this.classValue = classValue;
	}
	
	private void logger(String message, String type) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		System.out.printf("%s [%-20.20s] %-5s | %s%n", sdf.format(cal.getTime()), classValue.getSimpleName()+".java", type, message);
		String log = String.format("%s [%-20.20s] %-5s | %s%n", sdf.format(cal.getTime()), classValue.getSimpleName()+".java", type, message);
		
		try{
			FileOutputStream file = new FileOutputStream(sdfDay.format(cal.getTime()) + ".log", true);
			file.write(log.getBytes());
			file.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public void error(String message) {
		logger(message, "ERROR");
	}
	
	public void info (String message) {
		logger(message, "INFO");
	}
}
