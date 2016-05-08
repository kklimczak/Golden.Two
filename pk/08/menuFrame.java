import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

class menuFrame extends Frame implements ActionListener {

        Menu Menu1, SubMenu1;                                                  
        MenuBar Menubar1;                                                      
        TextField text1;                                                 
        MenuItem menuitem1, menuitem2, menuitem4;                              
        CheckboxMenuItem menuitem3;                                            
                                                                                                                                      
        menuFrame(String title){                                               
                super(title);                                                  
                text1 = new TextField("Full menu");                      
                setLayout(new GridLayout(1, 1));                               
                add(text1);                                              
                Menubar1 = new MenuBar();                                      
                Menu1 = new Menu("File");                                      
                                                                                                                                      
                menuitem1 = new MenuItem("Item 1");                            
                menuitem1.addActionListener(this);                             
                Menu1.add(menuitem1);                                          
                                                                                                                                      
                menuitem2 = new MenuItem("Item 2");                            
                menuitem2.addActionListener(this);                             
                Menu1.add(menuitem2);                                          
                                                                                                                                      
                Menu1.addSeparator();                                          
                                                                                                                                      
                menuitem3 = new CheckboxMenuItem("Check Item");                
                menuitem3.addActionListener(this);                             
                Menu1.add(menuitem3);                                          
                                                                                                                                      
                Menu1.addSeparator();                                          
                                                                                                                                      
                SubMenu1 = new Menu("Sub menus");                              
                SubMenu1.add(new MenuItem("Hello"));                           
                SubMenu1.add(new MenuItem("from"));                            
                SubMenu1.add(new MenuItem("Java"));                            
                                                                                                                                      
                Menu1.add(SubMenu1);                                           
                Menubar1.add(Menu1);                                           
                setMenuBar(Menubar1);                                          
                                                                                                                                      
                Menu1.addSeparator();                                          
                                                                                                                                      
                menuitem4 = new MenuItem("Exit");                              
                menuitem4.addActionListener(this);                             
                Menu1.add(menuitem4);                                          
        }                                                                      
                                                                                                                                      
        public void actionPerformed(ActionEvent event){                        
            if(event.getSource() == menuitem1){                                
                  text1.setText("Item 1");                               
            }                                                                  
            if(event.getSource() == menuitem2){                                
                  menuitem2.setEnabled(false);                                 
                  text1.setText("Item 2");                               
            }                                                                  
            if(event.getSource() == menuitem3){                                
                  ((CheckboxMenuItem)event.getSource()).setState(true);        
            }                                                                  
            if(event.getSource() == menuitem4){                                
                  setVisible(false);                                                      
            }                                                                  
                                                                               
        }  
}