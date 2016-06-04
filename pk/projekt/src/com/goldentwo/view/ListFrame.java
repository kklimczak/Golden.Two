package com.goldentwo.view;

import java.awt.Color;
import java.awt.Font;
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
	private int latestSelectedRow;
	int currentPage;
	int totalElements, totalPages;
	Vector<Vector<String>> events;
	JTable table;
	JButton prev, next;
	JLabel eventCounter;
	
	public ListFrame(UserInterface ui){
		this.ui = ui;
		currentPage = 1;
		events = new Vector<>();
		initJList();
		generateButtons();
	}
	
	public void initJList(){
		ui.sort = new Sort("date",Direction.ASC);
		Page<Event> page = ui.dataServiceImpl.getSortedAndFilteredEvents(ui.sort, ui.filter, currentPage);
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
		table.getColumnModel().getColumn(1).setWidth(400);
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
		}
	}
	
	void fillTable(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
	
		for(Event e : ui.dataServiceImpl.getSortedAndFilteredEvents(ui.sort, ui.filter, currentPage).getContent()){
			Vector<Object> row = new Vector<>();
			
			row.add(e.getName());
			row.add(e.getDate()); 

			model.addRow(row);
		}
	}

	private void generateButtons(){
		prev = new JButton("Previous");
		next = new JButton("Next");
		
		prev.setBounds(225, 440, 100, 25);
		next.setBounds(730, 440, 100, 25);
		
		ui.listComponentList.add(prev);
		ui.listComponentList.add(next);
		
		eventCounter = new JLabel("Generated Label");
		eventCounter.setBounds(500, 440, 100, 25);
		ui.listComponentList.add(eventCounter);
		updateEventCounterLabel();
		prev.addActionListener(ui);
		next.addActionListener(ui);
		
		prev.setEnabled(false);
		if(totalPages == currentPage){
			next.setEnabled(false);
		}
	}
	
    void updateEventCounterLabel(){		
		eventCounter.setText(10*(currentPage)-9 + " - " + (10*currentPage > totalElements ? totalElements : 10*currentPage)  + " of " + totalElements);
	}
	
}