package com.goldentwo.service;

import java.util.Date;
import java.util.List;

import com.goldentwo.data.Event.Event;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Page;
import com.goldentwo.utils.Pagination.Sort;

public interface DataService {
	
	List<Event> getAllEvents();
	Page<Event> getSortedAndFilteredEvents(Sort sort, Filter filter);
	List<Event> getAllEventsBetweenDates(Date from, Date to);
	List<Event> getEventsByName(String name);
	Event getEventById();
	void addEvent(Event event);
	void updateEvent(Event event);
	void deleteEvent(int id);
	void deleteEventBeforeDate(Date date);
	void allEventsToXml();
	List<Event> allEventsFromXml();
	void oneEventToXml(Event event);
	Event oneEventFromXml(String pathname);
}