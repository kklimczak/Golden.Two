package com.goldentwo.data.Settings;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.goldentwo.data.database.DBConnection;
import com.goldentwo.utils.Exceptions.SQLUpdateException;
import com.goldentwo.utils.Logger.Logger;

/**
 * Klasa odpowidzialna za obsluge bazy danych dla tabeli z ustawieniami (CRUD).
 */
public class SettingsRepository {

	/** Obiekt zawierajacy konfiguracje oraz polaczenie z baza danych. */
	private DBConnection db;
	
	/** Obiekt zawierajacy metody sluzace do logowania na konsoli. */
	private Logger logger = new Logger(SettingsRepository.class);
	
	/**
	 * Glowny konstruktor klasy SettingsRepository ustawiajacy obiekt klasy konfiguracyjnej bazy danych.
	 *
	 * @param dbConnection Klasa konfiguracyjna bazy danych.
	 */
	public SettingsRepository(DBConnection dbConnection) {
		this.db = dbConnection;
	}
	
	/**
	 * Metoda zwracajaca obiekt ustawien wraz ze wszystkimi uzupelnionymi polami.
	 *
	 * @return Obiekt ustawien
	 */
	public Settings getOne() {
		try {
			ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM `settings`");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String defaultExportPath = resultSet.getString("defaultExportPath");
				int alarmSound = resultSet.getInt("alarmSound");
				int lookAndFeelNumber = resultSet.getInt("lookAndFeelNumber");
				logger.info("Get settings from database");
				return new Settings(id, defaultExportPath, alarmSound, lookAndFeelNumber);
			}
			logger.error("Settings not found in database");
			return null;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Metoda dodajaca nowy rekord w bazie zawierajacy ustawienia programu.
	 *
	 * @param defaultExportPath Domyslna sciezka eksportu
	 * @param alarmSound Numer dzwieku alarmu
	 * @param lookAndFeelNumber Numer wygladu programu
	 */
	public void addOne(String defaultExportPath, int alarmSound, int lookAndFeelNumber) {
		try {
			db.getStmt().executeUpdate("INSERT INTO `settings` VALUES (NULL, '" + defaultExportPath + "',"
			+ "'"+alarmSound+"','"+lookAndFeelNumber+"')");
			logger.info("Create new settings record");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
	}
	
	/**
	 * Metoda aktualizujaca ustawienia programu w bazie.
	 *
	 * @param settings Obiekt ustawien
	 * @throws SQLUpdateException Wyjatek zwiazany z bleden zaktualizowania obiektu w bazie.
	 */
	public void updateOne(Settings settings) throws SQLUpdateException {
		try {
			db.getStmt().executeUpdate("UPDATE `settings` SET "
					+ "defaultExportPath='"+settings.getDefaultExportPath()+"',"
					+ "alarmSound='"+settings.getAlarmSound()+"',"
					+ "lookAndFeelNumber='"+settings.getLookAndFeelNumber()+"' "
					+ "WHERE id=1");
			logger.info("Settings updated successfully");
		} catch (SQLException sqlException) {
			throw new SQLUpdateException("Error with saving settings in database");
		}
	}
}
