package com.billingsystem.component;

import javax.swing.JPanel;
import javax.swing.JComboBox;

public class combobox extends JComboBox
{
	JComboBox jc;
    public combobox(JPanel p,int x,int y,int w,int h)
    {
        	jc=this;
			jc.setBounds(x,y,w,h);
			p.add(jc);
    }
} 