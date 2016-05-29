package com.goldentwo.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.goldentwo.utils.Logger.Logger;

public class DBConnection {

	private Connection con;
	private Statement stmt;
	
	private Logger logger = new Logger(DBConnection.class);
	
	public DBConnection() {
	    try {
	    	con = DriverManager.getConnection(
					 "jdbc:mysql://localhost/jProject?useSSL=false&allowMultiQueries=true",
	                 "root",
	                 "wiadro123");

			stmt = con.createStatement();
			logger.info("Successfully connected to database jProject as root");
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
