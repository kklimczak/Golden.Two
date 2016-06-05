package com.goldentwo.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.utils.Pagination.*;

@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener{

	DataServiceImpl dataServiceImpl;
	
	ListFrame listFrame;
	CalendarFrame calendarFrame;
	
	JLabel topTitle;
	ArrayList<JButton> buttonDayList;
	ArrayList<Component> calComponentList;
	ArrayList<Component> listComponentList;
	
	Filter filter;
	Sort sort;
	
	boolean isEvents;
	
	private JButton calendarButton, listButton;
	private JComboBox alarmComboBox;
	private JSeparator separatorH, separatorV; 
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem filterItem, exitItem;
	
	public UserInterface(DataServiceImpl dataServiceImpl) {
		this.dataServiceImpl = dataServiceImpl;
		buttonDayList = new ArrayList<>();
		calComponentList = new ArrayList<>();
		listComponentList = new ArrayList<>();
		filter = null;
		sort = null;
		isEvents = true;
		setLayout(null);
		listFrame = new ListFrame(this);
		calendarFrame = new CalendarFrame(this);
		
		setResizable(false);
		init();
    }
	
    private void init(){
	    setTitle("ORGANIZER");
	    setSize(860, 540);
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
        generateMenuBar();
    }
    
    private void generateMenuBar(){
    	menuBar = new JMenuBar();
    	menu = new JMenu("Options");
    	filterItem = new JMenuItem("Filter manager");
    	exitItem = new JMenuItem("Exit");
    	filterItem.addActionListener(this);
    	exitItem.addActionListener(this);
    	
    	menu.add(filterItem);
    	menu.addSeparator();
    	menu.add(exitItem);
    	
    	setJMenuBar(menuBar);
    	menuBar.add(menu);
    }

	private void generateLeftSideButtons(){
    	calendarButton = new JButton("Calendar"); 
    	listButton = new JButton("List");
    	String[] str = {"Events", "Alarms"};
    	alarmComboBox = new JComboBox<>(str);
    	
    	calendarButton.setBounds(20, 140, 160, 60);
    	listButton.setBounds(20, 210, 160, 60);
    	alarmComboBox.setBounds(20, 280, 160, 30);
    	
    	calendarButton.addActionListener(this);
    	listButton.addActionListener(this);
    	alarmComboBox.addActionListener(this);
    	add(calendarButton);
    	add(listButton);
    	add(alarmComboBox);
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == calendarFrame.previousMonthButton){
			calendarFrame.actualMonth--;
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
			calendarFrame.markTodayDay();
		}
		if(e.getSource() == calendarFrame.nextMonthButton){
			calendarFrame.actualMonth++;
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
			calendarFrame.markTodayDay();
		}
		if(e.getSource() == exitItem){
			dispose();
		}
		if(e.getSource() == filterItem){
			new FilterFrame(this).setVisible(true);
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
			calendarFrame.markTodayDay();
		}
		if(e.getSource() == listFrame.next){
			listFrame.currentPage++;
			listFrame.updateEventCounterLabel();
			listFrame.fillTable();
			if(listFrame.totalPages == listFrame.currentPage){
				listFrame.next.setEnabled(false);
			}
			listFrame.prev.setEnabled(true);
		}
		if(e.getSource() == listFrame.prev){
			listFrame.currentPage--;
			listFrame.updateEventCounterLabel();
			listFrame.fillTable();
			if(listFrame.currentPage == 1){
				listFrame.prev.setEnabled(false);
			}
			listFrame.next.setEnabled(true);
		}
		if(e.getSource() == calendarFrame.addEventButton){
			new AddEventFrame(dataServiceImpl).setVisible(true);
		}
		if(e.getSource() == calendarFrame.findPresentDayButton){
			calendarFrame.setPresentDate();
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
			calendarFrame.markTodayDay();
		}
		if(e.getSource() == alarmComboBox){
			if(alarmComboBox.getSelectedItem().equals("Events")){
				isEvents = true;
				calendarFrame.fillDayButtons();
				calendarFrame.setEventsIntoCalendar();
				listFrame.update();
			}else{
				isEvents = false;
				calendarFrame.fillDayButtons();
				calendarFrame.setEventsIntoCalendar();
				listFrame.update();
			}
		}
	}
}
