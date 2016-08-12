package com.billingsystem.component;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class label extends JLabel
{
	JLabel jl;
	String sname;
    public label(JPanel p,String sname,int x,int y,int w,int h)
    {
        	jl=this;
            this.sname=sname;
			jl.setText(this.sname);
            jl.setBounds(x,y,w,h);
                p.add(jl);
    }
} 