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
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.goldentwo.data.Event.Event;
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
	private JMenuItem filterItem, exitItem, deleteOldEventsItem, saveToXML, loadFromXML;
	
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
    	deleteOldEventsItem = new JMenuItem("Delete old events");
    	saveToXML = new JMenuItem("Save data to XML");
    	loadFromXML = new JMenuItem("Load data from XML");
    	
    	exitItem = new JMenuItem("Exit");
    	filterItem.addActionListener(this);
    	exitItem.addActionListener(this);
    	deleteOldEventsItem.addActionListener(this);
    	saveToXML.addActionListener(this);
    	loadFromXML.addActionListener(this);
    	
    	menu.add(filterItem);
    	menu.add(deleteOldEventsItem);
    	menu.addSeparator();
    	menu.add(saveToXML);
    	menu.add(loadFromXML);
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
			new AddEventFrame(dataServiceImpl, null).setVisible(true);
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
		}
		if(source == listFrame.details){
			Event updateEvent = listFrame.list.getContent().get(listFrame.latestSelectedRow);
			new AddEventFrame(dataServiceImpl, updateEvent).setVisible(true);
		}
		if(source == listFrame.exportToXML){
			Event exportedEvent = listFrame.list.getContent().get(listFrame.latestSelectedRow);
			dataServiceImpl.oneEventToXml(exportedEvent);
			generateMessage(true);
		}
		if(source == listFrame.delete){
			if(generateQuestion() == 0){
				Event deletedEvent = listFrame.list.getContent().get(listFrame.latestSelectedRow);
				dataServiceImpl.deleteEvent(deletedEvent.getId());
				generateMessage(true);
				listFrame.update();	
			}
		}
		if(source == saveToXML){
			dataServiceImpl.allEventsToXml();
			generateMessage(true);
		}
		if(source == loadFromXML){
			generateMessage(!(dataServiceImpl.allEventsFromXml() == null));
		}
	}
    
	private void generateMessage(boolean flag){
		if(flag){
			JOptionPane.showMessageDialog(this,
					  "Success!",
					  "Message",
					  JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this,
					  "Cannot load date from XML file!",
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
		}

	}
	
	private int generateQuestion(){
		int choose = JOptionPane.showConfirmDialog(this,
												  "ARE YOU SURE ?", 
												  "Warning",
												  JOptionPane.YES_NO_OPTION);
		return choose;
	}
}
