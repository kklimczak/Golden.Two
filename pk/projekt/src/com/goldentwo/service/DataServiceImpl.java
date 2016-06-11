package com.goldentwo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.model.property.Version;

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
	public Event getEventWithClosestAlarm() {
		logger.info("getEventWithClosestAlarm() called");
		return eventRepository.findOneWithClosestAlarm();
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
	public boolean allEventsFromXml(File file) {
		EventsDto eventsDto = new EventsDto();
		try {
			JAXBContext context = JAXBContext.newInstance(eventsDto.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			eventsDto = (EventsDto) unmarshaller.unmarshal(file);
			logger.info("Import all events from file: events.xml with " + eventsDto.getEventDtos().size() + " elements");
			List<Event> events = eventsDto.getEventDtos().stream().map(EventDto::asDefault).collect(Collectors.toList());
			for (Event event : events) {
				addEvent(event);
			}
			return true;
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
			return false;
		} catch (NullPointerException exception) {
			logger.error("Cannot import XML file: You have empty file");
			return false;
		} catch (ClassCastException e){
			logger.error("Error in XML file class casting");
			return false;
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
	public boolean oneEventFromXml(File file) {
		EventDto event = new EventDto();
		try {
			JAXBContext context = JAXBContext.newInstance(event.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			event = (EventDto) unmarshaller.unmarshal(file);
			addEvent(event.asDefault());
			logger.info("Import event from: " + file.getAbsolutePath() + " as event with name: " + event.getName() + " and id: " + event.getId());
			return true;
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean oneEventToIcs(Event event) {
		DateTime dateTime = new DateTime(event.getDate());
		VEvent vEvent = new VEvent(dateTime, event.getName());
		vEvent.getProperties().add(new Description(event.getDescription()));
		vEvent.getProperties().add(new Location(event.getPlace()));
		
		Calendar calendar = new Calendar();
		calendar.getComponents().add(vEvent);
		calendar.getProperties().add(new ProdId("-//Ximian//NONSGML Evolution Calendar//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(Method.PUBLISH);
		try {
			File file = new File("event.ics");
			FileOutputStream fos = new FileOutputStream(file);
			CalendarOutputter calendarOutputter = new CalendarOutputter();
			calendarOutputter.setValidating(false);
			calendarOutputter.output(calendar, fos);
			return true;
		} catch (IOException ioException) {
			ioException.printStackTrace();
			return false;
		} catch (ValidationException validationException) {
			validationException.printStackTrace();
			return false;
		}
	}

}
