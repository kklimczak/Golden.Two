package com.goldentwo.service;

import java.awt.Color;

import com.goldentwo.data.Settings.Settings;
import com.goldentwo.data.Settings.SettingsRepository;
import com.goldentwo.data.database.DBConnection;
import com.goldentwo.utils.Exceptions.SQLUpdateException;
import com.goldentwo.utils.Logger.Logger;

public class SettingsServiceImpl implements SettingsService {
	
	private SettingsRepository settingsRepository;
	
	Logger logger = new Logger(SettingsServiceImpl.class); 
	
	public SettingsServiceImpl(DBConnection db) {
		this.settingsRepository = new SettingsRepository(db);
	}

	@Override
	public Settings getSettings() {
		logger.info("getSettings() called");
		Settings settings = settingsRepository.getOne();
		if (settings == null) {
			settingsRepository.addOne(System.getProperty("user.home"), Color.green.getRGB(), Color.red.getRGB(), 1);
			return settingsRepository.getOne();
		} else {
			return settings;
		}
	}

	@Override
	public boolean updateSettings(Settings settings) {
		try {
			settingsRepository.updateOne(settings);
			logger.info("updateSettings() called");
			return true;
		} catch (SQLUpdateException exception) {
			exception.printStackTrace();
			return false;
		}
	}

}
