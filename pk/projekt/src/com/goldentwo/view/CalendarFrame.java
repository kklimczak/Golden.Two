package com.goldentwo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.goldentwo.data.Event.Event;
import com.goldentwo.utils.Date.DateConverter;

// TODO: Auto-generated Javadoc
/**
 * Klasa generujaca komponenty widoku kalendarza
 */
public class CalendarFrame {
	
	/** Komponent nadrzedny */
	private UserInterface ui;
	
	/** Kalendarz uzywany do pobierania daty. */
	Calendar calendar;
	
	JLabel monLab, tueLab, wedLab, thuLab, friLab, sunLab, satLab;
	
	int actualMonth, actualYear, firstDayOfMonth;
	
	/** Przyciski zmieniajace miesiac */
	JButton previousMonthButton, nextMonthButton;
	
	/** Przyciski funkcyjne */
	JButton addEventButton, findPresentDayButton;

	/** Tabela z iloscia dni w miesiacu. */
	int[] daysOfMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	/**
	 * Inicjalizuje instancje CalendarFrame
	 *
	 * @param ui klasa nadrzedna
	 */
	public CalendarFrame(UserInterface ui){
		this.ui = ui;
		calendar = new GregorianCalendar();
		actualMonth = calendar.get(Calendar.MONTH);
		actualYear = calendar.get(Calendar.YEAR);
		
		initComponents();
		initTopLabel();
		setDayLabels();
		setMonthAndYear();
		generateMonthsButtons();
		generateDayButtons();
		generateAddEventAndFindPresentDayButtons();
		fillDayButtons();
		setEventsIntoCalendar();
	}
	
	/**
	 * Inicjalizuje najwazniejsze komponenty
	 */
	private void initComponents(){
	    ui.topTitle = new JLabel();
	    
		previousMonthButton = new JButton("<");
		nextMonthButton = new JButton(">");

        monLab = new JLabel("Mon"); tueLab = new JLabel("Tue"); wedLab = new JLabel("Wed"); 
        thuLab = new JLabel("Thu"); friLab = new JLabel("Fri"); 
        satLab = new JLabel("Sat"); sunLab = new JLabel("Sun");
        
        addEventButton = new JButton("ADD EVENT");
    	findPresentDayButton = new JButton("BACK TO PRESENT DAY");
	}
	
	/**
	 * Inicjalizuje glowny napis organizera
	 */
	private void initTopLabel(){
	    ui.topTitle.setBounds(430, 20, 280, 50);
	    ui.topTitle.setFont(new Font("Arial", 1, 30));
	    ui.add(ui.topTitle);
	}
	
    /**
     * Generuje label'e z dniami tygodnia
     */
    void setDayLabels(){
        monLab.setBounds(265, 90, 50, 50);
        tueLab.setBounds(345, 90, 50, 50);
        wedLab.setBounds(425, 90, 52, 50);
        thuLab.setBounds(505, 90, 50, 50);
        friLab.setBounds(585, 90, 50, 50);
        satLab.setBounds(665, 90, 50, 50);
        sunLab.setBounds(745, 90, 50, 50);
        
        monLab.setFont(new Font("Arial", Font.ITALIC, 25));
        tueLab.setFont(new Font("Arial", Font.ITALIC, 25));
        wedLab.setFont(new Font("Arial", Font.ITALIC, 25));
        thuLab.setFont(new Font("Arial", Font.ITALIC, 25));
        friLab.setFont(new Font("Arial", Font.ITALIC, 25));
        satLab.setFont(new Font("Arial", Font.ITALIC, 25));
        sunLab.setFont(new Font("Arial", Font.ITALIC, 25));
        
        ui.calComponentList.add(monLab);
        ui.calComponentList.add(tueLab);
        ui.calComponentList.add(wedLab);
        ui.calComponentList.add(thuLab);
        ui.calComponentList.add(friLab);
        ui.calComponentList.add(satLab);
        ui.calComponentList.add(sunLab);
        
    }
    
