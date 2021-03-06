package com.goldentwo.view;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.annotation.Generated;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.goldentwo.data.Settings.Settings;
import com.goldentwo.service.SettingsServiceImpl;

public class PropertiesFrame extends JFrame implements ActionListener {
	
	private UserInterface ui;
	private SettingsServiceImpl ssi;
	private JFileChooser directoryChooser;
	private JRadioButton metal, nimbus, gtk, motif;
	private ButtonGroup bGroup;
	private JComboBox<String> soundChooser;
	private JButton listenButton, accept, changePath;
	private String[] soundPaths;
	private int lookAndFeelNumber;
	private String exportPath;
	private JLabel theme, sound, path;
	
	
	public PropertiesFrame(UserInterface ui, SettingsServiceImpl ssi) {
		this.ui = ui;
		this.ssi = ssi;
		exportPath = null;
	    setTitle("Properties");
	    setSize(240, 350);
	    setLocationRelativeTo(null);
		setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        
        init();
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            	setDefaultSettings();
            }
        });

	}
	
	private void init() {
		
		directoryChooser = new JFileChooser();
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		createSoundsPaths();
		generateButtons();
		generateSoundComponents(); 
		
		setDefaultSettings();
        changeLookAndFeel();
        updateSound();
        generateLabels();
	}
	
	private void generateLabels(){
		theme = new JLabel("Choose Organizer's theme");
		sound = new JLabel("Choose alarm sound");
		path = new JLabel("Choose default export directory");
		
		theme.setBounds(10, 5, 250, 30);
		sound.setBounds(10, 125, 250, 30);
		path.setBounds(10, 190, 250, 30);
		
		add(theme);
		add(sound);
		add(path);
	}
	
	private void updateSound() {
		String str = ui.ac.getSoundPath();
		int i = 0;
		for(String s : soundPaths){
			if(s.equals(str)){
				soundChooser.setSelectedIndex(i);
				break;
			}
			i++;
		}
	}

	private void createSoundsPaths(){
		soundPaths = new String[5];
		soundPaths[0] = "audio/alarmSound.wav";
		soundPaths[1] = "audio/ahem.wav";
		soundPaths[2] = "audio/applause.wav";
		soundPaths[3] = "audio/arrow.wav";
		soundPaths[4] = "audio/charge.wav";
	}
	
	private void generateSoundComponents() {
		String[] sounds = {"Default", "Ahem...", "Applause", "Arrow", "Charge"};
		soundChooser = new JComboBox<>(sounds);
		listenButton = new JButton("Listen");
		soundChooser.setBounds(10, 150, 100, 30);
		listenButton.setBounds(130, 150, 100, 30);
		soundChooser.addActionListener(this);
		listenButton.addActionListener(this);
		
		add(soundChooser);
		add(listenButton);
	}

	private void generateButtons() {
		metal = new JRadioButton("Metal");
		nimbus = new JRadioButton("Nimbus");
		gtk = new JRadioButton("GTK+");
		motif = new JRadioButton("Motif");
		bGroup = new ButtonGroup();
		
		metal.setBounds(10, 30, 100, 20);
		nimbus.setBounds(10, 50, 100, 20);
		motif.setBounds(10, 70, 100, 20);
		gtk.setBounds(10, 90, 100, 20);
		
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
		
		accept = new JButton("Save and quit");
		accept.setBounds(50, 300, 140, 30);
		accept.addActionListener(this);
		
		changePath = new JButton("Set path");
		changePath.setBounds(10, 215, 220, 30);
		changePath.addActionListener(this);
		
		add(accept);
		add(changePath);
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
			e.printStackTrace();
		}
		
		updateWindows();
	}
	
	public void setDefaultSettings(){
		lookAndFeelNumber = ssi.getSettings().getLookAndFeelNumber();
		changeLookAndFeel();
		ui.ac.setSoundPath(soundPaths[ssi.getSettings().getAlarmSound()]);
		exportPath = ssi.getSettings().getDefaultExportPath();
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
		
		if(source == listenButton){
			setAlarmSound();
				
			try {
				ui.ac.makeAlarmSound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(source == accept){
			setAlarmSound();
			int alarmSound = soundChooser.getSelectedIndex() == -1 ? ssi.getSettings().getAlarmSound() : soundChooser.getSelectedIndex();
			ssi.updateSettings(new Settings(1, exportPath, alarmSound, lookAndFeelNumber));
			dispose();
		}
		
		if(source == changePath){
			if(directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				exportPath = directoryChooser.getSelectedFile().getAbsolutePath();
			}
		}
	}
	
	private void setAlarmSound(){
		String str = (String) soundChooser.getSelectedItem();
		
		if(str.equals("Default")) ui.ac.setSoundPath(soundPaths[0]);
		else if(str.equals("Ahem...")) ui.ac.setSoundPath(soundPaths[1]);
			 else if(str.equals("Applause")) ui.ac.setSoundPath(soundPaths[2]);
			   	  else if(str.equals("Arrow")) ui.ac.setSoundPath(soundPaths[3]);
					   else if(str.equals("Charge")) ui.ac.setSoundPath(soundPaths[4]);
	}
}
