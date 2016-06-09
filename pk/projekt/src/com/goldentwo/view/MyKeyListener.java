package com.goldentwo.view;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class MyKeyListener implements KeyEventDispatcher {

	private UserInterface ui;
	
	public MyKeyListener(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(e.getID() == KeyEvent.KEY_PRESSED){

			
			if(keyCode == KeyEvent.VK_F9){
				ui.menu.doClick();
			}
						
			if(keyCode == KeyEvent.VK_KP_LEFT){
				if(ui.calComponentList.get(0).isVisible()){
					ui.calendarFrame.previousMonthButton.doClick();
				}else{
					ui.listFrame.prev.doClick();
				}
			}
			
			if(keyCode == KeyEvent.VK_KP_RIGHT){
				if(ui.calComponentList.get(0).isVisible()){
					ui.calendarFrame.nextMonthButton.doClick();
				}else{
					ui.listFrame.next.doClick();
				}
			}
			
			if(keyCode == KeyEvent.VK_F1){
				ui.calendarFrame.addEventButton.doClick();
			}
			
			if(keyCode == KeyEvent.VK_F5){
				if(ui.calendarFrame.findPresentDayButton.isVisible()){
					ui.calendarFrame.findPresentDayButton.doClick();
				}
			}
		}
		return false;
	}
	


}
