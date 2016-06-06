package com.goldentwo.app;

import com.goldentwo.data.database.DBInit;
import com.goldentwo.service.DataServiceImpl;
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
		DataServiceImpl dataServiceImpl = new DataServiceImpl();
	
		logger.info("UserInterface initialized successfully!");
		UserInterface ui = new UserInterface(dataServiceImpl);	
		ui.setVisible(true);
		
		new Thread(new AlarmChecker()).start();
		
	}

}
