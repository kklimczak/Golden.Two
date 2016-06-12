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

// TODO: Auto-generated Javadoc
/**
 * Klasa generujaca komponenty listy wydarzen
 */
public class ListFrame implements ListSelectionListener{
	
	/** The ui. */
	private UserInterface ui;
	
	/** Ostatnio wybrany wiersz */
	int latestSelectedRow;
	
	/** Aktualna strona listy wydarzen */
	int currentPage;
	
	/** Ilosc wszystkich elementow/stron */
	int totalElements, totalPages;
	
	/** Lista wydarzen dla danej strony (max 10) */
	Vector<Vector<String>> events;
	
	/** Tabela z wydarzeniami */
	JTable table;
	
	/** Przyciski funkcyjne */
	JButton prev, next, details, export, delete;
	
	/** Label */
	JLabel eventCounter;
	
	/** Zwracana z metody obsugujacej dane. Zawiera wszystkie dane dla strony (wydarzenia, nr strony, ilosc wszystkich wydarzen itd) */
	Page<Event> list;
	
	/** Daty */
	Date dateFrom, dateTo;
	
	/**
	 * Inicuje nowy obiekt ListFrame
	 *
	 * @param ui glowny komponent programu
	 */
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
	
	/**
	 * Inicjuje liste wydarzen
	 */
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

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()){
			String str = e.getSource().toString();
			latestSelectedRow = str.charAt(str.length() - 2) - 48;
			details.setEnabled(true);
			export.setEnabled(true);
			delete.setEnabled(true);
		}
	}
	
	/**
	 * Wypelnia tabele wydarzeniami
	 */
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

	/**
	 * Generuje przyciski widoku listy wydarzen
	 */
	private void generateButtons(){
		prev = new JButton("Previous");
		next = new JButton("Next");
		details = new JButton("Details");
		export = new JButton("Export event");
		delete = new JButton("Delete");
		
		prev.setBounds(225, 440, 100, 25);
		next.setBounds(730, 440, 100, 25);
		details.setBounds(730, 100, 100, 25);
		export.setBounds(560, 100, 150, 25);
		delete.setBounds(225, 100, 100, 25);
		
		ui.listComponentList.add(prev);
		ui.listComponentList.add(next);
		ui.listComponentList.add(details);
		ui.listComponentList.add(export);
		ui.listComponentList.add(delete);
				
		eventCounter = new JLabel("Generated Label");
		eventCounter.setBounds(500, 440, 100, 25);
		ui.listComponentList.add(eventCounter);
		updateEventCounterLabel();
		prev.addActionListener(ui);
		next.addActionListener(ui);
		details.addActionListener(ui);
		export.addActionListener(ui);
		delete.addActionListener(ui);
		
		if(latestSelectedRow == -1){
			details.setEnabled(false);
			export.setEnabled(false);
			delete.setEnabled(false);
		}
		
		updateButtons();
	}
	
    /**
     * Uaktualnia licznik wydarzen
     */
    void updateEventCounterLabel(){		
		eventCounter.setText(10*(currentPage)-9 + " - " + (10*currentPage > totalElements ? totalElements : 10*currentPage)  + " of " + totalElements);
	}
    
    /**
     * Uaktualnia przyciski zmiany stron listy
     */
    void updateButtons(){
    	prev.setEnabled(false);
		if(totalPages == currentPage || totalElements == 0){
			next.setEnabled(false);
		}else{
			next.setEnabled(true);
		}
    }
    
    /**
     * Uaktualnia cala liste wydarzen
     */
    void update(){
    	currentPage = 1;
    	fillTable();
    	updateEventCounterLabel();
    	updateButtons();
    }
}
