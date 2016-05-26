package com.goldentwo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.goldentwo.data.Event.Event;
import com.goldentwo.data.Event.EventDto;
import com.goldentwo.data.Event.EventsDto;

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
	
	@Override
	public void allEventsToXml() {
		EventsDto eventsDto = new EventsDto(new ArrayList<>());
		
		for(int i = 0; i < 10; i++) {
			eventsDto.getEventDtos().add(new EventDto(i, "name", "desc", "place", new Date()));
		}
		try {
			JAXBContext context = JAXBContext.newInstance(eventsDto.getClass());
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(eventsDto, new File ("events.xml"));
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
		}
	}
	
	@Override
	public List<Event> allEventsFromXml() {
		EventsDto eventsDto = new EventsDto();
		try {
			File file = new File ("events.xml");
			JAXBContext context = JAXBContext.newInstance(eventsDto.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			eventsDto = (EventsDto) unmarshaller.unmarshal(file);
			return eventsDto.getEventDtos().stream().map(EventDto::asDefault).collect(Collectors.toList());
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
			return null;
		}
	}

}
