package com.billingsystem.component;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class text extends JTextField
{
	JTextField jtx;
    public text(JPanel p,int x,int y,int w,int h)
    {
        	jtx=this;
			jtx.setColumns(10);
            jtx.setBounds(x,y,w,h);
            p.add(jtx);
    }
} 