    /**
     * Ustawia napis z rokiem i miesiacem
     */
    void setMonthAndYear(){
    	
       	if(actualMonth == -1){
       		actualYear--;
       		actualMonth = 11;
       	}
       	
       	if(actualMonth == 12){
       		actualYear++;
       		actualMonth = 0;
       	}
    	
    	if(actualMonth == 5){
    		ui.topTitle.setText("JUNE " + actualYear);
    	}
    	if(actualMonth == 4){
    		ui.topTitle.setText("MAY " + actualYear);
    	}
    	if(actualMonth == 3){
    		ui.topTitle.setText("APRIL " + actualYear);
    	}
    	if(actualMonth == 2){
    		ui.topTitle.setText("MARCH " + actualYear);
    	}
    	if(actualMonth == 1){
    		ui.topTitle.setText("FEBRUARY " + actualYear);
    	}
    	if(actualMonth == 0){
    		ui.topTitle.setText("JANUARY " + actualYear);
    	}
    	if(actualMonth == 6){
    		ui.topTitle.setText("JULY " + actualYear);
    	}
    	if(actualMonth == 7){
    		ui.topTitle.setText("AUGUST " + actualYear);
    	}
    	if(actualMonth == 8){
    		ui.topTitle.setText("SEPTEMBER " + actualYear);
    	}
    	if(actualMonth == 9){
    		ui.topTitle.setText("OCTOBER " + actualYear);
    	}
       	if(actualMonth == 10){
       		ui.topTitle.setText("NOVEMBER " + actualYear);
    	}
       	if(actualMonth == 11){
       		ui.topTitle.setText("DECEMBER " + actualYear);
    	}

    }

    /**
     * Generuje przyciski zmieniajace miesiac
     */
    void generateMonthsButtons() {
		previousMonthButton.setBounds(225, 140, 20, 325);
		nextMonthButton.setBounds(810, 140, 20, 325);
		
		previousMonthButton.addActionListener(ui);
		nextMonthButton.addActionListener(ui);	
		ui.calComponentList.add(previousMonthButton);
		ui.calComponentList.add(nextMonthButton);
	}
    
