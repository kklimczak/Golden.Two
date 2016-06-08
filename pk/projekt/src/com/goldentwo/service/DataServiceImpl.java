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
import com.goldentwo.utils.Logger.Logger;
import com.goldentwo.utils.Pagination.Direction;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Page;
import com.goldentwo.utils.Pagination.Sort;

public class DataServiceImpl implements DataService {
	
	private DBConnection dbc = new DBConnection();
	
	private EventRepository eventRepository = new EventRepository(dbc);
	
	private Logger logger = new Logger(DataServiceImpl.class);

	@Override
	public List<Event> getAllEvents() {
		logger.info("getAllEvents() called");
		return eventRepository.findAll();
	}
	
	@Override
	public Page<Event> getSortedAndFilteredEvents(Sort sort, Filter filter, int page) {
		if(sort == null) {
			sort = new Sort("id", Direction.ASC);
		}
		logger.info("getSortedAndFilteredEvents() called");
		return eventRepository.findEventWithSortAndFilterParams(sort, filter, page);
	}
	
	@Override
	public Page<Event> getSortedAndFilteredEventsWithAlarm(Sort sort, Filter filter, int page) {
		if(sort == null) {
			sort = new Sort("id", Direction.ASC);
		}
		logger.info("getSortedAndFilteredEventsWithAlarm() called");
		return eventRepository.findEventWithSortAndFilterParamsAndAlarmNotNull(sort, filter, page);
	}

	@Override
	public List<Event> getAllEventsBetweenDates(Date from, Date to, boolean isEvent) {
		logger.info("getAllEventsBetweenDates() called");
		return eventRepository.findByDateRange(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()), isEvent);
	}
	
	@Override
	public Page<Event> getAllSortedAndFilteredEventsBetweenDates(Date from, Date to, boolean isEvent, Sort sort, Filter filter, int page) {
		if(sort == null) {
			sort = new Sort("id", Direction.ASC);
		}
		logger.info("getAllSortedAndFilteredEventsBetweenDates() called");
		return eventRepository.findByDateRangeWithSortAndFilterParams(from, to, isEvent, sort, filter, page);
	}

	@Override
	public Event getEventById(int id) {
		logger.info("getEventById() called");
		return eventRepository.findOne(id);
	}

	@Override
	public void addEvent(Event event) {
		logger.info("addEvent() called");
		eventRepository.addOne(event);
	}

	@Override
	public void updateEvent(Event event) {
		logger.info("updateEvent() called");
		eventRepository.updateOne(event);
	}

	@Override
	public void deleteEvent(int id) {
		logger.info("deleteEvent() called");
		eventRepository.deleteOne(id);
	}

	@Override
	public void deleteEventBeforeDate(Date date) {
		logger.info("deleteEventsBeforeDate() called");
		eventRepository.deleteEventsBeforeDate(new java.sql.Date(date.getTime()));
	}
	
	@Override
	public void allEventsToXml() {
		EventsDto eventsDto = new EventsDto(new ArrayList<>());
		
		eventsDto.setEventDtos(getAllEvents().stream().map(Event::asDto).collect(Collectors.toList()));
		try {
			JAXBContext context = JAXBContext.newInstance(eventsDto.getClass());
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(eventsDto, new File ("events.xml"));
			logger.info("Export all events to xml file: events.xml with " + eventsDto.getEventDtos().size() + " elements" );
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
		}
	}
	
	@Override
	public List<Event> allEventsFromXml(File file) {
		EventsDto eventsDto = new EventsDto();
		try {
			JAXBContext context = JAXBContext.newInstance(eventsDto.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			eventsDto = (EventsDto) unmarshaller.unmarshal(file);
			logger.info("Import all events from file: events.xml with " + eventsDto.getEventDtos().size() + " elements");
			return eventsDto.getEventDtos().stream().map(EventDto::asDefault).collect(Collectors.toList());
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
			return null;
		} catch (NullPointerException exception) {
			logger.error("Cannot import XML file: You have empty file");
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
			logger.info("Export event with name: " + event.getName() + " and id: " + event.getId() + " to " + event.getId()+"."+event.getName()+".xml");
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
		}
	}
	
	@Override
	public Event oneEventFromXml(File file) {
		EventDto event = new EventDto();
		try {
			JAXBContext context = JAXBContext.newInstance(event.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			event = (EventDto) unmarshaller.unmarshal(file);
			logger.info("Import event from: " + file.getAbsolutePath() + " as event with name: " + event.getName() + " and id: " + event.getId());
			return event.asDefault();
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
			return null;
		}
	}

}
