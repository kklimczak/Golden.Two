package com.goldentwo.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection con;
	private Statement stmt;
	
	public DBConnection() {
	    try {
	    	con = DriverManager.getConnection(
					 "jdbc:mysql://localhost/jProject?useSSL=false",
	                 "root",
	                 "wiadro123");

			stmt = con.createStatement();
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
