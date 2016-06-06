package com.goldentwo.utils.alarmChecker;

import javax.swing.JFrame;

public class AlarmChecker implements Runnable {

	@Override
	public void run() {
		while(true){
			
			try{
				Thread.sleep(60000);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			
			/*
			 * TODO method that returns coming alarm
			 */
			/*
			 * TODO checking method if alarm date == current date
			 */
			/*
			 * TODO if previous is true, print message
			 */
		}
		
	}

	
}
