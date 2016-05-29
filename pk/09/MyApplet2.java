
import java.applet.Applet;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyApplet2 extends Applet implements ItemListener  {

	CheckboxGroup cbg1;
    Checkbox cb1, cb2, cb3, cb4;
    Checkbox cb11, cb12;
    Choice color, font;
    Color fontColor;

	String fontName;
	int fontSize;
	boolean isItalic = false,
			isBold = false;
	
    public void paint(Graphics g){
    	
    	String text = new String("Habba babba");
    	Font font; 
    	
    	if (isItalic && isBold){
        	font = new Font(fontName, Font.ITALIC | Font.BOLD, fontSize);
    	}
    	else {
    		if(isItalic){
    			font = new Font(fontName, Font.ITALIC, fontSize);
    		}
    		else if(isBold){
	    			 font = new Font(fontName, Font.BOLD, fontSize);
	    		 }
    			 else {
    				 font = new Font(fontName, Font.PLAIN, fontSize);
    			 }
    			
    	}
    	
    	g.setFont(font);
    	g.setColor(fontColor);
    	g.drawString(text, 250, 100);
    	
    }
    
    public void init(){
    	
    	setSize(700, 200);
    	fontName = "Times New Roman";
    	fontSize = 10;
    	fontColor = Color.BLACK;
    	
    	//font size buttons
    	
    	cbg1 = new CheckboxGroup();    	
    	cb1 = new Checkbox("10pt", cbg1, false);
    	cb2 = new Checkbox("20pt", cbg1, false);
    	cb3 = new Checkbox("30pt", cbg1, false);
    	cb4 = new Checkbox("40pt", cbg1, false);
    	
    	cb1.addItemListener(new ItemListener(){
    		public void itemStateChanged(ItemEvent e){
    			fontSize = 10;
    			repaint();
    		}
    	});
    	cb2.addItemListener(new ItemListener(){
    		public void itemStateChanged(ItemEvent e){
				fontSize = 20;
				repaint();
		}
    	});
    	cb3.addItemListener(new ItemListener(){
    		public void itemStateChanged(ItemEvent e){
				fontSize = 30;
				repaint();
		}
    	});
    	cb4.addItemListener(new ItemListener(){
    		public void itemStateChanged(ItemEvent e){
				fontSize = 40;
				repaint();
		}
    	});
    	
    	add(cb1);
    	add(cb2);
    	add(cb3);
    	add(cb4);
    	
    	// font style buttons
    	
    	cb11 = new Checkbox("Bold");
    	cb12 = new Checkbox("Italic");
    	
    	cb11.addItemListener(this);
    	cb12.addItemListener(this);
    	
    	add(cb11);
    	add(cb12);
    	
    	// font color
    	color = new Choice();
    	color.add("BLACK");
    	color.add("RED");
    	color.add("GREEN");
    	color.addItemListener(this);
    	add(color);
    	
    	// font
    	font = new Choice();
    	font.add("Times New Roman");
    	font.add("Arial");
    	font.addItemListener(this);
    	add(font);
    	
    }
    
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getItemSelectable() == cb11){
			isBold = !isBold;
		}
		
		if(e.getItemSelectable() == cb12){
			isItalic = !isItalic;
		}
		
		if(e.getItemSelectable() == color){
			if(((Choice)e.getItemSelectable()).getSelectedItem() == "BLACK"){
				fontColor = Color.BLACK;
			}
			
			if(((Choice)e.getItemSelectable()).getSelectedItem() == "RED"){
				fontColor = Color.RED;
			}
			
			if(((Choice)e.getItemSelectable()).getSelectedItem() == "GREEN"){
				fontColor = Color.GREEN;
			}
		}
		
		if(e.getItemSelectable() == font){
			fontName = ((Choice)e.getItemSelectable()).getSelectedItem();
		}
		
		repaint();
		
	}
	
	
}
