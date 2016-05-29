package com.goldentwo.app;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.goldentwo.utils.Logger.Logger;

public class AppConfig {

	private File file;
	private Properties properties;
	
	private Logger logger = new Logger(AppConfig.class);
	
	public AppConfig(String profile) {
		file = new File(profile + ".properties");
		
		try {
			FileReader fileReader = new FileReader(file);
			properties = new Properties();
			properties.load(fileReader);
			logger.info(profile + ".properties loaded!");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public Object getProperty(String propertyName) {
		return properties.get(propertyName);
	}
	
}
