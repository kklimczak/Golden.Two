package com.goldentwo.data.Settings;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.goldentwo.data.database.DBConnection;
import com.goldentwo.utils.Exceptions.SQLUpdateException;
import com.goldentwo.utils.Logger.Logger;

public class SettingsRepository {

	private DBConnection db;
	
	private Logger logger = new Logger(SettingsRepository.class);
	
	public SettingsRepository(DBConnection dbConnection) {
		this.db = dbConnection;
	}
	
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
	
	public void addOne(String defaultExportPath, int alarmSound, int lookAndFeelNumber) {
		try {
			db.getStmt().executeUpdate("INSERT INTO `settings` VALUES (NULL, '" + defaultExportPath + "',"
			+ "'"+alarmSound+"','"+lookAndFeelNumber+"')");
			logger.info("Create new settings record");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
	}
	
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
