package com.billingsystem.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.billingsystem.component.ItemTable;
import com.billingsystem.component.combobox;
import com.billingsystem.component.datepicker;
import com.billingsystem.component.jbutton;
import com.billingsystem.component.label;
import com.billingsystem.component.text;
import com.billingsystem.component.textarea;
import com.billingsystem.dto.XMLAttributeDTO;
import com.billingsystem.xml.ParseXML;

public class BaseGUI extends JInternalFrame {
	JPanel pnlMain;
	List<Object> list;
	HashMap<String,JComboBox> cmbList=new HashMap<String,JComboBox>();
	HashMap<String,JTextField> textList=new HashMap<String,JTextField>();
	HashMap<String,JTextArea> textAreaList=new HashMap<String,JTextArea>();
	HashMap<String,JXDatePicker> dateList=new HashMap<String,JXDatePicker>();
	HashMap<String,JButton> btnList=new HashMap<String,JButton>();
	ItemTable itemTabel;
	public BaseGUI()
	{
		super("", true, true, true, true);
	}
	public BaseGUI(String formName)
	{
		super("", true, true, true, true);
		ParseXML parseXml=new ParseXML();
		list=parseXml.readEditXML("xml/", formName+".xml");
		pnlMain=new JPanel();
		
		pnlMain=new JPanel();
		pnlMain.setBounds(10,10,900,630);
		pnlMain.setLayout(null);
		List<XMLAttributeDTO> groupList=(List<XMLAttributeDTO>)list.get(6);
		List<XMLAttributeDTO> groupColumnList=(List<XMLAttributeDTO>)list.get(7);
		for(XMLAttributeDTO xmlAttributeDTO:groupList)
		{
			JPanel jpanel=new JPanel();
			if(xmlAttributeDTO.getLabel()!=null)
				jpanel.setBorder(BorderFactory.createTitledBorder(xmlAttributeDTO.getLabel()));
			jpanel.setBounds(Integer.parseInt(xmlAttributeDTO.getXpos()),Integer.parseInt(xmlAttributeDTO.getYpos()), Integer.parseInt(xmlAttributeDTO.getWidth()), Integer.parseInt(xmlAttributeDTO.getHeight()));
			jpanel.setLayout(null);
			jpanel.setOpaque(false);
			pnlMain.add(jpanel);
			for(XMLAttributeDTO column:groupColumnList)
			{
				if(column.getParentID()!=null && xmlAttributeDTO.getID().equalsIgnoreCase(column.getParentID()))
				{
					System.out.println(column.getName());
					if(column.getDtype()!=null && column.getDtype().equalsIgnoreCase("label"))
					{
						JLabel lbl=new label(jpanel,column.getLabel(),Integer.parseInt(column.getXpos()),Integer.parseInt(column.getYpos()), Integer.parseInt(column.getWidth()), Integer.parseInt(column.getHeight()));
					
					}
					else if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && (column.getDtype()==null || column.getDtype().equalsIgnoreCase("text")))
					{
						JTextField textfield=new text(jpanel,Integer.parseInt(column.getXpos()),Integer.parseInt(column.getYpos()), Integer.parseInt(column.getWidth()), Integer.parseInt(column.getHeight()));
						textList.put(column.getName(),textfield);
					}
					else if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && column.getDtype().equalsIgnoreCase("password"))
					{
						
					}
					else if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && column.getDtype().equalsIgnoreCase("radio"))
					{
						
					}
					else if(column.getDtype()!=null && column.getDtype().equalsIgnoreCase("textarea"))
					{
						JTextArea textArea=new textarea(jpanel,Integer.parseInt(column.getXpos()),Integer.parseInt(column.getYpos()), Integer.parseInt(column.getWidth()), Integer.parseInt(column.getHeight()));
						textAreaList.put(column.getName(),textArea);
					}
					else if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && column.getDtype().equalsIgnoreCase("date"))
					{
						JXDatePicker datePicker=new datepicker(jpanel,Integer.parseInt(column.getXpos()),Integer.parseInt(column.getYpos()), Integer.parseInt(column.getWidth()), Integer.parseInt(column.getHeight()));
						dateList.put(column.getName(),datePicker);
					}
					else if((column.getStype()!=null  && column.getStype().equalsIgnoreCase("boolean")) ||(column.getDtype()!=null &&  column.getDtype().equalsIgnoreCase("checkbox")))
					{
						
					}
					else if(column.getDtype()!=null && column.getDtype().equalsIgnoreCase("list"))
					{
						JComboBox cmb=new combobox(jpanel,Integer.parseInt(column.getXpos()),Integer.parseInt(column.getYpos()), Integer.parseInt(column.getWidth()), Integer.parseInt(column.getHeight()));
						cmbList.put(column.getName(),cmb);
					}
					else if(column.getDtype()!=null && column.getDtype().equalsIgnoreCase("listgrid"))
					{
						itemTabel=new ItemTable(Integer.parseInt(column.getXpos()),Integer.parseInt(column.getYpos()), Integer.parseInt(column.getWidth()), Integer.parseInt(column.getHeight()));
						jpanel.add(itemTabel);
					}
					else if(column.getDtype()!=null && column.getDtype().equalsIgnoreCase("button"))
					{
						JButton btn=new jbutton(jpanel,column.getName(),Integer.parseInt(column.getXpos()),Integer.parseInt(column.getYpos()), Integer.parseInt(column.getWidth()), Integer.parseInt(column.getHeight()));
						btnList.put(column.getName(),btn);
					}
				}
			}
		}
	}
	
	public Map<String,String> getControlValues()
	{
		Map<String,String> map=new HashMap<String, String>();
		List<XMLAttributeDTO> groupList=(List<XMLAttributeDTO>)list.get(6);
		List<XMLAttributeDTO> groupColumnList=(List<XMLAttributeDTO>)list.get(7);
		for(XMLAttributeDTO xmlAttributeDTO:groupList)
		{
			for(XMLAttributeDTO column:groupColumnList)
			{
				if(column.getParentID()!=null && xmlAttributeDTO.getID().equalsIgnoreCase(column.getParentID()))
				{
					System.out.println(column.getName());
					if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && (column.getDtype()==null || column.getDtype().equalsIgnoreCase("text")))
					{
						map.put(column.getName(),textList.get(column.getName()).getText());
					}
					else if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && column.getDtype().equalsIgnoreCase("password"))
					{
						
					}
					else if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && column.getDtype().equalsIgnoreCase("radio"))
					{
						
					}
					else if(column.getDtype()!=null && column.getDtype().equalsIgnoreCase("textarea"))
					{
						map.put(column.getName(),textAreaList.get(column.getName()).getText());
						System.out.println(map);
					}
					else if((column.getStype()!=null && column.getStype().equalsIgnoreCase("char")) && column.getDtype().equalsIgnoreCase("date"))
					{
						map.put(column.getName(),dateList.get(column.getName()).getDate().toString());
					}
					else if((column.getStype()!=null  && column.getStype().equalsIgnoreCase("boolean")) ||(column.getDtype()!=null &&  column.getDtype().equalsIgnoreCase("checkbox")))
					{
						
					}
					else if(column.getDtype()!=null && column.getDtype().equalsIgnoreCase("list"))
					{
						//map.put(column.getName(),cmbList.get(column.getName()).getSelectedItem().toString());
					}
				}
			}
		}
		return map;
	}
}
