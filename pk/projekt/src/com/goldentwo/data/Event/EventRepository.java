package com.goldentwo.data.Event;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.goldentwo.data.database.DBConnection;

public class EventRepository {
	
	private DBConnection db;
	
	public EventRepository(DBConnection dbConnection) {
		this.db = dbConnection;
	}
	
	public List<Event> findAll() {
		try {
			List<Event> events = new ArrayList<>();
			ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM events");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = resultSet.getDate("date");
		        events.add(new Event(id, name, description, place, date));
			}
			return events;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public List<Event> findByDateRange(Date from, Date to) {
		try {
			List<Event> events = new ArrayList<>();
			ResultSet resultSet = db.getStmt().executeQuery("Select * FROM events WHERE date >= '" + from + "' AND date <= '" + to + "'");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = resultSet.getDate("date");
		        events.add(new Event(id, name, description, place, date));
			}
			return events;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public Event findOne(int id) {
		try {
			ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM events WHERE id = "+id);
			while(resultSet.next()) {
				int eventId = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = resultSet.getDate("date");
		        System.out.println(eventId+name+description);
		        return new Event(eventId, name, description, place, date);
			}
			return null;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public void addOne(Event event) {
		try {
			db.getStmt().executeUpdate("INSERT INTO `events` VALUES (NULL, '"+event.getName()+"','"+event.getDescription()+"','"+event.getPlace()+"','"+new java.sql.Date(event.getDate().getTime())+"')");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void updateOne(Event event) {
		try {
			db.getStmt().executeUpdate("UPDATE `events` SET  name='"+event.getName()+"',description='"+event.getDescription()+"',place='"+event.getPlace()+"',date='"+new java.sql.Date(event.getDate().getTime())+"' WHERE id="+event.getId());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void deleteOne(int id) {
		try {
			db.getStmt().executeUpdate("DELETE FROM `events` WHERE id = " + id);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public void deleteEventsBeforeDate(Date date) {
		try {
			db.getStmt().executeUpdate("DELETE FROM `events` WHERE date < '" + date + "'");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
