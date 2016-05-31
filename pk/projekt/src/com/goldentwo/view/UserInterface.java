package com.goldentwo.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.goldentwo.service.DataServiceImpl;

@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener{

	DataServiceImpl dataServiceImpl;
	
	ListFrame listFrame;
	CalendarFrame calendarFrame;
	
	JLabel topTitle;
	ArrayList<JButton> buttonDayList;
	ArrayList<Component> calComponentList;
	ArrayList<Component> listComponentList;
	
	private JButton calendarButton, listButton, alarmButton;
	private JSeparator separatorH, separatorV; 
	
	public UserInterface(DataServiceImpl dataServiceImpl) {
		this.dataServiceImpl = dataServiceImpl;
		buttonDayList = new ArrayList<>();
		calComponentList = new ArrayList<>();
		listComponentList = new ArrayList<>();
		setLayout(null);
		listFrame = new ListFrame(this);
		calendarFrame = new CalendarFrame(this);

		init();
    }
	
    private void init(){
	    setTitle("ORGANIZER");
	    setSize(860, 535);
	    setLocationRelativeTo(null);
        
        separatorH = new JSeparator(SwingConstants.HORIZONTAL);
        separatorH.setBounds(200, 80, 630, 5);
        add(separatorH);
        
        separatorV = new JSeparator(SwingConstants.VERTICAL);
        separatorV.setBounds(200, 5, 5, 640);
        add(separatorV);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        for(Component c : calComponentList){
        	add(c);
        }
        
        for(JButton b : buttonDayList){
        	add(b);
        }

        generateLeftSideButtons();            
    }

	private void generateLeftSideButtons(){
    	calendarButton = new JButton("Calendar"); 
    	listButton = new JButton("List");
    	alarmButton = new JButton("Alarms");
    	
    	calendarButton.setBounds(20, 140, 160, 60);
    	listButton.setBounds(20, 210, 160, 60);
    	alarmButton.setBounds(20, 280, 160, 60);
    	
    	calendarButton.addActionListener(this);
    	listButton.addActionListener(this);
    	alarmButton.addActionListener(this);
    	add(calendarButton);
    	add(listButton);
    	add(alarmButton);
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == calendarFrame.previousMonthButton){
			calendarFrame.actualMonth--;
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
		}
		if(e.getSource() == calendarFrame.nextMonthButton){
			calendarFrame.actualMonth++;
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
		}
		if(e.getSource() == listButton){
			for(JButton b : buttonDayList){
				remove(b);
			}
			for(Component c : calComponentList){
				c.setVisible(false);
			}
			for(Component c : listComponentList){
				add(c);
			}
			topTitle.setText("EVENT LIST");
		}
		if(e.getSource() == calendarButton){
			for(JButton b : buttonDayList){
				add(b);
			}
			for(Component c : calComponentList){
				c.setVisible(true);
			}
			for(Component c : listComponentList){
				remove(c);
			}
			calendarFrame.setMonthAndYear();
		}
	}
}
