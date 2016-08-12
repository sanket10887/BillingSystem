package com.billingsystem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;

import com.billingsystem.dto.ItemDTO;
import com.billingsystem.report.BillReport;

public class BillInformationForm extends BaseGUI 
{
	int key;
	static JInternalFrame frame;
	String customerID;
	Map<String,String> customerMap;
	List<ItemDTO> itemList;
    public BillInformationForm(String title,int x,int y,int width,int height)
	{
    	super("bill");
		setTitle(title);
		setLayout(null);
    //            setFrameIcon((new ImageIcon(getClass().getResource("image/main user.png"))));
		
		textList.get("vatPer").setText("0");
		textList.get("addiTaxPer").setText("0");
		
		
		btnList.get("addToCart").addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("add click");
				if((textList.get("rate").getText()!=null && !textList.get("rate").getText().equals("") && textList.get("quantity").getText()!=null && !textList.get("quantity").getText().equals("")))
				{
					ItemDTO itemDTO=new ItemDTO();
					itemDTO.setItemName(textList.get("itemName").getText());
					itemDTO.setRate(textList.get("rate").getText());
					itemDTO.setQuantity(textList.get("quantity").getText());
					itemDTO.setWeight(textList.get("weight").getText());
					Double total=Double.parseDouble(textList.get("rate").getText())*Integer.parseInt(textList.get("quantity").getText());
					itemDTO.setTotal(total+"");
					System.out.println(itemDTO.getItemName());
					itemTabel.insertData(itemDTO);
					textList.get("total").setText(itemTabel.getTotal()+"");
					textList.get("itemName").setText("");
					textList.get("rate").setText("");
					textList.get("quantity").setText("");
					textList.get("weight").setText("");
				}
			}
		});
		
		btnList.get("deleteToCart").addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				itemTabel.removeData();
				textList.get("total").setText(itemTabel.getTotal()+"");
			}
		});
		
		
		btnList.get("ok").addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				generatePayableTotal();
				
				
			}
		});
		btnList.get("printBill").addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				generatePayableTotal();
				customerMap=getControlValues();
				
				//current date with specific format
				String pattern = "dd/MM/yyyy";
			    SimpleDateFormat format = new SimpleDateFormat(pattern);
			    customerMap.put("date", format.format(new Date()));
			    
				itemList=itemTabel.getData();
				System.out.println(customerMap);
				new BillReport(customerMap,itemList);
			}
		});
		setContentPane(pnlMain);
		setSize(width,height);
		setLocation(x,y);
		
		
	}
	public void generatePayableTotal()
	{
		Double total=Double.parseDouble(textList.get("total").getText());
		Double vatPer=0.0,addiTaxPer=0.0;
		if(!textList.get("vatPer").getText().equals(""))
			vatPer=Double.parseDouble(textList.get("vatPer").getText());
		if(!textList.get("addiTaxPer").getText().equals(""))
			addiTaxPer=Double.parseDouble(textList.get("addiTaxPer").getText());
		Double vatVal=total*vatPer/100;
		Double addiTaxVal=total*addiTaxPer/100;
		Double totalVat=vatVal+addiTaxVal;
		Double totalAmount=total+totalVat;
		textList.get("vatAmount").setText(vatVal+"");
		textList.get("addiTaxAmount").setText(addiTaxVal+"");
		textList.get("totalAmount").setText(totalAmount+"");
	}
      
   }