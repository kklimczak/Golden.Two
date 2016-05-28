package com.goldentwo.app;

import java.util.Date;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;

public class Application {

	public static void main(String[] args) {
		System.out.println("Work!");
		
		DataServiceImpl dataServiceImpl = new DataServiceImpl();
		
		dataServiceImpl.allEventsToXml();
		
		dataServiceImpl.allEventsFromXml();
		
		dataServiceImpl.oneEventToXml(new Event("String", "String", "Place", new Date()));
		
		dataServiceImpl.oneEventFromXml("0.String.xml");

	}

}
