package com.goldentwo.utils.alarmChecker;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;

public class AlarmChecker extends JFrame implements Runnable {

	private Event comingEvent;
	private Date comingAlarmDate;
	private DataServiceImpl ds;
	private Calendar cal;
	private int sleepTime;
	
	public AlarmChecker(DataServiceImpl ds) {
		this.ds = ds;
		sleepTime = 1000;
		cal = Calendar.getInstance();
		comingEvent = null;
		comingAlarmDate = null;
		
		loadComingEvent();
	}
	
	private void loadComingEvent(){
		/*
		 * TODO implement loadComingEvent()
		 */
	}
	
	private String generateAlarmMessage(){
		String str = "EVENT IS NEAR!\n";
		str += "Event name: " + comingEvent.getName() + 
			   "\nEvent place: " + comingEvent.getPlace() + 
			   "\nEvent date: " + comingEvent.getDate(); 
		
		return str;
	}
	
	@Override
	public void run() {
		while(true){
			
			try{
				Thread.sleep(sleepTime);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}


			cal.add(Calendar.MILLISECOND, sleepTime);		
			
			if(comingAlarmDate != null && cal.getTime().compareTo(comingAlarmDate) > 0){
				new Thread(){
					public void run(){
						JOptionPane.showMessageDialog(null,
								  generateAlarmMessage(), 
								  "ALARM", 
								  JOptionPane.INFORMATION_MESSAGE);
						currentThread().interrupt();
					}
				}.start();

				loadComingEvent();
			}
		}
	}
}
