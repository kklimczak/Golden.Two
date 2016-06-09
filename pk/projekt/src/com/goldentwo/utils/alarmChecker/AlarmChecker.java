package com.goldentwo.utils.alarmChecker;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;

public class AlarmChecker extends JFrame implements Runnable {

	private volatile Event comingEvent;
	private volatile Date comingAlarmDate;
	private DataServiceImpl ds;
	private Calendar cal;
	private long sleepTime;
	private volatile boolean isRunning;
	
	public AlarmChecker(DataServiceImpl ds) {
		this.ds = ds;
		sleepTime = 1000;
		cal = Calendar.getInstance();
		comingEvent = null;
		comingAlarmDate = null;
		isRunning = false;
		
		loadComingEvent();
	}
	
	public void loadComingEvent(){
		comingEvent = ds.getEventWithClosestAlarm();
		
		if(comingEvent != null){
			comingAlarmDate = comingEvent.getAlarm();
			cal = Calendar.getInstance();
			resumeThread();
			sleepTime = calculateSleepTime();
		}
	}
	
	private long calculateSleepTime(){
		return comingAlarmDate.getTime() - cal.getTimeInMillis();
	}
	
	private String generateAlarmMessage(){
		String str = "EVENT IS NEAR!\n\n";
		str += "Event name: " + comingEvent.getName() + 
			   "\nEvent place: " + comingEvent.getPlace() + 
			   "\nEvent date: " + comingEvent.getDate(); 
		
		return str;
	}
	
	public void pauseThread(){
		isRunning = false;
	}
	
	public void resumeThread(){
		isRunning = true;
	}
	
	private void deleteAlarm(){
		ds.updateEvent(new Event(comingEvent.getId(), 
				 comingEvent.getName(), 
				 comingEvent.getDescription(), 
				 comingEvent.getPlace(), 
				 comingEvent.getDate(), 
				 null));
	}
	
	@Override
	public void run() {
		
		while(true){
			
			while(isRunning){
				
				try{
					Thread.sleep(sleepTime);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				

				new Thread(){
					public void run(){
						JOptionPane.showMessageDialog(null,
													  generateAlarmMessage(), 
													  "ALARM", 
													  JOptionPane.INFORMATION_MESSAGE);
						currentThread().interrupt();
					}
				}.start();

				deleteAlarm();
				loadComingEvent();				
				if(comingEvent == null){
					isRunning = false;
					break;
				}
			}//END OF ALARM CHECKING LOOP	
			
			try{
				Thread.sleep(1000);				//Out of alarms. Thread is waiting for new alarm event
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}//END OF THREAD LOOP
	}
}
