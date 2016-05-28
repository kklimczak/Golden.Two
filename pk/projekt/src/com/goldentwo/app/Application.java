package com.goldentwo.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.goldentwo.data.Event.Event;
import com.goldentwo.data.database.DBConnection;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.view.UserInterface;

public class Application {

	public static void main(String[] args) throws SQLException {
		System.out.println("Work!");
		
		DataServiceImpl dataServiceImpl = new DataServiceImpl();
		
		dataServiceImpl.allEventsToXml();
		
		dataServiceImpl.allEventsFromXml();
		
		dataServiceImpl.oneEventToXml(new Event(0, "String", "String", "Place", new Date()));
		
		dataServiceImpl.oneEventFromXml("0.String.xml");
		
		DBConnection dbc = new DBConnection();
		ArrayList<Event> list = dbc.getAllEvents();
		
		for (Event e : list){
			System.out.println(e.getId() + " " + e.getDescription() + " " + e.getDate());
		}
		
		UserInterface ui = new UserInterface();
		ui.setVisible(true);

	}

}
