package com.goldentwo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.utils.Date.DateConverter;

@SuppressWarnings("serial")
public class AddEventFrame extends JFrame implements ActionListener{
	
	private DataServiceImpl ds;
	private JTextField name, place;
	private JTextArea description;
	private JFormattedTextField date, dateTime, alarm, alarmTime;
	private JCheckBox alarmCheckBox;
	private JLabel nameL, placeL, descL, dateL;
	private JButton accept, cancel;
	private JOptionPane message;

	public AddEventFrame(DataServiceImpl ds){
		this.ds = ds;
		init();
	}
	
	
	private void init(){
	    setTitle("Event manager");
	    setSize(300, 470);
	    setLocationRelativeTo(null);
		setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        
        print();
        printButtons();
	}
	
	private void print(){
		name = new JTextField();
		name.setBounds(10, 40, 280, 30);
		add(name);
		nameL = new JLabel("Event name");
		nameL.setBounds(10, 20, 280, 20);
		add(nameL);

		description = new JTextArea();
		description.setBounds(10, 100, 280, 60);
		description.setBorder(name.getBorder());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		descL = new JLabel("Description (max. 500 char)");
		descL.setBounds(10, 80, 280, 20);
		add(descL);
		
		JScrollPane scroll = new JScrollPane(description);
		scroll.setBounds(10, 100, 280, 60);
		add(scroll);
		
		place = new JTextField();
		place.setBounds(10, 190, 280, 30);
		add(place);
		placeL = new JLabel("Place name");
		placeL.setBounds(10, 170, 280, 20);
		add(placeL);
		
		MaskFormatter dateMask = null;
		MaskFormatter timeMask = null;
		try {
			dateMask = new MaskFormatter("##-##-####");
			timeMask = new MaskFormatter("##:##");
		}catch(ParseException e){
			e.printStackTrace();
		}
		dateMask.setPlaceholderCharacter('@');
		timeMask.setPlaceholderCharacter('$');
		date = new JFormattedTextField(dateMask);
		date.setBounds(10, 250, 130, 30);
		add(date);
		dateTime = new JFormattedTextField(timeMask);
		dateTime.setBounds(155, 250, 45, 30);
		add(dateTime);
		dateL = new JLabel("Date (DD-MM-YYYY    HH:MM)");
		dateL.setBounds(10, 230, 280, 20);
		add(dateL);
		
		alarmCheckBox = new JCheckBox("Set alarm");
		alarmCheckBox.setBounds(10, 310, 280, 30);
		alarmCheckBox.addActionListener(this);
		add(alarmCheckBox);
		
		alarm = new JFormattedTextField(dateMask);
		alarm.setBounds(10, 340, 130, 30);
		alarmTime = new JFormattedTextField(timeMask);
		alarmTime.setBounds(155, 340, 45, 30);
		add(alarmTime);
		alarm.setVisible(false);
		alarmTime.setVisible(false);
		add(alarm);
	}
	
	private void printButtons(){
		accept = new JButton("Accept");
		accept.setBounds(10, 420, 100, 40);
		add(accept);
		accept.addActionListener(this);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(190, 420, 100, 40);
		add(cancel);
		cancel.addActionListener(this);
	}
	
	private boolean addNewEvent(){
		String[] dateParts = new String[3];
		String[] timeParts = new String[2];
		String[] alarmParts = null;
		String[] aTimeParts = null;
		String eName, eDesc, ePlace;
				
		if(name.getText().isEmpty() || description.getText().isEmpty() || place.getText().isEmpty()) {
			return false;
		}
		
		if(date.getText().equals("@@-@@-@@@@") || dateTime.getText().equals("$$:$$")){
			return false;
		}
		
		if(alarmCheckBox.isSelected()){
			if(alarm.getText().equals("@@-@@-@@@@") || alarmTime.getText().equals("$$:$$")){
				return false;
			}
			
			alarmParts = alarm.getText().split("-");
			aTimeParts = alarmTime.getText().split(":");
			
			if(!dateCheck(alarmParts, aTimeParts)){
				return false;
			}
		}
		
		dateParts = date.getText().split("-");
		timeParts = dateTime.getText().split(":");
		
		if(!dateCheck(dateParts, timeParts)){
			return false;
		}
		
		eName = name.getText();
		eDesc = description.getText();
		ePlace = place.getText();

		ds.addEvent(createEvent(eName, eDesc, ePlace, dateParts, timeParts, alarmParts, aTimeParts));
		return true;
	}

	private Event createEvent(String name, String desc, String place, String[] dateParts, String[] timeParts, String[] alarmParts, String[] aTimeParts){
		
		Date alarmDate = null;
		Date eventDate = DateConverter.convertToDateWithTime(Integer.parseInt(dateParts[2]), 
															 Integer.parseInt(dateParts[1]), 
															 Integer.parseInt(dateParts[0]), 
															 Integer.parseInt(timeParts[0]), 
															 Integer.parseInt(timeParts[1]), 
															 0);
		System.out.println(eventDate);
		if(alarmParts != null){
			alarmDate = DateConverter.convertToDateWithTime(Integer.parseInt(alarmParts[2]), 
															Integer.parseInt(alarmParts[1]), 
															Integer.parseInt(alarmParts[0]), 
															Integer.parseInt(aTimeParts[0]), 
															Integer.parseInt(aTimeParts[1]), 
															0);
			
		}
		System.out.println(alarmDate);
		
		return new Event(1, name, desc, place, eventDate, alarmDate);
	}

	private boolean dateCheck(String[] date, String[] time) {
		if(date[0] == "00" || Integer.parseInt(date[0]) > 31 || date[1] == "00" || Integer.parseInt(date[1]) > 12){
			return false;
		}

		if(Integer.parseInt(time[0]) > 23 || Integer.parseInt(time[1]) > 59){
			return false;
		}
		return true;
		
	}

	private void generateMessage(boolean flag){
		if(flag){
			JOptionPane.showMessageDialog(this,
					  "Success!",
					  "Message",
					  JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this,
					  "Incorrect data!",
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == alarmCheckBox){
			if(alarmCheckBox.isSelected()){
				alarm.setVisible(true);
				alarmTime.setVisible(true);
			} else {
				alarm.setVisible(false);
				alarmTime.setVisible(false);
			}
		}
		
		if(source == accept){
			generateMessage(addNewEvent());
		}
		
		
	}
}
