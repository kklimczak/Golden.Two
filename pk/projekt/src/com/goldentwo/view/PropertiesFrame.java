package com.goldentwo.view;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PropertiesFrame extends JFrame implements ItemListener, ActionListener {
	
	private UserInterface ui;
	private JFileChooser directoryChooser;
	private JRadioButton metal, nimbus, gtk, motif;
	private ButtonGroup bGroup;
	private JComboBox<String> soundChooser;
	private int lookAndFeelNumber;
	
	
	public PropertiesFrame(UserInterface ui) {
		this.ui = ui;
	    setTitle("Properties");
	    setSize(300, 470);
	    setLocationRelativeTo(null);
		setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        
        init();
        setLookAndFeelNumber();
        changeLookAndFeel();
	}

	private void init() {
		metal = new JRadioButton("Metal");
		nimbus = new JRadioButton("Nimbus");
		gtk = new JRadioButton("GTK+");
		motif = new JRadioButton("Motif");
		bGroup = new ButtonGroup();
		
		metal.setBounds(10, 30, 300, 20);
		nimbus.setBounds(10, 50, 300, 20);
		motif.setBounds(10, 70, 300, 20);
		gtk.setBounds(10, 90, 300, 20);
		
		metal.addActionListener(this);
		nimbus.addActionListener(this);
		motif.addActionListener(this);
		gtk.addActionListener(this);
		
		bGroup.add(metal);
		bGroup.add(nimbus);
		bGroup.add(motif);
		bGroup.add(gtk);
		
		add(metal);
		add(nimbus);
		add(gtk);
		add(motif);
	}
	
	private void changeLookAndFeel(){
		try {
			switch(lookAndFeelNumber){
			case 1:
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				metal.setSelected(true);
				break;
			case 2:
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				nimbus.setSelected(true);
				break;
			case 3:
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				motif.setSelected(true);
				break;
			case 4:
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
				gtk.setSelected(true);
				break;
			default:
				String lookAndFeelStr = UIManager.getLookAndFeel().getID();
				if(lookAndFeelStr.equals("Metal"))metal.setSelected(true);
				else if(lookAndFeelStr.equals("Nimbus"))nimbus.setSelected(true);
					 else if(lookAndFeelStr.equals("Motif"))motif.setSelected(true);
					 	  else if(lookAndFeelStr.equals("GTK"))gtk.setSelected(true);
				
			}
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateWindows();
	}
	
	private void setLookAndFeelNumber(){
		//METODA OD KONRADA
	}
	
	private void updateWindows(){
	    for(Window window : JFrame.getWindows()) {
	        SwingUtilities.updateComponentTreeUI(window);
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(source == metal){
			lookAndFeelNumber = 1;
			changeLookAndFeel();
		}
		if(source == nimbus){
			lookAndFeelNumber = 2;
			changeLookAndFeel();
		}
		if(source == motif){
			lookAndFeelNumber = 3;
			changeLookAndFeel();
		}
		if(source == gtk){
			lookAndFeelNumber = 4;
			changeLookAndFeel();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}
}
