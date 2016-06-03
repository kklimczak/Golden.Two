package com.goldentwo.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.goldentwo.app.AppConfig;
import com.goldentwo.app.Application;
import com.goldentwo.utils.Logger.Logger;

public class DBInit {

	private Connection connection;
	private Statement statement;

	private AppConfig appConfig = new AppConfig(Application.profile);

	private Logger logger = new Logger(DBInit.class);

	public DBInit() {
		try {
			connection = DriverManager.getConnection(
					(String) appConfig.getProperty("url") + "?" + (String) appConfig.getProperty("params"),
					(String) appConfig.getProperty("user"), (String) appConfig.getProperty("password"));

			statement = connection.createStatement();
			createDatabase();
			createEntities();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	private void createDatabase() throws SQLException {
		logger.info("Creating database for application");
		statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS jProject");
	}

	private void createEntities() throws SQLException {
		logger.info("Creating tables in database");
		statement.executeUpdate("USE jProject;"
				+ "CREATE TABLE IF NOT EXISTS `events` ("
				+ "`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "`name` varchar(50) DEFAULT NULL," + "`description` varchar(500) DEFAULT NULL,"
				+ "`place` varchar(50) DEFAULT NULL," + "`date` datetime DEFAULT NULL,"
				+ "`alarm` datetime DEFAULT NULL)");
	}

	public void clearDatabase() {
		try {
			logger.info("Drop and create new empty database");
			statement.executeUpdate("DROP SCHEMA IF EXISTS jProject");
			createDatabase();
			createEntities();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
