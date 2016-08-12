package com.billingsystem.component;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXDatePicker;

public class datepicker extends JXDatePicker
{
	JXDatePicker dp;
    public datepicker(JPanel p,int x,int y,int w,int h)
    {
        	dp=this;
			//jc.setLabel(s);
            dp.setBounds(x,y,w,h);
			dp.setOpaque(false);
            p.add(dp);
    }
} 