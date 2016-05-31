package com.goldentwo.data.Event;

import java.util.Date;

public class Event {
	
	private int id;
	private String name;
	private String description;
	private String place;
	private Date date;
	private Date alarm;
	
	public Event(int id, String name, String description, String place, Date date, Date alarm) {
		this.name = name;
		this.description = description;
		this.place = place;
		this.date = date;
		this.alarm = alarm;
		this.id = id;
	}
	
	public EventDto asDto() {
		return new EventDto(id, name, description, place, date, alarm);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getAlarm() {
		return alarm;
	}

	public void setAlarm(Date alarm) {
		this.alarm = alarm;
	}
	
	
}
