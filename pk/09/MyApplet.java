
import java.awt.*;
import java.awt.event.*; 
import java.applet.*;


public class MyApplet extends Applet implements ActionListener, ItemListener {  

	
	boolean bokA = false,
			bokB = false,
			bokC = false;
	
	public void paint (Graphics g) {
		wybor.select(0);
		if (bokA == true) {
			g.setColor(this.getForeground()); 
			g.drawLine(50,50, 80, 190);
			przycisk1.setLabel("wylacz bokA");
			cb1.setState(true);
		}
		else {
			g.setColor(this.getBackground()); 
			g.drawLine(50,50, 80, 190);
			przycisk1.setLabel("WLACZ bokA");
			cb1.setState(false);
		}
		
		if (bokB == true) {
			g.setColor(Color.BLACK);
			g.drawLine(80, 190, 190, 50);
			przycisk2.setLabel("wylacz bokA");
			cb2.setState(true);
		}
		else {
			g.setColor(Color.WHITE); 
			g.drawLine(80, 190, 190, 50);
			przycisk2.setLabel("WLACZ bokB");
			cb2.setState(false);
		}
		
		if (bokC == true) {
			g.setColor(Color.BLACK);
			g.drawLine(190, 50, 50, 50);
				przycisk3.setLabel("wylacz bokC");
				cb3.setState(true);
		}
		else {
			g.setColor(Color.WHITE);
			g.drawLine(190, 50, 50, 50);
				przycisk3.setLabel("WLACZ bokC");
				cb3.setState(false);
		}
	}
	
	
	public void actionPerformed(ActionEvent evt) {
		
		if (evt.getSource() == przycisk1) {
			bokA = !bokA; 
		}
		
		if (evt.getSource() == przycisk2){
			bokB = !bokB; 
		}
		
		if (evt.getSource() == przycisk3){
			bokC = !bokC;
		}
		
		repaint(); 	// WAZNE, zeby funkcja paint wywolywala sie ponownie 
	}	// koniec funkcji actionPerformed
	
	
	
	// Deklaracje obiektow - zmiennych reprezentujacych przyciski i okno tekstowe
	Button 	przycisk1, 
			przycisk2,
			przycisk3; 
	
	Checkbox cb1,
			 cb2,
			 cb3;
	
	Choice wybor;
	
	TextField oknoTekstowe; 
	
	public void init(){
		
		setSize(500,200);
		przycisk1 = new Button("WLACZ A");	// inicjalizacja przycisku
		add(przycisk1);						// wyswietla przycisk w oknie appletu
		przycisk1.addActionListener(this);	// przypisuje przyciskowi dzialanie opisane w actionPerformed
		
		przycisk2 = new Button("WLACZ B");	
		add(przycisk2);						
		przycisk2.addActionListener(this);	

		przycisk3 = new Button("WLACZ C");	
		add(przycisk3);						
		przycisk3.addActionListener(this);	

		cb1 = new Checkbox("BOK A");
		add(cb1);
		cb1.addItemListener(this);
		
		cb2 = new Checkbox("BOK B");
		add(cb2);
		cb2.addItemListener(this);
		
		cb3 = new Checkbox("BOK C");
		add(cb3);
		cb3.addItemListener(this);
		
		wybor = new Choice();
		wybor.add("WYBIERZ BOK");
		wybor.add("BOK A");
		wybor.add("BOK B");
		wybor.add("BOK C");
		add(wybor);
		wybor.addItemListener(this);
		
	}	// koniec funkcji init


	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getItemSelectable() == cb1){
			bokA = !bokA; 
		}

		if(e.getItemSelectable() == cb2){
			bokB = !bokB; 
		}

		if(e.getItemSelectable() == cb3){
			bokC = !bokC; 
		}
		
		if(e.getItemSelectable() == wybor){
			
			if(((Choice)e.getItemSelectable()).getSelectedItem() == "BOK A"){
				bokA = !bokA;
			}

			if(((Choice)e.getItemSelectable()).getSelectedItem() == "BOK B"){
				bokB = !bokB; 
			}

			if(((Choice)e.getItemSelectable()).getSelectedItem() == "BOK C"){
				bokC = !bokC;
			}
			
		}
		
		repaint();
		
	}
	
}		// koniec klasy Appletu
