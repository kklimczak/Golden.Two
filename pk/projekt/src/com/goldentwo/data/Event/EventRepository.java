package com.goldentwo.data.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.goldentwo.data.database.DBConnection;
import com.goldentwo.utils.Date.DateConverter;
import com.goldentwo.utils.Logger.Logger;
import com.goldentwo.utils.Pagination.Direction;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Page;
import com.goldentwo.utils.Pagination.Sort;

/**
 * Klasa odpowidzialna za obsluge bazy danych dla tabeli z wydarzeniami (CRUD).
 */
public class EventRepository {
	
	/** Obiekt zawierajacy konfiguracje oraz polaczenie z baza danych. */
	private DBConnection db;
	
	/** Obiekt zawierajacy metody sluzace do logowania na konsoli. */
	private Logger logger = new Logger(EventRepository.class);
	
	/**
	 * Glowny konstruktor klasy EventRepository ustawiajacy obiekt klasy konfiguracyjnej bazy danych.
	 *
	 * @param dbConnection Klasa konfiguracyjna bazy danych.
	 */
	public EventRepository(DBConnection dbConnection) {
		this.db = dbConnection;
	}
	
	/**
	 * Metoda odpowiedzialna za wyciagniecie z bazy danych wszystkich elementow z tabeli "events", w ktorych miesiac wydarzenia jest zgodny
	 * z numerem miesiaca przekazanym przez parametr.
	 *
	 * @param month Miesiac
	 * @return Lista obiektow klasy Event
	 */
	public List<Event> findAll(int month) {
		try {
			List<Event> events = new ArrayList<>();
			ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM events WHERE MONTH(date) = " + month);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        events.add(new Event(id, name, description, place, date, alarm));
			}
			logger.info("findAll() in month: " + month);
			return events;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Zwraca liczbe wszystkich elementow w bazie danych wraz zaaplikowanym filtrowaniem.
	 *
	 * @param filter Filter
	 * @return Liczba wszystkich przefiltrowanych elementow 
	 */
	public int countAllEvents(Filter filter) {
		String query = "SELECT COUNT(*) FROM events";
		if (filter != null) {
			query += " WHERE `" + filter.getField() + "` LIKE '%" + filter.getValue() + "%'";
		}
		try {
			ResultSet resultSet = db.getStmt().executeQuery(query);
			resultSet.next();
			logger.info("countAllEvent() called");
			return resultSet.getInt(1);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Zwraca obiekt typu Page, ktory zawiera ograniczona liczbe elementow do rozmiaru strony, ktore sa posortowane i przefiltrowane.
	 * Jesli po przefiltrowaniu w bazie jest wiecej niz dzisiec elementow metoda zwroci tylko dziesiec, a kolejne elementy bedzie mozna pobrac
	 * po kolejnym wywolaniu tej metody wraz z nastepnym numerem strony.
	 * Dodatkowo metoda ta zwraca ilosc stron, ilosc wszystkich elementow w bazie, pojemnosc strony, aktualna strone oraz parametry filtru oraz
	 * sortowania.
	 *
	 * @param sort Ustawienia sortowania
	 * @param filter Ustawienia filtrowania
	 * @param page Numer strony
	 * @return Pojedyncza strona z obiektami oraz parametrami
	 */
	public Page<Event> findEventWithSortAndFilterParams(Sort sort, Filter filter, int page) {
		String query = "SELECT * FROM events";
		if (filter != null) {
			query += " WHERE `" + filter.getField() + "` LIKE '%" + filter.getValue() + "%'";
		}
		query += " ORDER BY " + sort.getField();
		if (sort.getDirection().equals(Direction.DESC)) {
			query += " DESC";
		}
		query += " LIMIT "+ (page-1)*10 + ", 10";
		try {
			List<Event> events = new ArrayList<Event>();
			ResultSet resultSet = db.getStmt().executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        events.add(new Event(id, name, description, place, date, alarm));
			}
			int amountOfEvents = countAllEvents(filter);
			Page<Event> data = new Page<Event>(events, amountOfEvents, (int)Math.ceil(amountOfEvents/10.0), 10, page, sort, filter);
			logger.info("findEventWithSortAndFilterParams() called and return " + amountOfEvents + " elements on page: " + page + " with sort: " + sort + " and filter: " + filter);
			return data;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Zwraca liczbe wydarzen w bazie ktore maja ustawiony alarm
	 *
	 * @param filter Ustawienia filtrowania
	 * @return Liczba elementow z alarmem
	 */
	public int countAllEventsWithAlarm(Filter filter) {
		String query = "SELECT COUNT(*) FROM events WHERE `alarm` IS NOT NULL";
		if (filter != null) {
			query += " AND `" + filter.getField() + "` LIKE '%" + filter.getValue() + "%'";
		}
		try {
			ResultSet resultSet = db.getStmt().executeQuery(query);
			resultSet.next();
			logger.info("countAllEvent() called");
			return resultSet.getInt(1);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Metoda ta dziala na tej samej zasadzie co findEventWithSortAndFilterParams, ktora dodatkowo pomija elementy, ktore nie maja alarmu.
	 *
	 * @param sort Ustawienia sortowania
	 * @param filter Ustawienia filtrowania
	 * @param page Numer strony
	 * @return Pojedyncza strona z obiektami oraz parametrami
	 */
	public Page<Event> findEventWithSortAndFilterParamsAndAlarmNotNull(Sort sort, Filter filter, int page) {
		String query = "SELECT * FROM events WHERE `alarm` IS NOT NULL";
		if (filter != null) {
			query += " AND `" + filter.getField() + "` LIKE '%" + filter.getValue() + "%'";
		}
		query += " ORDER BY " + sort.getField();
		if (sort.getDirection().equals(Direction.DESC)) {
			query += " DESC";
		}
		query += " LIMIT "+ (page-1)*10 + ", 10";
		try {
			List<Event> events = new ArrayList<Event>();
			ResultSet resultSet = db.getStmt().executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        events.add(new Event(id, name, description, place, date, alarm));
			}
			int amountOfEvents = countAllEventsWithAlarm(filter);
			Page<Event> data = new Page<Event>(events, amountOfEvents, (int)Math.ceil(amountOfEvents/10.0), 10, page, sort, filter);
			logger.info("findEventWithSortAndFilterParamsAndAlarmNotNull() called and return " + amountOfEvents + " elements on page: " + page + " with sort: " + sort + " and filter: " + filter);
			return data;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Metoda zwracajaca wszystkie wydarzenia z bazy.
	 *
	 * @return Lista wszystkich wydarzen
	 */
	public List<Event> findAll() {
		try {
			List<Event> events = new ArrayList<>();
			ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM events");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        events.add(new Event(id, name, description, place, date, alarm));
			}
			logger.info("findAll() called");
			return events;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Zwraca liczbe wydarzen pochodzacych z danego zakresu dat wydarzen lub ich alarmow.
	 *
	 * @param filter Ustawienia filtrowania
	 * @param from Data od kiedy
	 * @param to Data do kiedy
	 * @param isEvent Flaga mowiaca czy ma szukac po datach wydarzen czy datach alarmow
	 * @return Liczba elementow w bazie po przefiltrowaniu
	 */
	public int countAllEventsBetweenDates(Filter filter, Date from, Date to, boolean isEvent) {
		String query = "SELECT COUNT(*) FROM events";
		String dateColumn = isEvent ? "date" : "alarm";
		query += " WHERE " +dateColumn+ " >= '" + new java.sql.Date(from.getTime()) + "' AND date <= '" + new java.sql.Date(to.getTime()) + "'";
		if (filter != null) {
			query += " AND `" + filter.getField() + "` LIKE '%" + filter.getValue() + "%'";
		}
		try {
			ResultSet resultSet = db.getStmt().executeQuery(query);
			resultSet.next();
			logger.info("countAllEvent() called");
			return resultSet.getInt(1);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Metoda dziala identycznie jak findEventWithSortAndFilterParams dodatkowo przyjmujac za parametry zakres dat z ktorych maja pochodzic eventy
	 * rozrozniajac na zakres dat samych wydarzen jak i dat alarmow.
	 *
	 * @param from Data od kiedy
	 * @param to Data do kiedy
	 * @param isEvent Flaga mowiaca czy ma szukac po datach wydarzen czy datach alarmow
	 * @param sort Ustawienia sortowania
	 * @param filter Ustawienia filtrowania
	 * @param page Numer strony
	 * @return Pojedyncza strona z obiektami oraz parametrami
	 */
	public Page<Event> findByDateRangeWithSortAndFilterParams(Date from, Date to, boolean isEvent, Sort sort, Filter filter, int page) {
		String dateColumn = isEvent ? "date" : "alarm";
		String query = "SELECT * FROM events";
		query += " WHERE " +dateColumn+ " >= '" + new java.sql.Date(from.getTime()) + "' AND date <= '" + new java.sql.Date(to.getTime()) + "'";
		if (filter != null) {
			query += " AND `" + filter.getField() + "` LIKE '%" + filter.getValue() + "%'";
		}
		query += " ORDER BY " + sort.getField();
		if (sort.getDirection().equals(Direction.DESC)) {
			query += " DESC";
		}
		query += " LIMIT "+ (page-1)*10 + ", 10";
		try {
			List<Event> events = new ArrayList<Event>();
			ResultSet resultSet = db.getStmt().executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        events.add(new Event(id, name, description, place, date, alarm));
			}
			int amountOfEvents = countAllEventsBetweenDates(filter, from, to, isEvent);
			Page<Event> data = new Page<Event>(events, amountOfEvents, (int)Math.ceil(amountOfEvents/10.0), 10, page, sort, filter);
			logger.info("findByDateRangeWithSortAndFilterParams() called and return " + amountOfEvents + " elements on page: " + page + " with sort: " + sort + " and filter: " + filter + "with dates: " + from + " - " + to);
			return data;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Zwraca liste wszystkich wydarzen z danego zakresu dat.
	 *
	 * @param from Data od kiedy
	 * @param to Data do kiedy
	 * @param isEvent Flaga mowiaca czy ma szukac po datach wydarzen czy datach alarmow
	 * @return Lista wydarzen z danego przedzialu dat
	 */
	public List<Event> findByDateRange(Date from, Date to, boolean isEvent) {
		String dateColumn = isEvent ? "date" : "alarm";
		try {
			List<Event> events = new ArrayList<>();
			ResultSet resultSet = db.getStmt().executeQuery("Select * FROM events WHERE " +dateColumn+ " >= '" + new java.sql.Date(from.getTime()) + "' AND "+dateColumn+ " <= '" + new java.sql.Date(to.getTime()) + "'");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        events.add(new Event(id, name, description, place, date, alarm));
			}
			logger.info("findByDateRange() called with dates: " + from + " - " + to);
			return events;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Zwraca jedno wydarzenie z bazy na podstawie id wydarzenia.
	 *
	 * @param id Id wydarzenia
	 * @return the Obiekt wydarzenia
	 */
	public Event findOne(int id) {
		try {
			ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM events WHERE id = "+id);
			while(resultSet.next()) {
				int eventId = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        logger.info("findOne() called with id: " + id);
		        return new Event(eventId, name, description, place, date, alarm);
			}
			logger.error("Not found event with id: " + id);
			return null;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Zwraca jedno wydarzenie, w roznica miedzy data i godzina alarmu a czasem terazniejszym jest najmniejsza.
	 *
	 * @return Obiekt wydarzenia
	 */
	public Event findOneWithClosestAlarm() {
		try {
			ResultSet resultSet = db.getStmt().executeQuery("SELECT *, (TIMEDIFF(`alarm`, NOW())*24*60*60) AS diff FROM events WHERE alarm IS NOT NULL AND (TIMEDIFF(`alarm`, NOW())*24*60*60) >= 0 ORDER BY diff LIMIT 1");
			while(resultSet.next()) {
				int eventId = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        String description = resultSet.getString("description");
		        String place = resultSet.getString("place");
		        Date date = new Date(resultSet.getTimestamp("date").getTime());
		        Timestamp timestamp = resultSet.getTimestamp("alarm"); 
		        Date alarm = timestamp != null ? new Date (timestamp.getTime()) : null;
		        logger.info("findOneWithClosestAlarm() called with id: " + eventId);
		        return new Event(eventId, name, description, place, date, alarm);
			}
			return null;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Dodaje do bazy jedno wydarzenie.
	 *
	 * @param event Obiekt wydarzenia
	 */
	public void addOne(Event event) {
		try {
			db.getStmt().executeUpdate("INSERT INTO `events` VALUES (NULL, '"
					+event.getName()+"','"
					+event.getDescription()+"','"
					+event.getPlace()+"','"
					+DateConverter.dateToMySqlDateTimeString(event.getDate())
					+"', " + (event.getAlarm() != null
					? "'"+DateConverter.dateToMySqlDateTimeString(event.getAlarm())+"'"
							: "NULL") +")");
			logger.info("addOne() called with name: " + event.getName());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	/**
	 * Aktualizuje wydarzenie w bazie na podstawie podanego id.
	 *
	 * @param event Obiekt wydarzenia
	 */
	public void updateOne(Event event) {
		try {
			db.getStmt().executeUpdate("UPDATE `events` SET  name='"+event.getName()+"',description='"+event.getDescription()+"',place='"+event.getPlace()+"',date='"+DateConverter.dateToMySqlDateTimeString(event.getDate())+"',alarm=" + (event.getAlarm() != null ? "'"+DateConverter.dateToMySqlDateTimeString(event.getAlarm())+"'" : "NULL") +" WHERE id="+event.getId());
			logger.info("updateOne() called with id: " + event.getId());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	/**
	 * Usuwa wydarzenie o podanym id.
	 *
	 * @param id Id wydarzenia
	 */
	public void deleteOne(int id) {
		try {
			db.getStmt().executeUpdate("DELETE FROM `events` WHERE id = " + id);
			logger.info("deleteOne() called with id: " + id);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Usuwa wydarzenia starsze niz podana nazwa.
	 *
	 * @param date Data przed ktora maja byc usuniete wydarzenia
	 */
	public void deleteEventsBeforeDate(Date date) {
		try {
			db.getStmt().executeUpdate("DELETE FROM `events` WHERE date < '" + DateConverter.dateToMySqlDateTimeString(date) + "'");
			logger.info("deleteEventsBeforeDate() called with date: " + date);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
