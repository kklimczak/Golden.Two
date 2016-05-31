package com.goldentwo.view;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ListFrame {
	
	private UserInterface ui;
	
	public ListFrame(UserInterface ui){
		this.ui = ui;
		makeJTable();
	}
	
	public void makeJTable(){
		JTextPane table = new JTextPane();	
		ui.listComponentList.add(table);
		table.setEditable(false);
		table.setBounds(250, 150, 400, 400);
		table.setText("PROBA\nPROBA\nPROBA");
		table.setBackground(Color.LIGHT_GRAY);
	}

}
