package com.goldentwo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import com.goldentwo.utils.Date.DateConverter;

public class DeleteOldEventsFrame extends JFrame implements ActionListener {

	private UserInterface ui;
	private JFormattedTextField date;
	private JLabel label1, label2;
	private JButton execute, cancel;
	
	public DeleteOldEventsFrame(UserInterface ui){
		this.ui = ui;
		init();
	}
	
	private void init() {
		 setTitle("Remove old events");
		 setSize(270, 220);
		 setLocationRelativeTo(null);	
		 setLayout(null);
	     setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     setAlwaysOnTop(true);
	     date = null;
	     label1 = new JLabel("You will delete all events");
	     label2 = new JLabel("before this date");
	     execute = new JButton("Execute");
	     cancel = new JButton("Cancel");
	     
	     printComponents();
	}

	private void printComponents() {
		MaskFormatter mask = null;
		try{
			mask = new MaskFormatter("##-##-####");
		}catch(ParseException e){
			e.printStackTrace();
		}
		mask.setPlaceholderCharacter('@');
		
		date = new JFormattedTextField(mask);
		date.setBounds(10, 100, 250, 30);
		
		add(date);
		
		label1.setBounds(10, 40, 250, 20);
		label2.setBounds(10, 60, 250, 20);
		add(label1);
		add(label2);
		
		execute.setBounds(10, 170, 120, 30);
		execute.addActionListener(this);
		cancel.setBounds(140, 170, 120, 30);
		add(execute);
		add(cancel);
		
	}
	
	private boolean checkAndExecute(){
		if(date.getText().equals("@@-@@-@@@@")){
			return false;
		}
		
		String[] dateSplit = date.getText().split("-");
		
		if(dateSplit[0] == "00" || Integer.parseInt(dateSplit[0]) > 31 || 
		   dateSplit[1] == "00" || Integer.parseInt(dateSplit[1]) > 12){
			return false;
		}
		
		ui.dataServiceImpl.deleteEventBeforeDate(DateConverter.stringToDate(Integer.parseInt(dateSplit[2]), 
																			Integer.parseInt(dateSplit[1]), 
																			Integer.parseInt(dateSplit[0])));
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == execute){
			generateMessage(checkAndExecute());
			ui.calendarFrame.fillDayButtons();
			ui.calendarFrame.setEventsIntoCalendar();
			ui.listFrame.update();
		}
		if(e.getSource() == cancel){
			dispose();
		}
		
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
	
	
}
