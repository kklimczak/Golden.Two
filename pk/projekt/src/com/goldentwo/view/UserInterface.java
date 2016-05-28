package com.goldentwo.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
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

	int firstDayOfMonth = 3, lastDayOfMonth = 4;
	int actualMonth = 5, actualYear = 2016;
	
	private JLabel monthAndYear;
	private JButton day1,  day2,  day3,  day4,  day5,  day6,  day7,
					day8,  day9,  day10, day11, day12, day13, day14,
					day15, day16, day17, day18, day19, day20, day21,
					day22, day23, day24, day25, day26, day27, day28,
					day29, day30, day31, day32, day33, day34, day35;
	private ArrayList<JButton> dayList;
	private JButton calendarButton, listButton, alarmButton, addButton, deleteButton;
	private JButton previousMonthButton, nextMonthButton;
	private JSeparator separatorH, separatorV; 
	
	public UserInterface() {
		dayList = new ArrayList<>();
		init();
		setLayout(null);
    }
	
    private void init(){
	    setTitle("Przykladowe okno");
	    setSize(850, 600);
	    setLocationRelativeTo(null);
	        
	    monthAndYear = new JLabel();
	    monthAndYear.setBounds(380, 20, 250, 50);
	    monthAndYear.setFont(new Font("Arial", 1, 30));
        add(monthAndYear);
        
        separatorH = new JSeparator(SwingConstants.HORIZONTAL);
        separatorH.setBounds(200, 80, 590, 5);
        add(separatorH);
        
        separatorV = new JSeparator(SwingConstants.VERTICAL);
        separatorV.setBounds(200, 5, 5, 540);
        add(separatorV);
        
        paint();
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
		
		previousMonthButton.setBounds(225, 140, 20, 395);
		nextMonthButton.setBounds(810, 140, 20, 395);
		
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
    	
    	calendarButton.setBounds(20, 40, 160, 60);
    	listButton.setBounds(20, 140, 160, 60);
    	alarmButton.setBounds(20, 240, 160, 60);
    	addButton.setBounds(20, 340, 160, 60);
    	deleteButton.setBounds(20, 440, 160, 60);
    	
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
    	day1 = new JButton("1"); dayList.add(day1);
    	day2 = new JButton("2"); dayList.add(day2);
    	day3 = new JButton("3"); dayList.add(day3);
    	day4 = new JButton("4"); dayList.add(day4);
    	day5 = new JButton("5"); dayList.add(day5);
    	day6 = new JButton("6"); dayList.add(day6);
    	day7 = new JButton("7"); dayList.add(day7);
    	
    	day8 = new JButton("1"); dayList.add(day8);
    	day9 = new JButton("2"); dayList.add(day9);
    	day10 = new JButton("3"); dayList.add(day10);
    	day11 = new JButton("4"); dayList.add(day11);
    	day12 = new JButton("5"); dayList.add(day12);
    	day13 = new JButton("6"); dayList.add(day13);
    	day14 = new JButton("7"); dayList.add(day14);
    	
    	day15 = new JButton("1"); dayList.add(day15);
    	day16 = new JButton("2"); dayList.add(day16);
    	day17 = new JButton("3"); dayList.add(day17);
    	day18 = new JButton("4"); dayList.add(day18);
    	day19 = new JButton("5"); dayList.add(day19);
    	day20 = new JButton("6"); dayList.add(day20);
    	day21 = new JButton("7"); dayList.add(day21);
     	
    	day22 = new JButton("1"); dayList.add(day22);
    	day23 = new JButton("2"); dayList.add(day23);
    	day24 = new JButton("3"); dayList.add(day24);
    	day25 = new JButton("4"); dayList.add(day25);
    	day26 = new JButton("5"); dayList.add(day26);
    	day27 = new JButton("6"); dayList.add(day27);
    	day28 = new JButton("7"); dayList.add(day28);
    
    	day29 = new JButton("1"); dayList.add(day29);
    	day30 = new JButton("2"); dayList.add(day30);
    	day31 = new JButton("3"); dayList.add(day31);
    	day32 = new JButton("4"); dayList.add(day32);
    	day33 = new JButton("5"); dayList.add(day33);
    	day34 = new JButton("6"); dayList.add(day34);
    	day35 = new JButton("7"); dayList.add(day35);
    	
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
    
    	add(day1);  add(day2);  add(day3);  add(day4);	add(day5);  add(day6);  add(day7);
    	add(day8);  add(day9);  add(day10); add(day11); add(day12); add(day13); add(day14);
    	add(day15); add(day16); add(day17); add(day18); add(day19); add(day20); add(day21);
    	add(day22); add(day23); add(day24); add(day25); add(day26); add(day27); add(day28);
    	add(day29); add(day30); add(day31); add(day32); add(day33); add(day34); add(day35);
    	
    	day1.addActionListener(this);
    	day2.addActionListener(this);
    	day3.addActionListener(this);
    	day4.addActionListener(this);
    	day5.addActionListener(this);
    	day6.addActionListener(this);
    	day7.addActionListener(this);
    	day8.addActionListener(this);
    	day9.addActionListener(this);
    	day10.addActionListener(this);
    	day11.addActionListener(this);
    	day12.addActionListener(this);
    	day13.addActionListener(this);
    	day14.addActionListener(this);
    	day15.addActionListener(this);
    	day16.addActionListener(this);
    	day17.addActionListener(this);
    	day18.addActionListener(this);
    	day19.addActionListener(this);
    	day20.addActionListener(this);
    	day21.addActionListener(this);
    	day22.addActionListener(this);
    	day23.addActionListener(this);
    	day24.addActionListener(this);
    	day25.addActionListener(this);
    	day26.addActionListener(this);
    	day27.addActionListener(this);
    	day28.addActionListener(this);
    	day29.addActionListener(this);
    	day30.addActionListener(this);
    	day31.addActionListener(this);
    	day32.addActionListener(this);
    	day33.addActionListener(this);
    	day34.addActionListener(this);
    	day35.addActionListener(this);
    }

    private void fillDayButtons(){
    	
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == previousMonthButton){
			actualMonth--;
			setMonthAndYear();
		}
		if(e.getSource() == nextMonthButton){
			actualMonth++;
			setMonthAndYear();
		}
	}
}
