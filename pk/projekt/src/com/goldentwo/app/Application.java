package com.goldentwo.app;

import java.util.Date;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.utils.Date.DateConverter;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Page;
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
		
//		for(int i = 0; i < 15; i++) {
//			dataServiceImpl.addEvent(new Event(1, "Name " + i, "Description " + i, "Place " + i, DateConverter.stringToDate(2016, 05, i)));
//		}
		
		//dataServiceImpl.updateEvent(new Event(1, "String [edit]", "String", "Place", new Date()));
		
		//dataServiceImpl.deleteEvent(1);
		
		//dataServiceImpl.deleteEventBeforeDate(new Date());
		
		Page<Event> events = dataServiceImpl.getSortedAndFilteredEvents(null, new Filter("name", "ame 1"), 1);
		System.out.println(events);
		for(Event event : events.getContent()) {
			System.out.println(event.getId() + ": " + event.getName() + " " + event.getDate());
		}
		
		//UserInterface ui = new UserInterface(dataServiceImpl);
		//ui.setVisible(true);

	}

}
