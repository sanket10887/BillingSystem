package com.billingsystem.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.billingsystem.dto.ItemDTO;
public class ItemTable extends JPanel{

	JTable itemTable;
	DefaultTableModel model=null;
	String[]  columns={"ID","ItemName","Rate","Quantity","Weight","Total"};
	int count=1;
	
	Vector columnNames,visitdata;
	List<ItemDTO> itemList;
	public ItemTable(int x,int y,int width,int height)
	{
		itemList=new ArrayList<ItemDTO>();
		columnNames = new Vector();
        visitdata = new Vector();
            

            //  Get column names
            for (int i = 0; i < columns.length; i++){
                columnNames.addElement(columns[i]);
            }
 
          
       model = new DefaultTableModel(visitdata,columnNames); 
        //  Create table with data queried from the table "VisitTable"
       itemTable = new JTable(model);
       
       itemTable.setSize(width, height);
       JScrollPane scrollPane = new JScrollPane( itemTable );
       scrollPane.setBounds(0,0, width,height);
	   setLayout(null);
	   setBounds(x,y, width,height);
	   add(scrollPane);
	}
	public void insertData(ItemDTO itemDTO)
	{
		 model.insertRow(itemTable.getRowCount(),new Object[]  {count++,itemDTO.getItemName(),itemDTO.getRate(),itemDTO.getQuantity(),itemDTO.getWeight(),itemDTO.getTotal()});
	}
	public void removeData()
	{
		model.removeRow(itemTable.getSelectedRow());
	}
	public List<ItemDTO> getData()
	{
		List<ItemDTO> list=new ArrayList<ItemDTO>();
		Vector data=model.getDataVector();
		for(int i=0;i<model.getRowCount();i++)
		{
			list.add(new ItemDTO(((Vector)data.elementAt(i)).elementAt(0).toString(),((Vector)data.elementAt(i)).elementAt(1).toString(),((Vector)data.elementAt(i)).elementAt(2).toString(),((Vector)data.elementAt(i)).elementAt(3).toString(),((Vector)data.elementAt(i)).elementAt(4).toString(),((Vector)data.elementAt(i)).elementAt(5).toString()));
		}
		return list;
	}
	public Double getTotal()
	{
		Double total=0.0;
		Vector data=model.getDataVector();
		for(int i=0;i<model.getRowCount();i++)
		{
			total+=Double.parseDouble(((Vector)data.elementAt(i)).elementAt(5).toString());
		}
		return total;
	}
}
