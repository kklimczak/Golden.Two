package com.goldentwo.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.goldentwo.app.AppConfig;
import com.goldentwo.app.Application;
import com.goldentwo.utils.Logger.Logger;

/**
 * Klasa odpowiedzialna za stworzenie bazy wraz z tabeli, jak ich usuniecia.
 */
public class DBInit {

	/** Klasa odpowiedzialna za polaczenia z baza. */
	private Connection connection;
	
	/** Klasa odpowiedzialna za wykonwywanie zapytan do bazy. */
	private Statement statement;

	/** Klasa sluzaca do pobierania odpowiednich ustawien z plików .properties dla aktywnego profilu. */
	private AppConfig appConfig = new AppConfig(Application.profile);

	/** Obiekt zawierajacy metody sluzace do logowania na konsoli. */
	private Logger logger = new Logger(DBInit.class);

	/**
	 * Glowny konstruktor odpowiedzialny za nawiazanie polaczenia z baza oraz stworzenie schema oraz tabeli.
	 */
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

	/**
	 * Tworzy schema w bazie.
	 *
	 * @throws SQLException the SQL exception
	 */
	private void createDatabase() throws SQLException {
		logger.info("Creating database for application");
		statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS jProject");
	}

	/**
	 * Tworzy tabele w bazie.
	 *
	 * @throws SQLException the SQL exception
	 */
	private void createEntities() throws SQLException {
		logger.info("Creating tables in database");
		statement.executeUpdate("USE jProject;"
				+ "CREATE TABLE IF NOT EXISTS `events` ("
				+ "`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "`name` varchar(50) DEFAULT NULL," + "`description` varchar(500) DEFAULT NULL,"
				+ "`place` varchar(50) DEFAULT NULL," + "`date` datetime DEFAULT NULL,"
				+ "`alarm` datetime DEFAULT NULL);"
				+ "CREATE TABLE IF NOT EXISTS `settings` ("
				+ "`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "`defaultExportPath` varchar(100) DEFAULT NULL,"
				+ "`alarmSound` int(11) DEFAULT NULL,"
				+ "`lookAndFeelNumber` int(11) DEFAULT NULL)");
		
	}

	/**
	 * Czysci cala baze danych.
	 */
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
