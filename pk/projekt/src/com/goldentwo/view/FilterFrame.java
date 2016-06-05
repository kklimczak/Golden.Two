package com.goldentwo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileVisitResult;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.goldentwo.utils.Date.DateConverter;
import com.goldentwo.utils.Pagination.Direction;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Sort;

public class FilterFrame extends JFrame implements ActionListener{

	private UserInterface ui;
	JCheckBox sortCheckBox, filterCheckBox;
	JComboBox<Object> sortName, sortDirection, filtrName;
	JTextField filtrValue;
	JFormattedTextField dateFrom, dateTo;
	JButton acceptButton, cancelButton;
	
	public FilterFrame(UserInterface ui){
		this.ui = ui;
		init();
	}

	private void init() {
		 setTitle("Filter manager");
		 setSize(270, 340);
		 setLocationRelativeTo(null);	
		 setLayout(null);
	     setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     setAlwaysOnTop(true);
	        
	     sortCheckBox = new JCheckBox("Enable sorting");
	     filterCheckBox = new JCheckBox("Enable filtering");
	     sortName = new JComboBox<>();
	     sortDirection = new JComboBox<>();
	     filtrName = new JComboBox<>();
	     filtrValue = new JTextField();
	     acceptButton = new JButton("Accept");
	     cancelButton = new JButton("Cancel");
	     dateFrom = null;
	     dateTo = null;
	     
	     printCheckBox();
	     printComponents();
	     printButtons();
	}

	private void printCheckBox() {
		sortCheckBox.setBounds(10, 30, 280, 30);
		sortCheckBox.addActionListener(this);
		filterCheckBox.setBounds(10, 130, 280, 30);
		filterCheckBox.addActionListener(this);
		
		if(ui.sort != null){
			sortCheckBox.setSelected(true);
		}
		
		if(ui.filter != null || ui.listFrame.dateFrom != null){
			filterCheckBox.setSelected(true);
		}
		
		add(sortCheckBox);
		add(filterCheckBox);
	}
	
	private void printComponents() {
		sortName.addItem("ID");
		sortName.addItem("Name");
		sortName.addItem("Description");
		sortName.addItem("Place");
		sortName.addItem("Date");
		sortName.setEnabled(sortCheckBox.isSelected());
		
		sortDirection.addItem("Descending");
		sortDirection.addItem("Ascending");
		sortDirection.setEnabled(sortCheckBox.isSelected());
		
		filtrName.addItem("ID");
		filtrName.addItem("Name");
		filtrName.addItem("Description");
		filtrName.addItem("Place");
		filtrName.addItem("Date");
		filtrName.setEnabled(filterCheckBox.isSelected());
		filtrName.addActionListener(this);
		
		sortName.setBounds(10, 60, 120, 30);
		sortDirection.setBounds(140, 60, 120, 30);
		filtrName.setBounds(10, 160, 120, 30);
		filtrValue.setBounds(10, 210, 120, 30);
		
		filtrValue.setEnabled(filterCheckBox.isSelected());
		
		MaskFormatter dateMask = null;
		try {
			dateMask = new MaskFormatter("##-##-####");
		}catch(ParseException e){
			e.printStackTrace();
		}
		dateMask.setPlaceholderCharacter('@');
		dateFrom = new JFormattedTextField(dateMask);
		dateTo = new JFormattedTextField(dateMask);
		
		dateFrom.setBounds(10, 210, 120, 30);
		dateTo.setBounds(140, 210, 120, 30);
		dateFrom.setVisible(false);
		dateTo.setVisible(false);
		
		add(sortName);
		add(sortDirection);
		add(filtrName);
		add(filtrValue);
		add(dateFrom);
		add(dateTo);
	}
	
	private void printButtons(){
		acceptButton.setBounds(10, 280, 100, 40);
		cancelButton.setBounds(160, 280, 100, 40);
		acceptButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		add(acceptButton);
		add(cancelButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sortCheckBox){
			sortName.setEnabled(!sortName.isEnabled());
			sortDirection.setEnabled(!sortDirection.isEnabled());
		}
		
		if(e.getSource() == filterCheckBox){
			filtrName.setEnabled(!filtrName.isEnabled());
			filtrValue.setEnabled(!filtrValue.isEnabled());
		}
		
		if(e.getSource() == filtrName){
			if(filtrName.getSelectedItem().equals("Date")){
				filtrValue.setVisible(false);
				dateFrom.setVisible(true);
				dateTo.setVisible(true);
			}else{
				if(!filtrValue.isVisible()){
					filtrValue.setVisible(true);
					dateFrom.setVisible(false);
					dateTo.setVisible(false);
				}
			}
		}
		
		if(e.getSource() == cancelButton){
			dispose();
		}
		
		if(e.getSource() == acceptButton){
			update();
			dispose();
		}
		
	}
	
	//private void print

	private void update() {
		if(sortCheckBox.isSelected()){
			ui.sort = new Sort(null, null);
			ui.sort.setField((String)sortName.getSelectedItem());
			if(sortDirection.getSelectedItem().equals("Descending")){
				ui.sort.setDirection(Direction.DESC);
			}else{
				ui.sort.setDirection(Direction.ASC);
			}
		}else{
			ui.sort = null;
		}
		
		if(filterCheckBox.isSelected()){
			if(filtrName.getSelectedItem().equals("Date")){
				generateMessage(dateCheckAndSet());
			}else{
				ui.filter = new Filter(null, null);
				ui.filter.setField((String) filtrName.getSelectedItem());
				ui.filter.setValue(filtrValue.getText());
			}
		}else{
			ui.filter = null;
			ui.listFrame.dateFrom = null;
			ui.listFrame.dateTo = null;
		}
		
		ui.listFrame.update();
	}
	
	private boolean dateCheckAndSet(){
		if(dateFrom.getText().equals("@@-@@-@@@@") || dateTo.getText().equals("@@-@@-@@@@")){
			return false;
		}
		
		String[] dateFromSplit = dateFrom.getText().split("-");
		String[] dateToSplit = dateTo.getText().split("-");
		
		if(dateFromSplit[0] == "00" || Integer.parseInt(dateFromSplit[0]) > 31 || 
		   dateFromSplit[1] == "00" || Integer.parseInt(dateFromSplit[1]) > 12 ||
		   dateToSplit[0] == "00" || Integer.parseInt(dateToSplit[0]) > 31 || 
		   dateToSplit[1] == "00" || Integer.parseInt(dateToSplit[1]) > 12){
			
			return false;
		}
		
		ui.filter = null;
		ui.listFrame.dateFrom = DateConverter.stringToDate(Integer.parseInt(dateFromSplit[2]), 
														   Integer.parseInt(dateFromSplit[1]), 
														   Integer.parseInt(dateFromSplit[0]));
		ui.listFrame.dateTo = DateConverter.stringToDate(Integer.parseInt(dateToSplit[2]), 
														 Integer.parseInt(dateToSplit[1]), 
													     Integer.parseInt(dateToSplit[0]));
		
		return true;
	}
	
	private void generateMessage(boolean flag){
		if(flag){
			JOptionPane.showMessageDialog(this,
					  "Success!",
					  "Message",
					  JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this,
					  "Incorrect data!",
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
		}

	}
}
