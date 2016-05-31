package com.goldentwo.app;

import java.util.Date;

import com.goldentwo.data.Event.Event;
import com.goldentwo.data.database.DBInit;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.utils.Date.DateConverter;
import com.goldentwo.utils.Logger.Logger;
import com.goldentwo.utils.Pagination.Direction;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Page;
import com.goldentwo.utils.Pagination.Sort;
import com.goldentwo.view.UserInterface;

public class Application {
	
	public static String profile;

	public static void main(String[] args) {
		
		profile = args[0]; 
		
		Logger logger = new Logger(Application.class);
		
		logger.info("Aplication started!");
		
		logger.info("Load profile: " + args[0]);
		
		DBInit dbInit = new DBInit();
		
		dbInit.clearDatabase();
		
		DataServiceImpl dataServiceImpl = new DataServiceImpl();
		
		dataServiceImpl.allEventsToXml();
		
		dataServiceImpl.allEventsFromXml();
		
		dataServiceImpl.oneEventToXml(new Event(0, "String", "String", "Place", new Date(), new Date()));
		
		dataServiceImpl.oneEventFromXml("0.String.xml");
		
		dataServiceImpl.addEvent(new Event(1, "String1", "String", "Place", DateConverter.stringToDate(2016, 5, 12), null));
		
//		for(int i = 0; i < 15; i++) {
//			dataServiceImpl.addEvent(new Event(1, "Name " + i, "Description " + i, "Place " + i, DateConverter.stringToDate(2016, 05, i)));
//		}
		
		dataServiceImpl.updateEvent(new Event(1, "String [edit]", "String", "Place", new Date(), null));
		
		//dataServiceImpl.deleteEvent(1);
		
		//dataServiceImpl.deleteEventBeforeDate(new Date());
		
		//dataServiceImpl.getAllEventsBetweenDates(new Date(), new Date());
		
		Page<Event> events = dataServiceImpl.getSortedAndFilteredEvents(new Sort("date", Direction.DESC), null, 1);
//		System.out.println(events);
//		for(Event event : events.getContent()) {
//			System.out.println(event.getId() + ": " + event.getName() + " " + event.getDate());
//		}
		
		logger.info("UserInterface initialized successfully!");
		UserInterface ui = new UserInterface(dataServiceImpl);
		
		ui.setVisible(true);

	}

}
