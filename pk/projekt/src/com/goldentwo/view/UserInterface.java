package com.goldentwo.view;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.goldentwo.data.Event.Event;
import com.goldentwo.service.DataServiceImpl;
import com.goldentwo.service.SettingsServiceImpl;
import com.goldentwo.utils.Pagination.*;
import com.goldentwo.utils.alarmChecker.AlarmChecker;

// TODO: Auto-generated Javadoc
/**
 * The Class UserInterface.
 * 
 * Main GUI class;
 * 
 * Defines view of program.
 * In containers are stored components of calendar view and event-list view,
 * which become visible or invisible in properly time.
 * 
 *
 * @author lkuta
 * @author kklimczak
 */
@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener{

	/** Providing methods for data base support */
	DataServiceImpl dataServiceImpl;
	
	/** Providing default setups */
	SettingsServiceImpl ssi;
	
	/** Generates alarm when event's alarm date is equal to system date */
	AlarmChecker ac;
	
	/** Generates components for event-list view */
	ListFrame listFrame;
	
	/** Generates componenents for calendar view */
	CalendarFrame calendarFrame;
		
	/** The top title. */
	JLabel topTitle;
	
	/** The button day list. */
	ArrayList<JButton> buttonDayList;
	
	/** The calendar view component list. */
	ArrayList<Component> calComponentList;
	
	/** The event-list's view component list. */
	ArrayList<Component> listComponentList;
	
	/** The xml chooser. */
	JFileChooser xmlChooser;
	
	/** The xml filter for file chooser. */
	FileNameExtensionFilter xmlFilter;
	
	/** The filter for filtering events. */
	Filter filter;
	
	/** The sort for sorting events. */
	Sort sort;
	
	/** Flag for displaying on calendar events or alarms. */
	boolean isEvents;
	
    /** Button switching calendar view */
    JButton calendarButton, listButton;
	
	/** The alarm combo box changing field <code> isEvents </code>. */
	JComboBox alarmComboBox;
	
	/** The calendar line separator. */
	JSeparator separatorH, separatorV; 
	
	/** The menu bar. */
	JMenuBar menuBar;
	
	/** The menu. */
	JMenu menu;
	
	/** The menu items */
	JMenuItem filterItem, exitItem, deleteOldEventsItem, 
			  saveToXML, loadFromXML, loadOneXML,
			  about, properties;
	
	/**
	 * Instantiates a new GUI of Organizer.
	 *
	 * @param dataServiceImpl implementation of interface <code> DataService </code>
	 * @param ac class that generate alarms
	 */
	public UserInterface(DataServiceImpl dataServiceImpl, AlarmChecker ac, SettingsServiceImpl ssi) {
		this.dataServiceImpl = dataServiceImpl;
		this.ssi = ssi;
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
	
    /**
     * Inits the program.
     */
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
        
        PropertiesFrame component = new PropertiesFrame(this, ssi);
        component.setDefaultSettings();
    }
    
    /**
     * Generate menu bar.
     */
    private void generateMenuBar(){
    	menuBar = new JMenuBar();
    	menu = new JMenu("Options");
    	filterItem = new JMenuItem("Filter manager");
    	deleteOldEventsItem = new JMenuItem("Delete old events");
    	saveToXML = new JMenuItem("Export data to XML");
    	loadFromXML = new JMenuItem("Load data from XML");
    	loadOneXML = new JMenuItem("Load event from XML");
    	about = new JMenuItem("About");
    	properties = new JMenuItem("Properties");
    	
    	exitItem = new JMenuItem("Exit");
    	filterItem.addActionListener(this);
    	exitItem.addActionListener(this);
    	deleteOldEventsItem.addActionListener(this);
    	saveToXML.addActionListener(this);
    	loadFromXML.addActionListener(this);
    	loadOneXML.addActionListener(this);
    	about.addActionListener(this);
    	properties.addActionListener(this);
    	
    	menu.add(filterItem);
    	menu.add(deleteOldEventsItem);
    	menu.addSeparator();
    	menu.add(saveToXML);
    	menu.addSeparator();
    	menu.add(loadFromXML);
    	menu.add(loadOneXML);
    	menu.addSeparator();
    	menu.add(properties);
    	menu.add(about);
    	menu.addSeparator();
    	menu.add(exitItem);
    	
    	setJMenuBar(menuBar);
    	menuBar.add(menu);
    	
    	xmlFilter = new FileNameExtensionFilter("XML files", "xml");
    	xmlChooser = new JFileChooser();
    	xmlChooser.setFileFilter(xmlFilter);
    }

	/**
	 * Generate left side buttons.
	 */
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
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
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
		if(source == listFrame.export){
			Event exportedEvent = listFrame.list.getContent().get(listFrame.latestSelectedRow);
			int choose = generateExportQuestion();
				
			if(choose == 0){
				dataServiceImpl.oneEventToXml(exportedEvent);
			}
			if(choose == 1) {
				dataServiceImpl.oneEventToIcs(exportedEvent);
			}
				
			if(choose != -1){
				generateMessage(true, this);
			}
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
		if(source == properties){
			new PropertiesFrame(this, ssi).setVisible(true);
		}
	}
    
	/**
	 * Generate message.
	 *
	 * @param flag 'true' for positive message, 'false'e for an error 
	 * @param c parent component 
	 */
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
	
	/**
	 * Generate confirm question
	 *
	 * @param c parent component
	 * @return 1 if "yes" was chosen, 0 if "no"	
	 *  
	 */
	static public int generateQuestion(Component c){
		int choose = JOptionPane.showConfirmDialog(c,
												  "ARE YOU SURE ?", 
												  "Warning",
												  JOptionPane.YES_NO_OPTION);
		return choose;
	}
	
	private int generateExportQuestion(){
		String[] options = new String[2];
		options[0] = new String("XML format");
		options[1] = new String("Evolution format");
		int choose = JOptionPane.showOptionDialog(this,
									   			 "Export selected event to: ",
												 "Choose wisely", 
												 0,
												 JOptionPane.INFORMATION_MESSAGE,
												 null,
												 options,
												 null);
		
		return choose;
	}
}
