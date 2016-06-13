package com.goldentwo.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.goldentwo.data.Event.Event;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Page;
import com.goldentwo.utils.Pagination.Sort;

/**
 * Interfejs definiujacy wszystkie metody zwiazane z logika wydarzen.
 */
public interface DataService {
	
	/**
	 * Zwraca liste wszystkich wydarzen.
	 *
	 * @return Lista wydarzen
	 */
	List<Event> getAllEvents();
	
	/**
	 * Zwraca posortowane i przefiltrowane wydarzenia.
	 *
	 * @param sort Sortowanie
	 * @param filter Filtrowanie
	 * @param page Numer strony
	 * @return Strona z wydarzeniami
	 */
	Page<Event> getSortedAndFilteredEvents(Sort sort, Filter filter, int page);
	
	/**
	 * Zwraca posortowane i przefiltrowane wydarzenia posiadajace alarm.
	 *
	 * @param sort Sortowanie
	 * @param filter Filtrowanie
	 * @param page Numer strony
	 * @return Strona z wydarzeniami
	 */
	Page<Event> getSortedAndFilteredEventsWithAlarm(Sort sort, Filter filter, int page);
	
	/**
	 * Zwraca liste wydarzen z podanego zakresu dat.
	 *
	 * @param from Data od keidy
	 * @param to Data do kiedy
	 * @param isEvent flaga zwiazana z wyszukiwaniem po dacie wydarzenia czy po dacie alarmu
	 * @return Lista wydarzen
	 */
	List<Event> getAllEventsBetweenDates(Date from, Date to, boolean isEvent);
	
	/**
	 * Zwraca posortowane i przefiltrowane wydarzenia z danego zakresu dat.
	 *
	 * @param from Data od keidy
	 * @param to Data do kiedy
	 * @param isEvent flaga zwiazana z wyszukiwaniem po dacie wydarzenia czy po dacie alarmu
	 * @param sort Sortowanie
	 * @param filter Filtrowanie
	 * @param page Numer strony
	 * @return Strona z wydarzeniami
	 */
	Page<Event> getAllSortedAndFilteredEventsBetweenDates(Date from, Date to, boolean isEvent, Sort sort, Filter filter, int page);
	
	/**
	 * Zwraca pojedynczy obiekt wydarzenia o podanym id.
	 *
	 * @param id Id wydarzenia
	 * @return Obiekt wydarzenia
	 */
	Event getEventById(int id);
	
	/**
	 * Zwraca pojedynczy obiekt wydarzenia z najblizsza data alarmu.
	 *
	 * @return Obiekt wydarzenia
	 */
	Event getEventWithClosestAlarm();
	
	/**
	 * Dodaje jedno wydarzenie do repozytorium.
	 *
	 * @param event Obiekt wydarzenia
	 */
	void addEvent(Event event);
	
	/**
	 * Aktualizuje obiekt wydarzenia o podanym id.
	 *
	 * @param event Obiekt wydarzenia
	 */
	void updateEvent(Event event);
	
	/**
	 * Usuwa element z repozytorium.
	 *
	 * @param id Id wydarzenia
	 */
	void deleteEvent(int id);
	
	/**
	 * Usuwa wszytkie wydarzenia starsze niz podana data.
	 *
	 * @param date Data
	 */
	void deleteEventBeforeDate(Date date);
	
	/**
	 * Eksportuje wszytkie wydarzenia z bazy do jednego pliku xml.
	 */
	void allEventsToXml();
	
	/**
	 * Importuje liste wydarzen do bazy z pliku xml.
	 *
	 * @param file Plik z wydarzeniami
	 * @return Status powodzenia
	 */
	boolean allEventsFromXml(File file);
	
	/**
	 * Eksportuje podane wydarzenie do pojedynczego pliku xml.
	 *
	 * @param event Obiekt wydarzenia
	 */
	void oneEventToXml(Event event);
	
	/**
	 * Importuje pojedyncze wydarzenie z pliku xml do bazy.
	 *
	 * @param file Plik z wydarzeniem
	 * @return Status powodzenia
	 */
	boolean oneEventFromXml(File file);
	
	/**
	 * Eksportuje pojedyncze wydarzenie do formatu ics.
	 *
	 * @param event Obiekt wydarzenia
	 * @return Status powodzenia
	 */
	boolean oneEventToIcs(Event event);
	
	boolean allEventsToIcs();
}
