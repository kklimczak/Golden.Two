package com.goldentwo.view;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.goldentwo.data.Event.Event;
import com.goldentwo.utils.Pagination.*;

public class ListFrame implements ListSelectionListener{
	
	private UserInterface ui;
	int latestSelectedRow;
	int currentPage;
	int totalElements, totalPages;
	Vector<Vector<String>> events;
	JTable table;
	JButton prev, next, details, exportToXML, delete;
	JLabel eventCounter;
	Page<Event> list;
	
	Date dateFrom, dateTo;
	
	public ListFrame(UserInterface ui){
		this.ui = ui;
		currentPage = 1;
		events = new Vector<>();
		dateFrom = null;
		dateTo = null;
		latestSelectedRow = -1;
		list = null;
		initJList();
		generateButtons();
	}
	
	public void initJList(){
		Page<Event> page = ui.isEvents ? 
				ui.dataServiceImpl.getSortedAndFilteredEvents(ui.sort, ui.filter, currentPage) :
				ui.dataServiceImpl.getSortedAndFilteredEventsWithAlarm(ui.sort, ui.filter, currentPage);
		totalElements = page.getTotalElements();
		totalPages = page.getTotalPages();
		
		for(Event e : page.getContent()){
			Vector<String> rowData = new Vector<>();
			rowData.add(e.getName());
			rowData.add(e.getDate().toString());
			events.add(rowData);
		}
		
		Vector<String> colNames = new Vector<>(2);
		colNames.add("Name");
		colNames.add("Data");
		
		table = new JTable(events, colNames);	
		fillTable();
		
		table.setBounds(225, 140, 605, 300);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 1, 1, color);
		table.setBorder(border);
		
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(this);


		ui.listComponentList.add(table);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()){
			String str = e.getSource().toString();
			latestSelectedRow = str.charAt(str.length() - 2) - 48;
			details.setEnabled(true);
			exportToXML.setEnabled(true);
			delete.setEnabled(true);
		}
	}
	
	void fillTable(){
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		if(dateFrom == null || dateTo == null){
			list = ui.isEvents ?
				   ui.dataServiceImpl.getSortedAndFilteredEvents(ui.sort, ui.filter, currentPage) :
				   ui.dataServiceImpl.getSortedAndFilteredEventsWithAlarm(ui.sort, ui.filter, currentPage);
		}else{
			list = ui.dataServiceImpl.getAllSortedAndFilteredEventsBetweenDates(dateFrom, dateTo, ui.isEvents, ui.sort, ui.filter, currentPage);
		}
		

		totalElements = list.getTotalElements();
		totalPages = list.getTotalPages();
		
		for(Event e : list.getContent()){
			Vector<Object> row = new Vector<>();
			
			row.add(e.getName());
			Date date = ui.isEvents ? e.getDate() : e.getAlarm();
			String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
			row.add(dateStr);

			model.addRow(row);
		}
	}

	private void generateButtons(){
		prev = new JButton("Previous");
		next = new JButton("Next");
		details = new JButton("Details");
		exportToXML = new JButton("Export to XML");
		delete = new JButton("Delete");
		
		prev.setBounds(225, 440, 100, 25);
		next.setBounds(730, 440, 100, 25);
		details.setBounds(730, 100, 100, 25);
		exportToXML.setBounds(560, 100, 150, 25);
		delete.setBounds(225, 100, 100, 25);
		
		ui.listComponentList.add(prev);
		ui.listComponentList.add(next);
		ui.listComponentList.add(details);
		ui.listComponentList.add(exportToXML);
		ui.listComponentList.add(delete);
		
		eventCounter = new JLabel("Generated Label");
		eventCounter.setBounds(500, 440, 100, 25);
		ui.listComponentList.add(eventCounter);
		updateEventCounterLabel();
		prev.addActionListener(ui);
		next.addActionListener(ui);
		details.addActionListener(ui);
		exportToXML.addActionListener(ui);
		delete.addActionListener(ui);
		
		if(latestSelectedRow == -1){
			details.setEnabled(false);
			exportToXML.setEnabled(false);
			delete.setEnabled(false);
		}
		
		updateButtons();
	}
	
    void updateEventCounterLabel(){		
		eventCounter.setText(10*(currentPage)-9 + " - " + (10*currentPage > totalElements ? totalElements : 10*currentPage)  + " of " + totalElements);
	}
    
    void updateButtons(){
    	prev.setEnabled(false);
		if(totalPages == currentPage){
			next.setEnabled(false);
		}else{
			next.setEnabled(true);
		}
    }
    
    void update(){
    	currentPage = 1;
    	fillTable();
    	updateEventCounterLabel();
    	updateButtons();
    }
    
    
	
}
