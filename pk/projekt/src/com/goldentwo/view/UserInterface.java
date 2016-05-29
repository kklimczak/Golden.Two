package com.goldentwo.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.spi.CalendarDataProvider;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.goldentwo.data.Event.Event;
import com.goldentwo.data.database.DBConnection;
import com.goldentwo.service.DataServiceImpl;

@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener{

	private DataServiceImpl dataServiceImpl;
	
	private Calendar calendar;
	int actualMonth, actualYear, firstDayOfMonth;
	int[] daysOfMonths = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	private JLabel monthAndYear;
	private JLabel monLab, tueLab, wedLab, thuLab, friLab, sunLab, satLab;
	private JButton day1,  day2,  day3,  day4,  day5,  day6,  day7,
					day8,  day9,  day10, day11, day12, day13, day14,
					day15, day16, day17, day18, day19, day20, day21,
					day22, day23, day24, day25, day26, day27, day28,
					day29, day30, day31, day32, day33, day34, day35,
					day36, day37, day38, day39, day40, day41, day42; 
	private ArrayList<JButton> buttonDayList;
	private JButton calendarButton, listButton, alarmButton, addButton, deleteButton;
	private JButton previousMonthButton, nextMonthButton;
	private JSeparator separatorH, separatorV; 
	
	@SuppressWarnings("deprecation")
	public UserInterface(DataServiceImpl dataServiceImpl) {
		this.dataServiceImpl = dataServiceImpl;
		calendar = new GregorianCalendar();
		actualMonth = calendar.getTime().getMonth();
		actualYear = calendar.getTime().getYear() + 1900;
		buttonDayList = new ArrayList<>();
		init();
		setLayout(null);
    }
	
    private void init(){
	    setTitle("Przykladowe okno");
	    setSize(850, 655);
	    setLocationRelativeTo(null);
	        
	    monthAndYear = new JLabel();
	    monthAndYear.setBounds(400, 20, 280, 50);
	    monthAndYear.setFont(new Font("Arial", 1, 30));
        add(monthAndYear);
        
        separatorH = new JSeparator(SwingConstants.HORIZONTAL);
        separatorH.setBounds(200, 80, 630, 5);
        add(separatorH);
        
        separatorV = new JSeparator(SwingConstants.VERTICAL);
        separatorV.setBounds(200, 5, 5, 640);
        add(separatorV);
        
        monLab = new JLabel("Pon"); tueLab = new JLabel("Wto"); wedLab = new JLabel("Śro"); 
        thuLab = new JLabel("Czw"); friLab = new JLabel("Pią"); 
        satLab = new JLabel("Sob"); sunLab = new JLabel("Nie");
        
        monLab.setBounds(265, 90, 50, 50);
        tueLab.setBounds(345, 90, 50, 50);
        wedLab.setBounds(425, 90, 50, 50);
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
        
        add(monLab);
        add(tueLab);
        add(wedLab);
        add(thuLab);
        add(friLab);
        add(satLab);
        add(sunLab);
        
	setDefaultCloseOperation(EXIT_ON_CLOSE);
        paint();
        setEventsIntoCalendar();
    }
    
    private void paint(){
        setMonthAndYear();
        generateDayButtons();
        generateLeftSideButtons();
        generateMonthsButtons();
        fillDayButtons();
    }
 
    private void setMonthAndYear(){
    	
       	if(actualMonth == -1){
       		actualYear--;
       		actualMonth = 11;
       	}
       	
       	if(actualMonth == 12){
       		actualYear++;
       		actualMonth = 0;
       	}
    	
    	if(actualMonth == 5){
    		monthAndYear.setText("CZERWIEC " + actualYear);
    	}
    	if(actualMonth == 4){
    		monthAndYear.setText("MAJ " + actualYear);
    	}
    	if(actualMonth == 3){
    		monthAndYear.setText("KWIECIEŃ " + actualYear);
    	}
    	if(actualMonth == 2){
    		monthAndYear.setText("MARZEC " + actualYear);
    	}
    	if(actualMonth == 1){
    		monthAndYear.setText("LUTY " + actualYear);
    	}
    	if(actualMonth == 0){
    		monthAndYear.setText("STYCZEŃ " + actualYear);
    	}
    	if(actualMonth == 6){
    		monthAndYear.setText("LIPIEC " + actualYear);
    	}
    	if(actualMonth == 7){
    		monthAndYear.setText("SIEPIEŃ " + actualYear);
    	}
    	if(actualMonth == 8){
    		monthAndYear.setText("WRZESIEŃ " + actualYear);
    	}
    	if(actualMonth == 9){
    		monthAndYear.setText("PAŹDZIERNIK " + actualYear);
    	}
       	if(actualMonth == 10){
    		monthAndYear.setText("LISTOPAD " + actualYear);
    	}
       	if(actualMonth == 11){
    		monthAndYear.setText("GRUDZIEŃ " + actualYear);
    	}

    }
    
    private void generateMonthsButtons() {
		previousMonthButton = new JButton("<");
		nextMonthButton = new JButton(">");
		
		previousMonthButton.setBounds(225, 140, 20, 475);
		nextMonthButton.setBounds(810, 140, 20, 475);
		
		previousMonthButton.addActionListener(this);
		nextMonthButton.addActionListener(this);	
		add(previousMonthButton);
		add(nextMonthButton);
	}

	private void generateLeftSideButtons(){
    	calendarButton = new JButton("Calendar"); 
    	listButton = new JButton("List");
    	alarmButton = new JButton("Alarms");
    	addButton = new JButton("ADD");
    	deleteButton = new JButton("DELETE");
    	
    	calendarButton.setBounds(20, 140, 160, 60);
    	listButton.setBounds(20, 230, 160, 60);
    	alarmButton.setBounds(20, 330, 160, 60);
    	addButton.setBounds(20, 480, 160, 60);
    	deleteButton.setBounds(20, 555, 160, 60);
    	
    	calendarButton.addActionListener(this);
    	listButton.addActionListener(this);
    	alarmButton.addActionListener(this);
    	addButton.addActionListener(this);
    	deleteButton.addActionListener(this);
    	add(calendarButton);
    	add(listButton);
    	add(alarmButton);
    	add(addButton);
    	add(deleteButton);
    }
    
    private void generateDayButtons(){
    	day1 = new JButton("1"); buttonDayList.add(day1);
    	day2 = new JButton("2"); buttonDayList.add(day2);
    	day3 = new JButton("3"); buttonDayList.add(day3);
    	day4 = new JButton("4"); buttonDayList.add(day4);
    	day5 = new JButton("5"); buttonDayList.add(day5);
    	day6 = new JButton("6"); buttonDayList.add(day6);
    	day7 = new JButton("7"); buttonDayList.add(day7);
    	
    	day8 = new JButton("1"); buttonDayList.add(day8);
    	day9 = new JButton("2"); buttonDayList.add(day9);
    	day10 = new JButton("3"); buttonDayList.add(day10);
    	day11 = new JButton("4"); buttonDayList.add(day11);
    	day12 = new JButton("5"); buttonDayList.add(day12);
    	day13 = new JButton("6"); buttonDayList.add(day13);
    	day14 = new JButton("7"); buttonDayList.add(day14);
    	
    	day15 = new JButton("1"); buttonDayList.add(day15);
    	day16 = new JButton("2"); buttonDayList.add(day16);
    	day17 = new JButton("3"); buttonDayList.add(day17);
    	day18 = new JButton("4"); buttonDayList.add(day18);
    	day19 = new JButton("5"); buttonDayList.add(day19);
    	day20 = new JButton("6"); buttonDayList.add(day20);
    	day21 = new JButton("7"); buttonDayList.add(day21);
     	
    	day22 = new JButton("1"); buttonDayList.add(day22);
    	day23 = new JButton("2"); buttonDayList.add(day23);
    	day24 = new JButton("3"); buttonDayList.add(day24);
    	day25 = new JButton("4"); buttonDayList.add(day25);
    	day26 = new JButton("5"); buttonDayList.add(day26);
    	day27 = new JButton("6"); buttonDayList.add(day27);
    	day28 = new JButton("7"); buttonDayList.add(day28);
    
    	day29 = new JButton("1"); buttonDayList.add(day29);
    	day30 = new JButton("2"); buttonDayList.add(day30);
    	day31 = new JButton("3"); buttonDayList.add(day31);
    	day32 = new JButton("4"); buttonDayList.add(day32);
    	day33 = new JButton("5"); buttonDayList.add(day33);
    	day34 = new JButton("6"); buttonDayList.add(day34);
    	day35 = new JButton("7"); buttonDayList.add(day35);
    	
    	day36 = new JButton("1"); buttonDayList.add(day36);
    	day37 = new JButton("2"); buttonDayList.add(day37);
    	day38 = new JButton("3"); buttonDayList.add(day38);
    	day39 = new JButton("4"); buttonDayList.add(day39);
    	day40 = new JButton("5"); buttonDayList.add(day40);
    	day41 = new JButton("6"); buttonDayList.add(day41);
    	day42 = new JButton("7"); buttonDayList.add(day42);
    	
    	day1.setBounds(250, 140, 75, 75);
    	day2.setBounds(330, 140, 75, 75);
    	day3.setBounds(410, 140, 75, 75);
    	day4.setBounds(490, 140, 75, 75);
    	day5.setBounds(570, 140, 75, 75);
    	day6.setBounds(650, 140, 75, 75);
    	day7.setBounds(730, 140, 75, 75);
    	
    	day8.setBounds(250, 220, 75, 75);
    	day9.setBounds(330, 220, 75, 75);
    	day10.setBounds(410, 220, 75, 75);
    	day11.setBounds(490, 220, 75, 75);
    	day12.setBounds(570, 220, 75, 75);
    	day13.setBounds(650, 220, 75, 75);
    	day14.setBounds(730, 220, 75, 75);
    	
    	day15.setBounds(250, 300, 75, 75);
    	day16.setBounds(330, 300, 75, 75);
    	day17.setBounds(410, 300, 75, 75);
    	day18.setBounds(490, 300, 75, 75);
    	day19.setBounds(570, 300, 75, 75);
    	day20.setBounds(650, 300, 75, 75);
    	day21.setBounds(730, 300, 75, 75);
    	
    	day22.setBounds(250, 380, 75, 75);
    	day23.setBounds(330, 380, 75, 75);
    	day24.setBounds(410, 380, 75, 75);
    	day25.setBounds(490, 380, 75, 75);
    	day26.setBounds(570, 380, 75, 75);
    	day27.setBounds(650, 380, 75, 75);
    	day28.setBounds(730, 380, 75, 75);
    	
    	day29.setBounds(250, 460, 75, 75);
    	day30.setBounds(330, 460, 75, 75);
    	day31.setBounds(410, 460, 75, 75);
    	day32.setBounds(490, 460, 75, 75);
    	day33.setBounds(570, 460, 75, 75);
    	day34.setBounds(650, 460, 75, 75);
    	day35.setBounds(730, 460, 75, 75);
    	
    	day36.setBounds(250, 540, 75, 75);
    	day37.setBounds(330, 540, 75, 75);
    	day38.setBounds(410, 540, 75, 75);
    	day39.setBounds(490, 540, 75, 75);
    	day40.setBounds(570, 540, 75, 75);
    	day41.setBounds(650, 540, 75, 75);
    	day42.setBounds(730, 540, 75, 75);
    
    	for(JButton button : buttonDayList){
    		add(button);
    		button.addActionListener(this);
    	}

    }

    private void fillDayButtons(){
    	int day = 1;
    	int foundDay = findFirstDayOfMonth();
    	for(int i = 0 ; i < 42 ; i++){
    		
    		if(i < foundDay || i > daysOfMonths[actualMonth] + foundDay - 1){
    			buttonDayList.get(i).setText("");
    			buttonDayList.get(i).setEnabled(false);
    			buttonDayList.get(i).setBackground(null);
    		}
    		else {
        		buttonDayList.get(i).setText(Integer.toString(day++));
        		buttonDayList.get(i).setEnabled(true);
    			buttonDayList.get(i).setBackground(null);
    		}
    		
    	}
    }
    
    
    private int findFirstDayOfMonth(){
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
    
	
    private void setEventsIntoCalendar(){
    	List<Event> list = dataServiceImpl.getAllEvents(actualMonth + 1);
    	int foundDay = findFirstDayOfMonth();
    	for(Event e : list){
    		Date date = e.getDate();
    		calendar.setTime(date);
    		buttonDayList.get(foundDay - 1 + calendar.get(Calendar.DAY_OF_MONTH)).setBackground(Color.red); 
    	}
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == previousMonthButton){
			actualMonth--;
			setMonthAndYear();
			fillDayButtons();
			setEventsIntoCalendar();
		}
		if(e.getSource() == nextMonthButton){
			actualMonth++;
			setMonthAndYear();
			fillDayButtons();
			setEventsIntoCalendar();
		}
	}
}
