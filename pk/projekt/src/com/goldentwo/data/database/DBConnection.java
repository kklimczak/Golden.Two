package com.goldentwo.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.goldentwo.app.AppConfig;
import com.goldentwo.app.Application;
import com.goldentwo.utils.Logger.Logger;

public class DBConnection {

	private Connection con;
	private Statement stmt;
	
	private AppConfig appConfig = new AppConfig(Application.profile);
	
	private Logger logger = new Logger(DBConnection.class);
	
	public DBConnection() {
	    try {
	    	con = DriverManager.getConnection(
					 (String) appConfig.getProperty("url") + "?" + (String) appConfig.getProperty("params"),
	                 (String) appConfig.getProperty("user"),
	                 (String) appConfig.getProperty("password"));

			stmt = con.createStatement();
			logger.info("Successfully connected to database " + appConfig.getProperty("url") + " as " + appConfig.getProperty("user"));
	    } catch (SQLException sqlException) {
	    	sqlException.printStackTrace();
	    }
	}

	public Connection getCon() {
		return con;
	}

	public Statement getStmt() {
		return stmt;
	}
}
