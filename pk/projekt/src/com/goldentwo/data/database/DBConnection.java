package com.goldentwo.data.database;

import java.sql.*;
import java.util.ArrayList;

import com.goldentwo.data.Event.Event;

public class DBConnection {

	private Connection con;
	private Statement stmt;
	
	public DBConnection() throws SQLException{
	    con = DriverManager.getConnection(
				 "jdbc:mysql://localhost/jProject?useSSL=false",
                 "root",
                 "wiadro123");

		stmt = con.createStatement();
	}
	
	public ArrayList<Event> getAllEvents() throws SQLException{

		ResultSet rs = stmt.executeQuery("SELECT * FROM events");
		ArrayList<Event> eventList = new ArrayList<>();
	    while (rs.next()) {
	        int id = rs.getInt("id");
	        String name = rs.getString("name");
	        String description = rs.getString("description");
	        String place = rs.getString("place");
	        Date date = rs.getDate("date");
	        eventList.add(new Event(id, name, description, place, date));
	    }
	    
		return eventList;
	}
}
