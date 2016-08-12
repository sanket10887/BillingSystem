package com.billingsystem.component;

import java.awt.Point;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ls extends JScrollPane
{
	int x,y,w;
	public ls(JPanel pm,JPanel p,JList jl,int xx,int yy,int ww,int hh)
	{
                super(jl,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                Point pxy=p.getLocation();
                this.x=pxy.x+xx;
                this.y=pxy.y+yy+hh-1;
                this.w=ww;
                this.setBounds(x,y,w,100);
		pm.add(this);
	}
        public void h(int hh)
        {
            this.setBounds(x,y,w,hh);
        }
}
                            