package com.goldentwo.view;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.utils.Pagination.*;
import com.goldentwo.utils.alarmChecker.AlarmChecker;

@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener{

	DataServiceImpl dataServiceImpl;
	AlarmChecker ac;
	
	ListFrame listFrame;
	CalendarFrame calendarFrame;
	
	JLabel topTitle;
	ArrayList<JButton> buttonDayList;
	ArrayList<Component> calComponentList;
	ArrayList<Component> listComponentList;
	JFileChooser xmlChooser;
	FileNameExtensionFilter xmlFilter;
	
	Filter filter;
	Sort sort;
	
	boolean isEvents;
	
    JButton calendarButton, listButton;
	JComboBox alarmComboBox;
	JSeparator separatorH, separatorV; 
	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem filterItem, exitItem, deleteOldEventsItem, 
			  saveToXML, loadFromXML, loadOneXML,
			  saveToOutlookFormat,
			  about;
	
	public UserInterface(DataServiceImpl dataServiceImpl, AlarmChecker ac) {
		this.dataServiceImpl = dataServiceImpl;
		this.ac = ac;
		buttonDayList = new ArrayList<>();
		calComponentList = new ArrayList<>();
		listComponentList = new ArrayList<>();
		filter = null;
		sort = null;
		isEvents = true;
		setLayout(null);
		listFrame = new ListFrame(this);
		calendarFrame = new CalendarFrame(this);
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyKeyListener(this));
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
    	deleteOldEventsItem = new JMenuItem("Delete old events");
    	saveToXML = new JMenuItem("Export data to XML");
    	loadFromXML = new JMenuItem("Load data from XML");
    	loadOneXML = new JMenuItem("Load event from XML");
    	saveToOutlookFormat = new JMenuItem("Export data as Outlook format");
    	about = new JMenuItem("About");
    	
    	exitItem = new JMenuItem("Exit");
    	filterItem.addActionListener(this);
    	exitItem.addActionListener(this);
    	deleteOldEventsItem.addActionListener(this);
    	saveToXML.addActionListener(this);
    	loadFromXML.addActionListener(this);
    	loadOneXML.addActionListener(this);
    	saveToOutlookFormat.addActionListener(this);
    	about.addActionListener(this);
    	
    	menu.add(filterItem);
    	menu.add(deleteOldEventsItem);
    	menu.addSeparator();
    	menu.add(saveToXML);
    	menu.add(saveToOutlookFormat);
    	menu.addSeparator();
    	menu.add(loadFromXML);
    	menu.add(loadOneXML);
    	menu.addSeparator();
    	menu.add(about);
    	menu.addSeparator();
    	menu.add(exitItem);
    	
    	setJMenuBar(menuBar);
    	menuBar.add(menu);
    	
    	xmlFilter = new FileNameExtensionFilter("XML files", "xml");
    	xmlChooser = new JFileChooser();
    	xmlChooser.setFileFilter(xmlFilter);
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
    	Object source = e.getSource();
    	
		if(source == calendarFrame.previousMonthButton){
			calendarFrame.actualMonth--;
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
			calendarFrame.markTodayDay();
		}
		if(source == calendarFrame.nextMonthButton){
			calendarFrame.actualMonth++;
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
			calendarFrame.markTodayDay();
		}
		if(source == exitItem){
			dispose();
		}
		if(source == filterItem){
			new FilterFrame(this).setVisible(true);
		}
		if(source == listButton){
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
		if(source == calendarButton){
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
		if(source == listFrame.next){
			listFrame.currentPage++;
			listFrame.updateEventCounterLabel();
			listFrame.fillTable();
			if(listFrame.totalPages == listFrame.currentPage){
				listFrame.next.setEnabled(false);
			}
			listFrame.prev.setEnabled(true);
		}
		if(source == listFrame.prev){
			listFrame.currentPage--;
			listFrame.updateEventCounterLabel();
			listFrame.fillTable();
			if(listFrame.currentPage == 1){
				listFrame.prev.setEnabled(false);
			}
			listFrame.next.setEnabled(true);
		}
		if(source == calendarFrame.addEventButton){
			new AddEventFrame(this, null).setVisible(true);
		}
		if(source == calendarFrame.findPresentDayButton){
			calendarFrame.setPresentDate();
			calendarFrame.setMonthAndYear();
			calendarFrame.fillDayButtons();
			calendarFrame.setEventsIntoCalendar();
			calendarFrame.markTodayDay();
		}
		if(source == alarmComboBox){
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
		if(source == deleteOldEventsItem){
			new DeleteOldEventsFrame(this).setVisible(true);
			ac.loadComingEvent();
		}
		if(source == listFrame.details){
			Event updateEvent = listFrame.list.getContent().get(listFrame.latestSelectedRow);
			new AddEventFrame(this, updateEvent).setVisible(true);
		}
		if(source == listFrame.exportToXML){
			Event exportedEvent = listFrame.list.getContent().get(listFrame.latestSelectedRow);
			dataServiceImpl.oneEventToXml(exportedEvent);
			generateMessage(true, this);
		}
		if(source == listFrame.delete){
			if(generateQuestion(this) == 0){
				Event deletedEvent = listFrame.list.getContent().get(listFrame.latestSelectedRow);
				dataServiceImpl.deleteEvent(deletedEvent.getId());
				generateMessage(true, this);
				listFrame.update();
				ac.loadComingEvent();
			}
		}
		if(source == saveToXML){
			dataServiceImpl.allEventsToXml();
			generateMessage(true, this);
		}
		if(source == loadFromXML){
			if(xmlChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				File file = xmlChooser.getSelectedFile();
				generateMessage(dataServiceImpl.allEventsFromXml(file), this);
				ac.loadComingEvent();
				listFrame.update();
			}
		}
		if(source == loadOneXML){
			if(xmlChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				File file = xmlChooser.getSelectedFile();
				generateMessage(dataServiceImpl.oneEventFromXml(file), this);
				ac.loadComingEvent();
				listFrame.update();
			}
		}
		if(source == saveToOutlookFormat){
			/*
			 * TODO add exporting to Outlook format method
			 */
			
			//generateMessage(true, this);	UNCOMMENT THIS SHIT
		}
		if(source == about){
			String str = "Organizer v1.0\n\n\n"
					+ "This program will help you to organize you time.\n"
					+ "Project wrote for 'Programowanie Komponentowe - FTIMS 2016'\n\n"
					+ "Łukasz Kuta & Konrad Klimczak\n"
					+ "®All rights reserved";
			JOptionPane.showMessageDialog(this,
										  str,
										  "About",
										  JOptionPane.INFORMATION_MESSAGE);
		}
	}
    
	static public void generateMessage(boolean flag, Component c){
		if(flag){
			JOptionPane.showMessageDialog(c,
					  "Success!",
					  "Message",
					  JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(c,
					  "Cannot load date from XML file!",
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
		}

	}
	
	static public int generateQuestion(Component c){
		int choose = JOptionPane.showConfirmDialog(c,
												  "ARE YOU SURE ?", 
												  "Warning",
												  JOptionPane.YES_NO_OPTION);
		return choose;
	}
}
