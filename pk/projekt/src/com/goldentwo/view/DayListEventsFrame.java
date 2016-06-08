package com.goldentwo.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;

public class DayListEventsFrame extends JFrame implements ActionListener {

	private List<Event> eventList;
	private JList<String> list;
	private JScrollPane scroll;
	private JButton details, export, delete;
	private UserInterface ui;
	
	public DayListEventsFrame(List<Event> eventList, UserInterface ui){
		this.eventList = eventList;
		this.ui = ui;
		list = null;
		scroll = null;
		details = null;
		export = null;
		delete = null;
		
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
		list.setFont(new Font("Arial", Font.PLAIN, 19));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedIndex() != -1){
					details.setEnabled(true);
					export.setEnabled(true);
					delete.setEnabled(true);
				}
			}
		});
		
		scroll = new JScrollPane(list);
		scroll.setBounds(10, 30, 380, 300);
		
		details = new JButton("Details");
		details.setBounds(10, 340, 100, 30);
		details.addActionListener(this);
		details.setEnabled(false);
		
		export = new JButton("Export to XML");
		export.setBounds(10, 380, 140, 30);
		export.addActionListener(this);
		export.setEnabled(false);
		
		delete = new JButton("DELETE");
		delete.setBounds(290, 340, 100, 30);
		delete.addActionListener(this);
		delete.setEnabled(false);
		
		add(scroll);
		add(details);
		add(export);
		add(delete);
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == details){
			int i = list.getSelectedIndex();
			new AddEventFrame(new DataServiceImpl(), eventList.get(i)).setVisible(true);
			dispose();
		}
		
		if(source == delete){
			int i = list.getSelectedIndex();
			if(UserInterface.generateQuestion(this) == 0){
				ui.dataServiceImpl.deleteEvent(eventList.get(i).getId());
				UserInterface.generateMessage(true, this);
				ui.listFrame.update();	
			}
			dispose();
		}
		
		if(source == export){
			int i = list.getSelectedIndex();
			ui.dataServiceImpl.oneEventToXml(eventList.get(i));
			UserInterface.generateMessage(true, this);
			dispose();
		}
		
	}
}
