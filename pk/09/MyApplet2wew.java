
import java.applet.Applet;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyApplet2wew extends Applet {

	CheckboxGroup cbg1;
    Checkbox cb1, cb2, cb3, cb4;
    Checkbox cb11, cb12;
    Choice color, font;
    Color fontColor;

	String fontName;
	protected int fontSize;
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
    	
    	cb1.addItemListener(new ExtAdaptClass());
    	cb2.addItemListener(new ExtAdaptClass());
    	cb3.addItemListener(new ExtAdaptClass());
    	cb4.addItemListener(new ExtAdaptClass());
    	
    	add(cb1);
    	add(cb2);
    	add(cb3);
    	add(cb4);
    	
    	// font style buttons
    	
    	cb11 = new Checkbox("Bold");
    	cb12 = new Checkbox("Italic");
    	
    	cb11.addItemListener(new MyHandler());
    	cb12.addItemListener(new MyHandler());
    	
    	add(cb11);
    	add(cb12);
    	
    	// font color
    	color = new Choice();
    	color.add("BLACK");
    	color.add("RED");
    	color.add("GREEN");
    	color.addItemListener(new MyHandler());
    	add(color);
    	
    	// font
    	font = new Choice();
    	font.add("Times New Roman");
    	font.add("Arial");
    	font.addItemListener(new MyHandler());
    	add(font);
    	
    }
    
    public class MyHandler implements ItemListener{
    	
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
    
    public class ExtAdaptClass extends AdaptClass {

    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		if(e.getItemSelectable() == cb1){
    			fontSize = 10;
    			repaint();
    		}
    		
    		if(e.getItemSelectable() == cb2){
    			fontSize = 20;
    			repaint();
    		}
    		
    		if(e.getItemSelectable() == cb3){
    			fontSize = 30;
    			repaint();
    		}
    		
    		if(e.getItemSelectable() == cb4){
    			fontSize = 40;
    			repaint();
    		}
    	}
    }

}
