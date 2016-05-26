package com.goldentwo.data.Alarm;

import java.util.Date;

import com.goldentwo.data.Event.Event;

public class Alarm {

	private int id;
	private String name;
	private Date date;
	private Event event;

	public Alarm(String name, Date date, Event event) {
		this.name = name;
		this.date = date;
		this.event = event;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	
}
