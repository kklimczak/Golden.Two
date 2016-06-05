package com.goldentwo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileVisitResult;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.goldentwo.utils.Pagination.Direction;
import com.goldentwo.utils.Pagination.Filter;
import com.goldentwo.utils.Pagination.Sort;

public class FilterFrame extends JFrame implements ActionListener{

	private UserInterface ui;
	JCheckBox sortCheckBox, filterCheckBox;
	JComboBox<Object> sortName, sortDirection, filtrName;
	JTextField filtrValue;
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
	     
	     printCheckBox();
	     printComboBoxes();
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
		
		add(sortCheckBox);
		add(filterCheckBox);
	}
	
	private void printComboBoxes() {
		sortName.addItem("ID");
		sortName.addItem("Name");
		sortName.addItem("Description");
		sortName.addItem("Place");
		sortName.addItem("Date");
		sortName.addActionListener(this);
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
		
		sortName.setBounds(10, 60, 120, 30);
		sortDirection.setBounds(140, 60, 120, 30);
		filtrName.setBounds(10, 160, 120, 30);
		filtrValue.setBounds(10, 210, 120, 30);
		
		filtrValue.setEnabled(filterCheckBox.isSelected());
		
		add(sortName);
		add(sortDirection);
		add(filtrName);
		add(filtrValue);
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
		
		if(e.getSource() == cancelButton){
			dispose();
		}
		
		if(e.getSource() == acceptButton){
			update();
			dispose();
		}
		
	}

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
			ui.listFrame.update();
			ui.filter = new Filter(null, null);
			ui.filter.setField((String) filtrName.getSelectedItem());
			ui.filter.setValue(filtrValue.getText());
		}else{
			ui.filter = null;
		}
		
		ui.listFrame.fillTable();
		ui.listFrame.updateEventCounterLabel();
		ui.listFrame.updateButtons();
	}
}
