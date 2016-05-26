package com.goldentwo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.goldentwo.data.Event.Event;

public class DataServiceImpl implements DataService {

	@Override
	public List<Event> getAllEvents() {
		List<Event> events = new ArrayList<>();
		return events;
	}

	@Override
	public List<Event> getAllEventsBetweenDates(Date from, Date to) {
		List<Event> events = new ArrayList<>();
		return events;
	}

	@Override
	public List<Event> getEventsByName(String name) {
		List<Event> events = new ArrayList<>();
		return events;
	}

	@Override
	public Event getEventById() {
		return null;
	}

	@Override
	public void addEvent(Event event) {

	}

	@Override
	public void updateEvent(Event event) {

	}

	@Override
	public void deleteEvent(int id) {

	}

	@Override
	public void deleteEventBeforeDate(Date date) {

	}

}
