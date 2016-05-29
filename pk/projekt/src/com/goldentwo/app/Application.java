package com.goldentwo.app;

import java.util.Date;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.view.UserInterface;

public class Application {

	public static void main(String[] args) {
		System.out.println("Work!");
		
		DataServiceImpl dataServiceImpl = new DataServiceImpl();
		
		dataServiceImpl.allEventsToXml();
		
		dataServiceImpl.allEventsFromXml();
		
		dataServiceImpl.oneEventToXml(new Event(0, "String", "String", "Place", new Date()));
		
		dataServiceImpl.oneEventFromXml("0.String.xml");
		
		//dataServiceImpl.addEvent(new Event(1, "String", "String", "Place", new Date()));
		
		dataServiceImpl.updateEvent(new Event(1, "String [edit]", "String", "Place", new Date()));
		
		//dataServiceImpl.deleteEvent(1);
		
		//dataServiceImpl.deleteEventBeforeDate(new Date());
		
		for(Event event: dataServiceImpl.getAllEvents()) {
			System.out.println("[" + event.getId() + "] "+ event.getName() + ": " + event.getDate());
		}
		
		UserInterface ui = new UserInterface();
		ui.setVisible(true);

	}

}
