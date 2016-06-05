package com.goldentwo.view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.goldentwo.data.Event.Event;

public class DayListEventsFrame extends JFrame {

	private List<Event> eventList;
	private JList<String> list;
	private JScrollPane scroll;
	
	public DayListEventsFrame(List<Event> eventList){
		this.eventList = eventList;
		list = null;
		scroll = null;
		
		init();
		
		printComponents();
	}

	private void init() {
	    setTitle("Day events");
	    setSize(400, 470);
	    setLocationRelativeTo(null);
		setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
	}

	private void printComponents() {
		String[] eventsStr = eventsToString();
		
		list = new JList<>(eventsStr);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		scroll = new JScrollPane(list);
		scroll.setBounds(10, 30, 380, 300);
		
		add(scroll);
		
	}

	private String[] eventsToString() {
		String[] str = new String[eventList.size()];
		String name, hour;
		
		Calendar c = new GregorianCalendar();
		
		int index = 0;
		for(Event e : eventList){
			c.setTime(e.getDate());
			name = e.getName();
			hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(c.get(Calendar.MINUTE));
			
			str[index++] = name + " at " + hour;
		}
		return str;
	}
}
