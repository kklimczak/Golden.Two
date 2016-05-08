import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;


public class MyApplet extends Applet implements ActionListener, ItemListener {

	// Deklaracje obiektow
		Button 	przycisk1, przycisk2, button1, button2;
		TextField oknoTekstowe;
		CheckboxGroup checkboxgroup1;
		Checkbox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8;
        TextField text1, text2, text3;
        Choice choice1;
        menuFrame fullmenuWindow;
        
        public void init(){
			
			// Powieksza rozmiar okna appletu
			setSize(1200,400); // albo takze funkcja resize(400,400);
			
			oknoTekstowe = new TextField(20);	// inicjalizacja okienka tekstowego na 20 znakow 
			add(oknoTekstowe); 					// wyswietla okienko w obszarze appletu
				

			przycisk1 = new Button("Welcome");	// inicjalizacja przycisku
			add(przycisk1);						// wyswietla przycisk w oknie appletu
			przycisk1.addActionListener(this);	// przypisuje przyciskowi dzialanie opisane w actionPerformed

			
			przycisk2 = new Button("to JAVA !!!");	
			add(przycisk2);						
			przycisk2.addActionListener(this);	
			
			///CHECKBOXY
			checkbox1 = new Checkbox("1");
            add(checkbox1);
            checkbox1.addItemListener(this);
            
            checkbox2 = new Checkbox("2");
            add(checkbox2);
            checkbox2.addItemListener(this);

            checkbox3 = new Checkbox("3");
            add(checkbox3);
            checkbox3.addItemListener(this);

            checkbox4 = new Checkbox("4");
            add(checkbox4);
            checkbox4.addItemListener(this);

            checkbox5 = new Checkbox("5");
            add(checkbox5);
            checkbox5.addItemListener(this);

            text1 = new TextField(20);
            add(text1);
            
            ///RADIOBUTTONS///
            checkboxgroup1 = new CheckboxGroup();

            checkbox6 = new Checkbox("1", false, checkboxgroup1);
            add(checkbox6);
            checkbox6.addItemListener(this);

            checkbox7 = new Checkbox("2", false, checkboxgroup1);
            add(checkbox7);
            checkbox7.addItemListener(this);

            checkbox8 = new Checkbox("3", false, checkboxgroup1);
            add(checkbox8);
            checkbox8.addItemListener(this);

            text2 = new TextField(20);
            add(text2);
            
            ///CHOICE///
            text3 = new TextField(20);
            add(text3);
            choice1 = new Choice();
            choice1.add("Pozycja 1");
            choice1.add("selection2");
            choice1.add("selection3");
            choice1.add("selection4");
            add(choice1); 
            choice1.addItemListener(this);
            
            //MENUFRAME
            button1 = new Button("Show full menu window");
            add(button1);
            button1.addActionListener(this);
            button2 = new Button("Hide full menu window");
            add(button2);
            button2.addActionListener(this);

            fullmenuWindow = new menuFrame("Full menus");
            fullmenuWindow.setSize(100, 100);
			
		}	// koniec funkcji init
	
        public void paint (Graphics g) {
		
		rysuj(g);
		rysujWlasne(g,Color.RED);

		przycisk1.setLocation(380,350);
		przycisk2.setLocation(380,373);
		oknoTekstowe.setLocation(450,361);
	}

		public void rysuj(Graphics g){
			// Rysuje odcinek o poczatku i koncu w podanych wspolrzednych
			g.drawLine(100, 10, 10, 150);
			g.drawLine(10, 150, 150, 150);
			g.drawLine(150, 150, 100, 10);
							
						
			// zmienia kolor linii
			g.setColor(Color.blue);
					
			// rysuje kwadrat lub prostokat
			g.drawRect(10,210,120,120); 
			g.drawRect(20,220,100,100);
			g.drawRect(30,230,80,80);
				
							
					 
					
			// rysuje kwadrat lub prostokat o zaokraglonych bokach
			g.setColor(Color.red);
			g.drawRoundRect(100, 300, 70, 70, 30, 30);
					
					
			// rysuje elipse wpisana w podany prostokat
			g.setColor(Color.MAGENTA);
			g.drawOval(250, 50, 80, 40);
				
							
			// Pobiera obrazek z pliku i wyswietla go w podanym prostokacie 
			Image image = getImage(getCodeBase(), "welcome.jpg");  
			g.drawImage(image, 210, 210, 200, 100, this);
					
					
			// Ustawia kroj i parametry czcionki w applecie
			String s = new String ("Hello World !!!");
			Font appFont = new Font("Times New Roman", Font.PLAIN, 22);  
					
			g.setColor(Color.blue);
			g.setFont(appFont); 
			g.drawString(s,250, 300);		
		}
		
		public void rysujWlasne(Graphics g, Color c){
			
			//prostokat w wybranym kolorze
			g.setColor(c);
			g.drawRect(400, 100, 200, 100);
			
			//zaokraglone boki
			g.drawRoundRect(450, 40, 50, 300, 20, 20);
			
			//elipsa
			g.drawOval(650, 40, 100, 350);
			
			//napis
			String napis = new String("Moj napis");
			Font font = new Font("Arial", Font.BOLD, 28);
			g.setFont(font);
			g.drawString(napis, 800, 300);  
			
		}
		
		public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == przycisk1)
			oknoTekstowe.setText("Welcome");
		
		if (evt.getSource() == przycisk2)
			oknoTekstowe.setText(oknoTekstowe.getText()+" to JAVA !!!");
		
		//MENUFRAME
        if(evt.getSource() == button1){
            fullmenuWindow.setVisible(true);
	    }
	    if(evt.getSource() == button2){
	            fullmenuWindow.setVisible(false);
	    }
		
	}

		public void itemStateChanged(ItemEvent e) {
			///BUTTONS
	        if(e.getItemSelectable() == checkbox1){
	                text1.setText("Check box 1 clicked!");
	        }
	
	        if(e.getItemSelectable() == checkbox2){
	                text1.setText("Check box 2 clicked!");
	        }
	
	        if(e.getItemSelectable() == checkbox3){
	                text1.setText("Check box 3 clicked!");
	        }
	
	        if(e.getItemSelectable() == checkbox4){
	                text1.setText("Check box 4 clicked!");
	        }
	
	        if(e.getItemSelectable() == checkbox5){
	                text1.setText("Check box 5 clicked!");
	        }
	        
	        ///RADIOBUTTONS
            if(e.getItemSelectable() == checkbox6){
                	text2.setText("Radio button 1 clicked!");
	        }
	        if(e.getItemSelectable() == checkbox7){
	                text2.setText("Radio button 2 clicked!");
	        }
	        if(e.getItemSelectable() == checkbox8){
	                text2.setText("Radio button 3 clicked!");
	        }
	        
	        ///CHOICE
            if(e.getItemSelectable() == choice1){
                text3.setText(((Choice)e.getItemSelectable()).getSelectedItem());
            }
		}

}
