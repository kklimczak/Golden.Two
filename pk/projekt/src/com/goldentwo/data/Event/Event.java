package com.goldentwo.data.Event;

import java.util.Date;

/**
 * Glowna klasa encji Event odpowiadajaca za strukture danych jednego wydarzenia.
 */
public class Event {
	
	/** Id wydarzenia. */
	private int id;
	
	/** Nazwa wydarzenia. */
	private String name;
	
	/** Opis wydarzenia. */
	private String description;
	
	/** Miejsce wydarzenia. */
	private String place;
	
	/** Data i godzina wydarzenia. */
	private Date date;
	
	/** Data i godzina alarmu. */
	private Date alarm;
	
	/**
	 *	Glowny konstruktor klasy uzupelniajacy wszystkie pola w klasie
	 *
	 * @param id Unikalne id wydarzenia
	 * @param name Nazwa wydarzenia
	 * @param description Opis wydarzenia
	 * @param place Miejsce wydarzenia
	 * @param date Data i godzina wydarzenia
	 * @param alarm Data i godzina alarmu przypominajacego o wydarzeniu
	 */
	public Event(int id, String name, String description, String place, Date date, Date alarm) {
		this.name = name;
		this.description = description;
		this.place = place;
		this.date = date;
		this.alarm = alarm;
		this.id = id;
	}
	
	/**
	 * Metoda konwertujaca obiekt typu Event do typu EventDto (Data Transfer Object) wykorzystywanego do serializacji encji do xmla.
	 *
	 * @return przekonwertowany obiekt encji na obiekt gotowy do serializacji (EventDto)
	 */
	public EventDto asDto() {
		return new EventDto(id, name, description, place, date, alarm);
	}

	/**
	 * Zwraca id wydarzenia.
	 *
	 * @return Id wydarzenia
	 */
	public int getId() {
		return id;
	}

	/**
	 * Ustawia id wydarzenia.
	 *
	 * @param id Id obiektu
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Zwraca nazwe wydarzenia
	 *
	 * @return Nazwa wydarzenia
	 */
	public String getName() {
		return name;
	}

	/**
	 * Ustawia nazwe wydarzenia.
	 *
	 * @param name Nazwa wydarzenia
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Zwraca opis wydarzenia.
	 *
	 * @return Opis wydarzenia
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Ustawia opis wydarzenia.
	 *
	 * @param description Opis wydarzenia
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Zwraca miejsce wydarzenia.
	 *
	 * @return Miejsce wydarzenia
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Ustawia miejsce wydarzenia.
	 *
	 * @param place Miejsce wydarzenia
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Zwraca date wydarzenia.
	 *
	 * @return Data wydarzenia
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Ustawia date wydarzenia.
	 *
	 * @param date Data wydarzenia
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Zwraca alarm wydarzenia.
	 *
	 * @return Alarm wydarzenia
	 */
	public Date getAlarm() {
		return alarm;
	}

	/**
	 * Ustawia alarm wydarzenia.
	 *
	 * @param alarm Alarm wydarzenia
	 */
	public void setAlarm(Date alarm) {
		this.alarm = alarm;
	}
	
	
}
