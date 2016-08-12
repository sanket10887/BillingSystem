package com.billingsystem.component;

import javax.swing.JButton;
import javax.swing.JPanel;

public class jbutton extends JButton
{
	JButton jc;
    public jbutton(JPanel p,String s,int x,int y,int w,int h)
    {
        	jc=this;
			//jc.setLabel(s);
            jc.setText(s);
			jc.setBounds(x,y,w,h);
			jc.setOpaque(false);
            p.add(jc);
    }
} 