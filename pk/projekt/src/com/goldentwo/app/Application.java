package com.goldentwo.app;

import java.awt.Color;
import java.util.Date;

import com.goldentwo.data.Event.Event;
import com.goldentwo.data.Settings.Settings;
import com.goldentwo.data.database.DBConnection;
import com.goldentwo.data.database.DBInit;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.service.SettingsServiceImpl;
import com.goldentwo.utils.Logger.Logger;
import com.goldentwo.utils.alarmChecker.AlarmChecker;
import com.goldentwo.view.UserInterface;

public class Application {
	
	public static String profile;

	public static void main(String[] args) {
		
		profile = args[0]; 
		
		Logger logger = new Logger(Application.class);
		logger.info("Aplication started!");
		logger.info("Load profile: " + profile);
		
		DBInit dbInit = new DBInit();
		DBConnection dbConnection = new DBConnection();
		SettingsServiceImpl settingsServiceImpl = new SettingsServiceImpl(dbConnection);
		
		Settings settings = settingsServiceImpl.getSettings();
		
		DataServiceImpl dataServiceImpl = new DataServiceImpl(dbConnection, settings);
		
		dataServiceImpl.getEventById(120);
	
		logger.info("UserInterface initialized successfully!");
		
		AlarmChecker ac = new AlarmChecker(dataServiceImpl);
		UserInterface ui = new UserInterface(dataServiceImpl, ac);	
		ui.setVisible(true);
		
		new Thread(ac).start();
		
	}

}
