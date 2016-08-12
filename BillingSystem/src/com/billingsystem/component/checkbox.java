package com.billingsystem.component;

import javax.swing.JPanel;
import javax.swing.JCheckBox;

/* checkbox class */
public class checkbox extends JCheckBox
{
	JCheckBox jc;
    public checkbox(JPanel p,String s,int x,int y,int w,int h)
    {
        	jc=this;
			//jc.setLabel(s);
            jc.setText(s);
			jc.setBounds(x,y,w,h);
			jc.setOpaque(false);
            p.add(jc);
    }
} 