package com.goldentwo.data.Event;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Events")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class EventsDto {

	private List<EventDto> eventDtos;

	public EventsDto() {};
	
	public EventsDto(List<EventDto> eventDtos) {
		this.eventDtos = eventDtos;
	}

	public List<EventDto> getEventDtos() {
		return eventDtos;
	}

	public void setEventDtos(List<EventDto> eventDtos) {
		this.eventDtos = eventDtos;
	}
	
}
