package com.billingsystem.component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class textarea extends JTextArea
{
	JTextArea jt;
	JScrollPane jsp;
    public textarea(JPanel p,int x,int y,int w,int h)
    {
        	jt=this;
			jsp=new JScrollPane(this);
			jsp.setBounds(x,y,w,h);
			p.add(jsp);
                        jt.setTabSize(0);
          
    }
} 