    /**
     * Generuje przyciski odpowiadajace dniom miesiaca
     */
    void generateDayButtons(){
        
    	for(int i = 0 ; i < 42 ; i++){
    		ui.buttonDayList.add(new JButton(Integer.toString(i)));
    	}
    	
    	for(JButton button : ui.buttonDayList){
    		button.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					new DayListEventsFrame(getDayEvents(Integer.parseInt(e.getActionCommand())),ui).setVisible(true);	
				}
			});
    	}

		int x = 250, 
			y = 140, 
			width = 75, 
			height = 50;
		
    	for(int i = 0 ; i < 42 ; i++){
    		
    		if(i%7 == 0 && i != 0){
    			x = 250;
    			y += 55;
    		}  		
    		ui.buttonDayList.get(i).setBounds(x, y, width, height);
    		x += 80;
    	}
    }

    /**
     * Wypelnia odpowiednie przyciski z dniami miesiaca numerem dnia miesiaca
     */
    void fillDayButtons(){
    	int day = 1;
    	int foundDay = findFirstDayOfMonth();
    	daysOfMonths[1] = actualYear % 4 == 0 ? 29 : 28;
    	for(int i = 0 ; i < 42 ; i++){
    		
    		if(i < foundDay || i > daysOfMonths[actualMonth] + foundDay - 1){
    			ui.buttonDayList.get(i).setText("");
    			ui.buttonDayList.get(i).setEnabled(false);
    			ui.buttonDayList.get(i).setBackground(null);
    			ui.buttonDayList.get(i).setBorder(new JButton().getBorder());
    		}
    		else {
    			ui.buttonDayList.get(i).setText(Integer.toString(day++));
    			ui.buttonDayList.get(i).setEnabled(true);
    			ui.buttonDayList.get(i).setBackground(null);
    			ui.buttonDayList.get(i).setBorder(new JButton().getBorder());
    		}
    		
    	}
    	markTodayDay();
    }
    
    /**
     * Znajduje pierwszy dzien miesiaca
     *
     * @return dzien tygodnia. 0 dla poniedzialku, 6 dla niedzieli
     */
    int findFirstDayOfMonth(){
    	calendar.set(Calendar.MONTH, actualMonth);
    	calendar.set(Calendar.YEAR, actualYear);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			return 6;	
		case 2:
			return 0;
		case 3:
			return 1;
		case 4:
			return 2;
		case 5:
			return 3;
		case 6:
			return 4;
		case 7:
			return 5;
		}
    	
    	return 10;
    }
    	
    /**
     * Oznacza w widoku kalendarza dzien dzisiejszy
     */
    void markTodayDay(){
		if(ui.calComponentList.get(0).isVisible())findPresentDayButton.setVisible(true);
    	Calendar c = new GregorianCalendar();
    	if(actualYear == c.get(Calendar.YEAR)){
    		if(actualMonth == c.get(Calendar.MONTH)){
    			Border thickBorder = new LineBorder(Color.GREEN, 3);
    			ui.buttonDayList.get(1 + firstDayOfMonth + c.get(Calendar.DAY_OF_MONTH)).setBorder(thickBorder);
    			findPresentDayButton.setVisible(false);
    		}
    	}
    }
    
    /**
     * Laduje wydarzenia do widoku kalendarza i zaznacza, w ktore dni zapisane jest wydarzenie
     */
    void setEventsIntoCalendar(){
    	List<Event> list = ui.dataServiceImpl.getAllEventsBetweenDates(
    			DateConverter.stringToDate(actualYear, actualMonth + 1, 1), 
    			DateConverter.stringToDate(actualYear, actualMonth + 1, daysOfMonths[actualMonth]),
    			ui.isEvents);
    	int foundDay = findFirstDayOfMonth();
    	Date date = null;
    	for(Event e : list){
    		if(ui.isEvents){
    			date = e.getDate();
    		}else{
    			date = e.getAlarm();
    		}
    		calendar.setTime(date);
    		ui.buttonDayList.get(foundDay - 1 + calendar.get(Calendar.DAY_OF_MONTH)).setBackground(Color.red); 
    	}
    }
    
    /**
     * Generuje przyciski do znajdowania aktualnego dnia oraz dodania nowego zdarzenia
     */
    void generateAddEventAndFindPresentDayButtons(){
    	addEventButton.setBounds(225, 470, 140, 20);
    	addEventButton.setBackground(Color.green);
    	addEventButton.addActionListener(ui);
    	ui.calComponentList.add(addEventButton);
    	
    	findPresentDayButton.setBounds(630, 470, 200, 20);
    	findPresentDayButton.addActionListener(ui);
    	ui.calComponentList.add(findPresentDayButton);
    	
    }
    
    /**
     * Uaktualnia kalendarz na bierzacy rok i miesiac 
     */
    void setPresentDate(){
    	Calendar c = new GregorianCalendar();
		actualMonth = c.get(Calendar.MONTH);
		actualYear = c.get(Calendar.YEAR);
    }
    
    /**
     * Zwraca liste wydarzen z jednego dnia
     *
     * @param dayNumber numer dnia miesiaca
     * @return lista wydarzen	
     */
    List<Event> getDayEvents(int dayNumber){
    	Date from = DateConverter.stringToDate(actualYear, actualMonth + 1, dayNumber);
    	Date to = DateConverter.stringToDate(actualYear, actualMonth + 1, dayNumber+1);
    	
    	List<Event> list = ui.dataServiceImpl.getAllEventsBetweenDates(from, to, ui.isEvents);
    	return list;
    }
}

