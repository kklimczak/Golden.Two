package com.goldentwo.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.goldentwo.app.AppConfig;
import com.goldentwo.app.Application;
import com.goldentwo.utils.Logger.Logger;

/**
 * Klasa odpowiedzialna za polaczenie z baza danych wykorzystujaca dane z plikow properties dla konkretnego profilu. 
 */
public class DBConnection {

	/** Klasa odpowiedzialna za polaczenia z baza. */
	private Connection con;
	
	/** Klasa odpowiedzialna za wykonwywanie zapytan do bazy. */
	private Statement stmt;
	
	/** Klasa sluzaca do pobierania odpowiednich ustawien z plików .properties dla aktywnego profilu. */
	private AppConfig appConfig = new AppConfig(Application.profile);
	
	/** Obiekt zawierajacy metody sluzace do logowania na konsoli. */
	private Logger logger = new Logger(DBConnection.class);
	
	/**
	 * Glowny kostruktor klasy, ktory nawiazuje polaczenie z baza danych wykorzystujac dane pliku .properties i tworzy obiekt typu Statement
	 * do wykonywania zapytan do bazy.
	 */
	public DBConnection() {
	    try {
	    	con = DriverManager.getConnection(
					 (String) appConfig.getProperty("url") + (String) appConfig.getProperty("dbName") + "?" + (String) appConfig.getProperty("params"),
	                 (String) appConfig.getProperty("user"),
	                 (String) appConfig.getProperty("password"));

			stmt = con.createStatement();
			logger.info("Successfully connected to database " + appConfig.getProperty("url") + (String) appConfig.getProperty("dbName") + " as " + appConfig.getProperty("user"));
	    } catch (SQLException sqlException) {
	    	sqlException.printStackTrace();
	    }
	}

	/**
	 * Zwraca obiekt odpowiedzialny za polaczenie z baza.
	 *
	 * @return Obiekt Connection
	 */
	public Connection getCon() {
		return con;
	}

	/**
	 * Zwraca obiekt sluzacy do wykonywania zapytan do bazy.
	 *
	 * @return Obiekt Statement
	 */
	public Statement getStmt() {
		return stmt;
	}
}
