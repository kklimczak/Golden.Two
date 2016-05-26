package com.goldentwo.app;

import com.goldentwo.service.DataServiceImpl;

public class Application {

	public static void main(String[] args) {
		System.out.println("Work!");
		
		DataServiceImpl dataServiceImpl = new DataServiceImpl();
		
		dataServiceImpl.allEventsToXml();
		
		dataServiceImpl.allEventsFromXml();

	}

}
