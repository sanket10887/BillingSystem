package com.billingsystem.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class MainGUI extends JFrame implements KeyListener {

    JMenuBar menuBar;
    JMenu user;
    JDesktopPane desktop;
    JInternalFrame toolPalette;
    static JMenuItem cd_entry;

    static final Integer DOCLAYER = new Integer(5);
    
    public static MainGUI lib=new MainGUI();
    
    public MainGUI() 
    {
        super("System");
        //setIconImage((new ImageIcon(getClass().getResource("image/Library.png"))).getImage());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds ( 0, 0, screenSize.width, screenSize.height );
        buildContent();
        buildMenus();
        this.addWindowListener(new WindowAdapter() {
	                       public void windowClosing(WindowEvent e) {
				   quit();
		}});
        UIManager.addPropertyChangeListener(new UISwitchListener((JComponent)getRootPane()));
		
        try{
        	this.getComponent(JInternalFrame.WHEN_IN_FOCUSED_WINDOW ).addKeyListener(this);
		}catch(Exception e){}
	    }

	    public void keyPressed(KeyEvent ke)
	    {
	    	if(ke.getModifiers()==KeyEvent.ALT_MASK)
	    	{
	    		System.out.println("hcxhhdsha");
	    	}
	    	
	    }
	    public void keyReleased(KeyEvent ke)
	    {}
	    public void keyTyped(KeyEvent ke)
	    {}
    
    
    
    
    protected void buildMenus() 
    {
	    menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		buildFileMenu();
	    user.setMnemonic('s');
		menuBar.add(user);
		setJMenuBar(menuBar);	
    }

    protected void buildFileMenu() 
    {
    	user = new JMenu("Bill System");
       
		JMenuItem stud = new JMenuItem("Bill Information");
        stud.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
    
        JMenuItem quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));

        stud.addActionListener(new ActionListener() {
	                       public void actionPerformed(ActionEvent e) {
	                    	   getInternalForm();
		}});

        quit.addActionListener(new ActionListener() {
	                       public void actionPerformed(ActionEvent e) {
				   quit();
		}});
        user.add(stud);
        user.addSeparator();
       
        user.add(quit);
	
        
    }
    
    
    protected void buildContent() {
        desktop = new JDesktopPane();
        getContentPane().add(desktop);
    }

    public void quit() {
        System.exit(0);
    }

    public void getInternalForm() 
    {
               JInternalFrame frame = new BillInformationForm("Billing information",5,5,900,670);
               desktop.add(frame, DOCLAYER);
			try { 
					frame.setVisible(true);
					frame.setSelected(true); 
                        
                        } catch (java.beans.PropertyVetoException e2) {
                        	e2.printStackTrace();
                        }
                    
                
                                    
    }
	
   
	
	
	public static void main(String s[])
	{
		//BillReport billReport=new BillReport();
		JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        

        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        }  
        catch ( Exception e ) 
        {
            System.exit(0);
        }

		JFrame f=new MainGUI();
		f.setVisible(true);
	}
	
}

