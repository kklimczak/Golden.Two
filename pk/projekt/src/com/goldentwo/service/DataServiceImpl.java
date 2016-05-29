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
import com.goldentwo.data.Event.EventRepository;
import com.goldentwo.data.Event.EventsDto;
import com.goldentwo.data.database.DBConnection;
import com.goldentwo.utils.Pagination.Direction;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Page;
import com.goldentwo.utils.Pagination.Sort;

public class DataServiceImpl implements DataService {
	
	private DBConnection dbc = new DBConnection();
	
	private EventRepository eventRepository = new EventRepository(dbc);

	@Override
	public List<Event> getAllEvents(int month) {
		return eventRepository.findAll(month);
	}

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}
	
	@Override
	public Page<Event> getSortedAndFilteredEvents(Sort sort, Filter filter, int page) {
		if(sort == null) {
			sort = new Sort("id", Direction.ASC);
		}
		return eventRepository.findEventWithSortAndFilterParams(sort, filter, page);
	}

	@Override
	public List<Event> getAllEventsBetweenDates(Date from, Date to) {
		return eventRepository.findByDateRange(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));
	}

	@Override
	public Event getEventById(int id) {
		return eventRepository.findOne(id);
	}

	@Override
	public void addEvent(Event event) {
		eventRepository.addOne(event);
	}

	@Override
	public void updateEvent(Event event) {
		eventRepository.updateOne(event);
	}

	@Override
	public void deleteEvent(int id) {
		eventRepository.deleteOne(id);
	}

	@Override
	public void deleteEventBeforeDate(Date date) {
		eventRepository.deleteEventsBeforeDate(new java.sql.Date(date.getTime()));
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
	
	@Override
	public void oneEventToXml(Event event) {
		try {
			JAXBContext context = JAXBContext.newInstance(event.asDto().getClass());
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(event.asDto(), new File(event.getId()+"."+event.getName()+".xml"));
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
		}
	}
	
	@Override
	public Event oneEventFromXml(String pathname) {
		EventDto event = new EventDto();
		try {
			File file = new File(pathname);
			JAXBContext context = JAXBContext.newInstance(event.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			event = (EventDto) unmarshaller.unmarshal(file);
			return event.asDefault();
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
			return null;
		}
	}

}
