package com.goldentwo.view;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

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

public class ListFrame implements ListSelectionListener{
	
	private UserInterface ui;
	private int latestSelectedRow;
	private int currentPage;
	Vector<Vector<String>> allEvents;
	JTable table;
	
	public ListFrame(UserInterface ui){
		this.ui = ui;
		currentPage = 0;
		allEvents = new Vector<>();
		makeJList();
	}
	
	public void makeJList(){

		for(Event e : ui.dataServiceImpl.getAllEvents()){
			Vector<String> rowData = new Vector<>();
			rowData.add(e.getName());
			rowData.add(e.getDate().toString());
			allEvents.add(rowData);
		}
		
		Vector<String> colNames = new Vector<>(2);
		colNames.add("Name");
		colNames.add("Data");
		
		table = new JTable(getFirstTenEvents(allEvents), colNames);	

		table.setBounds(225, 140, 605, 300);
		table.setBackground(null);
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
	
	private Vector<Vector<String>> getFirstTenEvents(Vector<Vector<String>> allEvents){
		Vector<Vector<String>> v = new Vector<Vector<String>>();
		
		for(int i = 0; i < 10 && i < allEvents.size() ; i++){
			v.add(allEvents.get(i));
		}
		
		return v;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()){
			String str = e.getSource().toString();
			latestSelectedRow = str.charAt(str.length() - 2) - 48;
		}
	}
	
	private void fillTable(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		
		
		for(int i = 10 * currentPage ; i < 10 * (currentPage + 1) && i < allEvents.size() ; i++){
			Vector<Object> row = new Vector<>();
			
			row.add(allEvents.get(i).get(0));
			row.add(allEvents.get(i).get(1)); 

			model.addRow(row);
		}
	}
}
