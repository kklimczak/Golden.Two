package com.goldentwo.utils.alarmChecker;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;

public class AlarmChecker extends JFrame implements Runnable {

	private volatile Event comingEvent;
	private volatile Date comingAlarmDate;
	private DataServiceImpl ds;
	private Calendar cal;
	private int sleepTime;
	private volatile boolean isRunning;
	private String soundPath;
	
	public AlarmChecker(DataServiceImpl ds) {
		this.ds = ds;
		sleepTime = 1000;
		cal = Calendar.getInstance();
		comingEvent = null;
		comingAlarmDate = null;
		isRunning = false;
		soundPath = "audio/alarmSound.wav";
		
		loadComingEvent();
	}
	
	public void setSoundPath(String soundPath){
		this.soundPath = soundPath;
	}
	
	public String getSoundPath(){
		return soundPath;
	}
	
	public void loadComingEvent(){
		comingEvent = ds.getEventWithClosestAlarm();
		comingAlarmDate = null;
		if(comingEvent != null){
			comingAlarmDate = comingEvent.getAlarm();
			cal = Calendar.getInstance();
			resumeThread();
		}
	}
	
	private String generateAlarmMessage(Event e){
		String str = "EVENT IS NEAR!\n\n";
		str += "Event name: " + e.getName() + 
			   "\nEvent place: " + e.getPlace() + 
			   "\nEvent date: " + e.getDate(); 
		
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
	
	public void makeAlarmSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
	    File yourFile = new File(soundPath);
	    AudioInputStream stream;
	    AudioFormat format;
	    DataLine.Info info;
	    Clip clip;

	    stream = AudioSystem.getAudioInputStream(yourFile);
	    format = stream.getFormat();
	    info = new DataLine.Info(Clip.class, format);
	    clip = (Clip) AudioSystem.getLine(info);
	    clip.open(stream);
	    clip.start();
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
				
				cal.add(Calendar.MILLISECOND, sleepTime);
				
				if(comingAlarmDate != null && cal.getTime().after(comingAlarmDate)){
					Event comingEventCopy = comingEvent;
					new Thread(){
						public void run(){						
							
							try {
								makeAlarmSound();
								JOptionPane.showMessageDialog(null,
										  generateAlarmMessage(comingEventCopy), 
										  "ALARM", 
										  JOptionPane.INFORMATION_MESSAGE);
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
								e1.printStackTrace();	
							}
							
							interrupt();
						}
					}.start();	

					deleteAlarm();
					loadComingEvent();
				}
				
				
				if(comingEvent == null){
					isRunning = false;
					break;
				}
			}//END OF ALARM CHECKING LOOP	
			
			try{
				Thread.sleep(sleepTime);				//Out of alarms. Thread is waiting for new alarm event
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}//END OF THREAD LOOP
	}
}
