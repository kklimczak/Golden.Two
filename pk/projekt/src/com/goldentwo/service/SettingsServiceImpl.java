package com.goldentwo.service;

import java.awt.Color;

import com.goldentwo.data.Settings.Settings;
import com.goldentwo.data.Settings.SettingsRepository;
import com.goldentwo.data.database.DBConnection;
import com.goldentwo.utils.Exceptions.SQLUpdateException;
import com.goldentwo.utils.Logger.Logger;

/**
 * Klasa odpowiadajaca za logike zwiazana z ustawieniami programu.
 */
public class SettingsServiceImpl implements SettingsService {
	
	/** Obiekt klasy odpowiedzialnej za operowanie na bazie danych. */
	private SettingsRepository settingsRepository;
	
	/** Obiekt zawierajacy metody sluzace do logowania na konsoli. */
	Logger logger = new Logger(SettingsServiceImpl.class); 
	
	/**
	 * Glowny konstruktor klasy odpowiadajacy za poprawne przekazanie do repozytorium polaczenia do bazy danych.
	 *
	 * @param db Klasa odpowiedzialna za polaczenia z baza
	 */
	public SettingsServiceImpl(DBConnection db) {
		this.settingsRepository = new SettingsRepository(db);
	}

	/* (non-Javadoc)
	 * @see com.goldentwo.service.SettingsService#getSettings()
	 */
	@Override
	public Settings getSettings() {
		logger.info("getSettings() called");
		Settings settings = settingsRepository.getOne();
		if (settings == null) {
			settingsRepository.addOne(System.getProperty("user.home"), 0, 1);
			return settingsRepository.getOne();
		} else {
			return settings;
		}
	}

	/* (non-Javadoc)
	 * @see com.goldentwo.service.SettingsService#updateSettings(com.goldentwo.data.Settings.Settings)
	 */
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
