package com.billingsystem.xml;
 
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.billingsystem.dto.XMLAttributeDTO;

public class ParseXML {
	public String listStr;
	public ParseXML()
	{
		
	}
	
	public List<Object> readListGridXML(String xmlFilePath,String xmlFileName)
	{
		System.out.println("call readListGridXML()");
		Document doc;
		if(xmlFilePath!=null)
			doc=loadXml(xmlFilePath+xmlFileName);
		else
			doc=loadXmlAsString(xmlFileName);
		HashMap<String,String> titleMap=new HashMap<String,String>();
		HashMap<String,String> headerColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> columnList=new ArrayList<XMLAttributeDTO>();
		
		HashMap<String,String> headerPopupMenuColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> popupMenuColumnList=new ArrayList<XMLAttributeDTO>();
		
		HashMap<String,String> headerToolbarColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> toolbarColumnList=new ArrayList<XMLAttributeDTO>();
		
		if(doc!=null)
		{
			Element moduleEle = doc.getDocumentElement();
			Element resultPnlEle = (Element) moduleEle.getElementsByTagName("resultpanel").item(0);
			//read Title of Panel
			NamedNodeMap AttrMap=resultPnlEle.getAttributes();
			for(int i=0;i<AttrMap.getLength();i++)
			{
				titleMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
			}
			titleMap.put("xmlName", xmlFileName);
			// read header setting 
			Element columnsEle = (Element) resultPnlEle.getElementsByTagName("columns").item(0);
			AttrMap=columnsEle.getAttributes();
			String style="";
			for(int i=0;i<AttrMap.getLength();i++)
			{
				headerColumnMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				if(AttrMap.item(i).getNodeName().equalsIgnoreCase("fgcolor") && AttrMap.item(i).getNodeValue()!=null && !AttrMap.item(i).getNodeValue().equals(""))
					style+="color:#"+AttrMap.item(i).getNodeValue().trim()+";";
				if(AttrMap.item(i).getNodeName().equalsIgnoreCase("bgcolor") && AttrMap.item(i).getNodeValue()!=null && !AttrMap.item(i).getNodeValue().equals(""))
					style+="background-color:#"+AttrMap.item(i).getNodeValue().trim()+";";
				if(AttrMap.item(i).getNodeName().equalsIgnoreCase("font") && AttrMap.item(i).getNodeValue()!=null && !AttrMap.item(i).getNodeValue().equals(""))
					style+="font-family:"+AttrMap.item(i).getNodeValue().trim()+";";
				if(AttrMap.item(i).getNodeName().equalsIgnoreCase("fontsize") && AttrMap.item(i).getNodeValue()!=null && !AttrMap.item(i).getNodeValue().equals(""))
					style+="font-size:"+AttrMap.item(i).getNodeValue().trim()+";";
				if(AttrMap.item(i).getNodeName().equalsIgnoreCase("fontweight") && AttrMap.item(i).getNodeValue()!=null && !AttrMap.item(i).getNodeValue().equals(""))
					style+="font-weight:"+AttrMap.item(i).getNodeValue().trim()+";";
				if(AttrMap.item(i).getNodeName().equalsIgnoreCase("fontstyle") && AttrMap.item(i).getNodeValue()!=null && !AttrMap.item(i).getNodeValue().equals(""))
					style+="font-style:"+AttrMap.item(i).getNodeValue().trim()+";";
				
			}
			headerColumnMap.put("style",style);
	
			
			//read one by one all columns
			NodeList columnListEle = columnsEle.getElementsByTagName("column");
			if(columnListEle != null && columnListEle.getLength() > 0) {
				for(int i = 0 ; i < columnListEle.getLength();i++) {
					
					Element ele = (Element)columnListEle.item(i);
					
					XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerColumnMap);
					columnList.add(xmlAttributeDTO);
					
				}
			}
			
			// read header setting of PopupMenu
			if(resultPnlEle.getElementsByTagName("pmcolumns").item(0)!=null)
			{
				columnsEle = (Element) resultPnlEle.getElementsByTagName("pmcolumns").item(0);
				AttrMap=columnsEle.getAttributes();
				style="";
				for(int i=0;i<AttrMap.getLength();i++)
				{
					headerPopupMenuColumnMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				}
				
				//read one by one all columns
				columnListEle = columnsEle.getElementsByTagName("column");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {
	
						Element ele = (Element)columnListEle.item(i);
						XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerPopupMenuColumnMap);
						popupMenuColumnList.add(xmlAttributeDTO);
					}
				}
			}
			
			// read header setting of Toolbar
			if(resultPnlEle.getElementsByTagName("tcolumns").item(0)!=null)
			{
				columnsEle = (Element) resultPnlEle.getElementsByTagName("tcolumns").item(0);
				AttrMap=columnsEle.getAttributes();
				style="";
				for(int i=0;i<AttrMap.getLength();i++)
				{
					headerToolbarColumnMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				}
			
				//read one by one all columns
				columnListEle = columnsEle.getElementsByTagName("column");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {
	
						Element ele = (Element)columnListEle.item(i);
						XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerToolbarColumnMap);
						toolbarColumnList.add(xmlAttributeDTO);
					}
				}
			}
		}
		List<Object> listGridXML=new ArrayList<Object>();
		listGridXML.add(titleMap);
		listGridXML.add(headerColumnMap);
		listGridXML.add(columnList);
		listGridXML.add(headerPopupMenuColumnMap);
		listGridXML.add(popupMenuColumnList);
		listGridXML.add(headerToolbarColumnMap);
		listGridXML.add(toolbarColumnList);
		return listGridXML;
	}
	
	public List<Object> readEditXML(String xmlFilePath,String xmlFileName)
	{
		System.out.println("call readEditXML()");
		String groupID="";
		Document doc;
		if(xmlFilePath!=null)
			doc=loadXml(xmlFilePath+xmlFileName);
		else
			doc=loadXmlAsString(xmlFileName);
		HashMap<String,String> titleMap=new HashMap<String,String>();
		HashMap<String,String> headerColumnMap=new HashMap<String,String>();
		HashMap<String,String> groupHeaderColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> columnList=new ArrayList<XMLAttributeDTO>();
		List<XMLAttributeDTO> groupList=new ArrayList<XMLAttributeDTO>();
		
		List<XMLAttributeDTO> groupColumnList=new ArrayList<XMLAttributeDTO>();
		
		HashMap<String,String> headerToolbarColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> toolbarColumnList=new ArrayList<XMLAttributeDTO>();
		
		if(doc!=null)
		{
			Element moduleEle = doc.getDocumentElement();
			Element resultPnlEle = (Element) moduleEle.getElementsByTagName("editpanel").item(0);
			//read Title of Panel
			NamedNodeMap AttrMap=resultPnlEle.getAttributes();
			for(int i=0;i<AttrMap.getLength();i++)
			{
				titleMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				
			}
			titleMap.put("xmlName", xmlFileName);
			// read header setting 
			Element columnsEle = (Element) resultPnlEle.getElementsByTagName("ecolumns").item(0);
			AttrMap=columnsEle.getAttributes();
			String style="";
			for(int i=0;i<AttrMap.getLength();i++)
			{
				headerColumnMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());

				
			}
			headerColumnMap.put("style",style);
	
			NodeList columnListEle;
			if(columnsEle.getElementsByTagName("group").item(0)!=null){
				columnListEle = columnsEle.getElementsByTagName("group");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {

						Element ele = (Element)columnListEle.item(i);
//						style="";
//						for(int g=0;g<AttrMap.getLength();g++)
//						{
//							groupHeaderColumnMap.put(AttrMap.item(g).getNodeName(),AttrMap.item(g).getNodeValue());
//						}
						XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerColumnMap);
						xmlAttributeDTO.setID("group"+i);
						groupID=xmlAttributeDTO.getID();
						groupList.add(xmlAttributeDTO);
						NodeList childList = ele.getElementsByTagName("column");
						for(int i1 = 0 ; i1 < childList.getLength();i1++) {
							Element e = (Element)childList.item(i1);
							AttrMap=e.getAttributes();
							xmlAttributeDTO = getColumn(e,headerColumnMap);
							xmlAttributeDTO.setID(groupID+"c"+i1);
							xmlAttributeDTO.setParentID(groupID);
							groupColumnList.add(xmlAttributeDTO);
						}
					}
				}
			}
			if(columnsEle.getElementsByTagName("column").item(0)!=null){
				columnListEle = columnsEle.getElementsByTagName("column");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {

						Element ele = (Element)columnListEle.item(i);
						if(ele.getParentNode().toString().contains("group")==false ){
							XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerColumnMap);
							columnList.add(xmlAttributeDTO);
						}
					}
					
				}
			}
			// read header setting of Toolbar
			if(resultPnlEle.getElementsByTagName("tcolumns").item(0)!=null)
			{
				columnsEle = (Element) resultPnlEle.getElementsByTagName("tcolumns").item(0);
				AttrMap=columnsEle.getAttributes();
				style="";
				for(int i=0;i<AttrMap.getLength();i++)
				{
					headerToolbarColumnMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				}
			
				//read one by one all columns
				columnListEle = columnsEle.getElementsByTagName("column");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {
	
						Element ele = (Element)columnListEle.item(i);
						XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerToolbarColumnMap);
						toolbarColumnList.add(xmlAttributeDTO);
					}
				}
			}
	
		}
		
		List<Object> listGridXML=new ArrayList<Object>();
		listGridXML.add(titleMap);
		listGridXML.add(headerColumnMap);
		listGridXML.add(columnList);
		listGridXML.add(headerToolbarColumnMap);
		listGridXML.add(toolbarColumnList);
		listGridXML.add(groupHeaderColumnMap);
		listGridXML.add(groupList);
		listGridXML.add(groupColumnList);
		return listGridXML;
	}
	
	public List<Object> readAppsGroupXML(String xmlFilePath,String xmlFileName)
	{
		System.out.println("call appsGroupXML()");
		String groupID="";
		Document doc;
		if(xmlFilePath!=null)
			doc=loadXml(xmlFilePath+xmlFileName);
		else
			doc=loadXmlAsString(xmlFileName);
		HashMap<String,String> titleMap=new HashMap<String,String>();
		HashMap<String,String> headerColumnMap=new HashMap<String,String>();
		HashMap<String,String> groupHeaderColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> columnList=new ArrayList<XMLAttributeDTO>();
		List<XMLAttributeDTO> groupList=new ArrayList<XMLAttributeDTO>();
		
		List<XMLAttributeDTO> groupColumnList=new ArrayList<XMLAttributeDTO>();
		
		HashMap<String,String> headerToolbarColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> toolbarColumnList=new ArrayList<XMLAttributeDTO>();
		
		if(doc!=null)
		{
			Element moduleEle = doc.getDocumentElement();
			Element resultPnlEle = (Element) moduleEle.getElementsByTagName("apps").item(0);
			//read Title of Panel
			NamedNodeMap AttrMap=resultPnlEle.getAttributes();
			for(int i=0;i<AttrMap.getLength();i++)
			{
				titleMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				
			}
			titleMap.put("xmlName", xmlFileName);
	
			NodeList columnListEle;
			if(resultPnlEle.getElementsByTagName("group").item(0)!=null){
				columnListEle = resultPnlEle.getElementsByTagName("group");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {

						Element ele = (Element)columnListEle.item(i);
//						style="";
						for(int g=0;g<AttrMap.getLength();g++)
						{
							groupHeaderColumnMap.put(AttrMap.item(g).getNodeName(),AttrMap.item(g).getNodeValue());
						}
						XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerColumnMap);
						xmlAttributeDTO.setID("group"+i);
						groupID=xmlAttributeDTO.getID();
						groupList.add(xmlAttributeDTO);
						NodeList childList = ele.getElementsByTagName("app");
						for(int i1 = 0 ; i1 < childList.getLength();i1++) {
							Element e = (Element)childList.item(i1);
							AttrMap=e.getAttributes();
							xmlAttributeDTO = getColumn(e,headerColumnMap);
							xmlAttributeDTO.setID(groupID+"c"+i1);
							groupColumnList.add(xmlAttributeDTO);
						}
					}
				}
			}
			if(resultPnlEle.getElementsByTagName("app").item(0)!=null){
				columnListEle = resultPnlEle.getElementsByTagName("app");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {

						Element ele = (Element)columnListEle.item(i);
						if(ele.getParentNode().toString().contains("group")==false ){
							XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerColumnMap);
							columnList.add(xmlAttributeDTO);
						}
					}
				}
			}
		}
		
		List<Object> listGridXML=new ArrayList<Object>();
		listGridXML.add(titleMap);
		listGridXML.add(headerColumnMap);
		listGridXML.add(columnList);
		listGridXML.add(headerToolbarColumnMap);
		listGridXML.add(toolbarColumnList);
		listGridXML.add(groupHeaderColumnMap);
		listGridXML.add(groupList);
		listGridXML.add(groupColumnList);
		return listGridXML;
	}
	
	public List<Object> readFindXML(String xmlFilePath,String xmlFileName)
	{
		System.out.println("call readFindXML()");
		Document doc;
		if(xmlFilePath!=null)
			doc=loadXml(xmlFilePath+xmlFileName);
		else
			doc=loadXmlAsString(xmlFileName);
//		loadXml(xmlFilePath+xmlFileName);
		HashMap<String,String> titleMap=new HashMap<String,String>();
		HashMap<String,String> headerColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> columnList=new ArrayList<XMLAttributeDTO>();
		
		if(doc!=null)
		{
			Element moduleEle = doc.getDocumentElement();
			Element resultPnlEle = (Element) moduleEle.getElementsByTagName("findpanel").item(0);
			//read Title of Panel
			NamedNodeMap AttrMap=resultPnlEle.getAttributes();
			for(int i=0;i<AttrMap.getLength();i++)
			{
				titleMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				
			}
			titleMap.put("xmlName", xmlFileName);
			// read header setting 
			Element columnsEle = (Element) resultPnlEle.getElementsByTagName("fcolumns").item(0);
			AttrMap=columnsEle.getAttributes();
			String style="";
			for(int i=0;i<AttrMap.getLength();i++)
			{
				headerColumnMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				
			}
			headerColumnMap.put("style",style);
	
			
			//read one by one all columns
			NodeList columnListEle = columnsEle.getElementsByTagName("column");
			if(columnListEle != null && columnListEle.getLength() > 0) {
				for(int i = 0 ; i < columnListEle.getLength();i++) {

					Element ele = (Element)columnListEle.item(i);
					XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerColumnMap);
					columnList.add(xmlAttributeDTO);
				}
			}
		}
		
		List<Object> listGridXML=new ArrayList<Object>();
		listGridXML.add(titleMap);
		listGridXML.add(headerColumnMap);
		listGridXML.add(columnList);
	
		return listGridXML;
	}
	public List<Object> readProjectEditXML(String xmlFilePath,String xmlFileName)
	{
		System.out.println("call readProjectEditXML()");
		String pageID="";
		String groupID="";
		Document doc;
		if(xmlFilePath!=null)
			doc=loadXml(xmlFilePath+xmlFileName);
		else
			doc=loadXmlAsString(xmlFileName);
		HashMap<String,String> titleMap=new HashMap<String,String>();
		HashMap<String,String> headerPageMap=new HashMap<String,String>();
		List<XMLAttributeDTO> pageList=new ArrayList<XMLAttributeDTO>();
		
		List<XMLAttributeDTO> groupList=new ArrayList<XMLAttributeDTO>();
		
		List<XMLAttributeDTO> groupColumnList=new ArrayList<XMLAttributeDTO>();
		
		HashMap<String,String> headerToolbarColumnMap=new HashMap<String,String>();
		List<XMLAttributeDTO> toolbarColumnList=new ArrayList<XMLAttributeDTO>();
		if(doc!=null)
		{
			
			Element moduleEle = doc.getDocumentElement();
			Element resultPnlEle = (Element) moduleEle.getElementsByTagName("editpanel").item(0);
			//read Title of Panel
			NamedNodeMap AttrMap=resultPnlEle.getAttributes();
			for(int i=0;i<AttrMap.getLength();i++)
			{
				titleMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
			}
			titleMap.put("xmlName", xmlFileName);
			// read header setting 
			Element columnsEle = (Element) resultPnlEle.getElementsByTagName("pages").item(0);
			AttrMap=columnsEle.getAttributes();
			String style="";
			for(int i=0;i<AttrMap.getLength();i++)
			{
				headerPageMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				
			}
			headerPageMap.put("style",style);
	
			NodeList columnListEle = columnsEle.getElementsByTagName("page");
			
			if(columnListEle != null && columnListEle.getLength() > 0) {
				for(int i = 0 ; i < columnListEle.getLength();i++) {

					Element ele = (Element)columnListEle.item(i);
					
					XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerPageMap);
					xmlAttributeDTO.setID("p"+i);
					pageID=xmlAttributeDTO.getID();
					pageList.add(xmlAttributeDTO);
					NodeList childList = ele.getElementsByTagName("group");
					for(int i1 = 0 ; i1 < childList.getLength();i1++) {
						
						Element e = (Element)childList.item(i1);
						xmlAttributeDTO = getColumn(e,headerPageMap);
						xmlAttributeDTO.setID(pageID+"g"+i1);
						groupID=xmlAttributeDTO.getID();
						groupList.add(xmlAttributeDTO);
						NodeList childColList = e.getElementsByTagName("column");
						for(int j =0;j< childColList.getLength();j++){
							Element e1 = (Element)childColList.item(j);
							AttrMap=e1.getAttributes();
							xmlAttributeDTO = getColumn(e1,headerPageMap);
							xmlAttributeDTO.setID(groupID+"c"+j);
							groupColumnList.add(xmlAttributeDTO);
						}
					}
					
				}
				
			}
			// read header setting of Toolbar
			if(resultPnlEle.getElementsByTagName("tcolumns").item(0)!=null)
			{
				columnsEle = (Element) resultPnlEle.getElementsByTagName("tcolumns").item(0);
				AttrMap=columnsEle.getAttributes();
				style="";
				for(int i=0;i<AttrMap.getLength();i++)
				{
					headerToolbarColumnMap.put(AttrMap.item(i).getNodeName(),AttrMap.item(i).getNodeValue());
				}
			
				//read one by one all columns
				columnListEle = columnsEle.getElementsByTagName("column");
				if(columnListEle != null && columnListEle.getLength() > 0) {
					for(int i = 0 ; i < columnListEle.getLength();i++) {
	
						Element ele = (Element)columnListEle.item(i);
						XMLAttributeDTO xmlAttributeDTO = getColumn(ele,headerToolbarColumnMap);
						toolbarColumnList.add(xmlAttributeDTO);
					}
				}
			}
	
		}
		
		List<Object> listGridXML=new ArrayList<Object>();
		listGridXML.add(titleMap);
		listGridXML.add(headerPageMap);
		listGridXML.add(pageList);
		listGridXML.add(groupList);
		listGridXML.add(groupColumnList);
		listGridXML.add(headerToolbarColumnMap);
		listGridXML.add(toolbarColumnList);
		listStr="";
		return listGridXML;
	}
	public Document loadXml(String XMLpath)
	{
		Document doc;
		DocumentBuilder builder;

		try {
			builder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(XMLpath);
			return doc;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public Document loadXmlAsString(String XMLData)
	{
		Document doc;
		DocumentBuilder builder;

		try {
			InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(XMLData));
			builder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(is);
			return doc;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	public void isValidXML(InputStream is)throws Exception {
	
			DocumentBuilder builder;
//			InputSource is = new InputSource();
//			is.setCharacterStream(new StringReader(XMLData));
			builder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
				
	}
	
	public XMLAttributeDTO getColumn(Element ele,HashMap<String,String> headerColumnMap)
	{
		
		XMLAttributeDTO xmlAttributeDTO =new XMLAttributeDTO();
		
		if(ele.getAttribute("name")!=null && !ele.getAttribute("name").equals(""))
			xmlAttributeDTO.setName(ele.getAttribute("name"));
		
		if(ele.getAttribute("label")!=null && !ele.getAttribute("label").equals(""))
			xmlAttributeDTO.setLabel(ele.getAttribute("label"));

		if(ele.getAttribute("display")!=null && !ele.getAttribute("display").equals(""))
			xmlAttributeDTO.setDisplay(ele.getAttribute("display"));
		else if(headerColumnMap.get("display")!=null && !headerColumnMap.get("display").equals(""))
			xmlAttributeDTO.setDisplay(headerColumnMap.get("display"));
		
		if(ele.getAttribute("hidden")!=null && !ele.getAttribute("hidden").equals(""))
			xmlAttributeDTO.setHidden(ele.getAttribute("hidden"));
		else if(headerColumnMap.get("hidden")!=null && !headerColumnMap.get("hidden").equals(""))
			xmlAttributeDTO.setHidden(headerColumnMap.get("hidden"));

		if(ele.getAttribute("stype")!=null && !ele.getAttribute("stype").equals(""))
			xmlAttributeDTO.setStype(ele.getAttribute("stype"));
		else if(headerColumnMap.get("stype")!=null && !headerColumnMap.get("stype").equals(""))
			xmlAttributeDTO.setStype(headerColumnMap.get("stype"));
			
		if(ele.getAttribute("dtype")!=null && !ele.getAttribute("dtype").equals(""))
			xmlAttributeDTO.setDtype(ele.getAttribute("dtype"));
		else if(headerColumnMap.get("dtype")!=null && !headerColumnMap.get("dtype").equals(""))
			xmlAttributeDTO.setDtype(headerColumnMap.get("dtype"));
		
		if(ele.getAttribute("formatmask")!=null && !ele.getAttribute("formatmask").equals(""))
			xmlAttributeDTO.setFormatmask(ele.getAttribute("formatmask"));
		if(ele.getAttribute("pk")!=null && !ele.getAttribute("pk").equals(""))
			xmlAttributeDTO.setPk(ele.getAttribute("pk"));

		if(ele.getAttribute("align")!=null && !ele.getAttribute("align").equals(""))
			xmlAttributeDTO.setAlign(ele.getAttribute("align"));
		else if(headerColumnMap.get("align")!=null && !headerColumnMap.get("align").equals(""))
			xmlAttributeDTO.setAlign(headerColumnMap.get("align"));
		
		if(ele.getAttribute("autofilter")!=null && !ele.getAttribute("autofilter").equals(""))
			xmlAttributeDTO.setAutofilter(ele.getAttribute("autofilter"));
		
		xmlAttributeDTO.setStyle("");
		
		if(ele.getAttribute("fgcolor")!=null && !ele.getAttribute("fgcolor").equals(""))
			xmlAttributeDTO.setStyle(xmlAttributeDTO.getStyle()+"color:#"+ele.getAttribute("fgcolor").trim()+";");
		if(ele.getAttribute("bgcolor")!=null && !ele.getAttribute("bgcolor").equals("")){
			xmlAttributeDTO.setStyle(xmlAttributeDTO.getStyle()+"background-color:#"+ele.getAttribute("bgcolor").trim()+";");
			xmlAttributeDTO.setBgcolor(ele.getAttribute("bgcolor"));
		}
		if(ele.getAttribute("font")!=null && !ele.getAttribute("font").equals(""))
			xmlAttributeDTO.setStyle(xmlAttributeDTO.getStyle()+"font-family:"+ele.getAttribute("font")+";");
		if(ele.getAttribute("fontsize")!=null && !ele.getAttribute("fontsize").equals(""))
			xmlAttributeDTO.setStyle(xmlAttributeDTO.getStyle()+"font-size:"+ele.getAttribute("fontsize")+";");
		if(ele.getAttribute("fontweight")!=null && !ele.getAttribute("fontweight").equals(""))
			xmlAttributeDTO.setStyle(xmlAttributeDTO.getStyle()+"font-weight:"+ele.getAttribute("fontweight")+";");
		if(ele.getAttribute("fontstyle")!=null && !ele.getAttribute("fontstyle").equals(""))
			xmlAttributeDTO.setStyle(xmlAttributeDTO.getStyle()+"font-style:"+ele.getAttribute("fontstyle")+";");
		
		if(xmlAttributeDTO.getStyle().toString().equals(""))
			xmlAttributeDTO.setStyle(headerColumnMap.get("style"));

		if(ele.getAttribute("width")!=null && !ele.getAttribute("width").equals(""))
			xmlAttributeDTO.setWidth(ele.getAttribute("width"));
		else if(headerColumnMap.get("width")!=null && !headerColumnMap.get("width").equals(""))
			xmlAttributeDTO.setWidth(headerColumnMap.get("width"));
	
		if(ele.getAttribute("height")!=null && !ele.getAttribute("height").equals(""))
			xmlAttributeDTO.setHeight(ele.getAttribute("height"));
		else if(headerColumnMap.get("height")!=null && !headerColumnMap.get("height").equals(""))
			xmlAttributeDTO.setHeight(headerColumnMap.get("height"));

		if(ele.getAttribute("xpos")!=null && !ele.getAttribute("xpos").equals(""))
			xmlAttributeDTO.setXpos(ele.getAttribute("xpos"));
		if(ele.getAttribute("ypos")!=null && !ele.getAttribute("ypos").equals(""))
			xmlAttributeDTO.setYpos(ele.getAttribute("ypos"));
		
		if(ele.getAttribute("tabindex")!=null && !ele.getAttribute("tabindex").equals(""))
			xmlAttributeDTO.setTabindex(ele.getAttribute("tabindex"));
		if(ele.getAttribute("required")!=null && !ele.getAttribute("required").equals(""))
			xmlAttributeDTO.setRequired(ele.getAttribute("required"));
		else if(headerColumnMap.get("required")!=null && !headerColumnMap.get("required").equals(""))
			xmlAttributeDTO.setRequired(headerColumnMap.get("required"));

		if(ele.getAttribute("chrlen")!=null && !ele.getAttribute("chrlen").equals(""))
			xmlAttributeDTO.setChrlen(ele.getAttribute("chrlen"));
		else if(headerColumnMap.get("chrlen")!=null && !headerColumnMap.get("chrlen").equals(""))
			xmlAttributeDTO.setChrlen(headerColumnMap.get("chrlen"));

		if(ele.getAttribute("disable")!=null && !ele.getAttribute("disable").equals(""))
			xmlAttributeDTO.setDisable(ele.getAttribute("disable"));
		else if(headerColumnMap.get("disable")!=null && !headerColumnMap.get("disable").equals(""))
			xmlAttributeDTO.setDisable(headerColumnMap.get("disable"));

		if(ele.getAttribute("styleclass")!=null && !ele.getAttribute("styleclass").equals(""))
			xmlAttributeDTO.setStyleclass(ele.getAttribute("styleclass"));
		else if(headerColumnMap.get("styleclass")!=null && !headerColumnMap.get("styleclass").equals(""))
			xmlAttributeDTO.setStyleclass(headerColumnMap.get("styleclass"));
		
		if(ele.getAttribute("image")!=null && !ele.getAttribute("image").equals(""))
			xmlAttributeDTO.setImage(ele.getAttribute("image"));

		if(ele.getAttribute("tooltip")!=null && !ele.getAttribute("tooltip").equals(""))
			xmlAttributeDTO.setTooltip(ele.getAttribute("tooltip"));
		else if(headerColumnMap.get("tooltip")!=null && !headerColumnMap.get("tooltip").equals(""))
			xmlAttributeDTO.setTooltip(headerColumnMap.get("tooltip"));
		
		if(ele.getAttribute("defval")!=null && !ele.getAttribute("defval").equals(""))
			xmlAttributeDTO.setDefval(ele.getAttribute("defval"));

		if(ele.getAttribute("case")!=null && !ele.getAttribute("case").equals(""))
			xmlAttributeDTO.setTextcase(ele.getAttribute("case"));
		else if(headerColumnMap.get("case")!=null && !headerColumnMap.get("case").equals(""))
			xmlAttributeDTO.setTextcase(headerColumnMap.get("case"));

		if(ele.getAttribute("edit")!=null && !ele.getAttribute("edit").equals(""))
			xmlAttributeDTO.setEdit(ele.getAttribute("edit"));
		else if(headerColumnMap.get("edit")!=null && !headerColumnMap.get("edit").equals(""))
			xmlAttributeDTO.setEdit(headerColumnMap.get("edit"));

		if(ele.getAttribute("sep")!=null && !ele.getAttribute("sep").equals(""))
			xmlAttributeDTO.setSep(ele.getAttribute("sep"));
		else if(headerColumnMap.get("sep")!=null && !headerColumnMap.get("sep").equals(""))
			xmlAttributeDTO.setSep(headerColumnMap.get("sep"));

		if(ele.getAttribute("wraptext")!=null && !ele.getAttribute("wraptext").equals(""))
			xmlAttributeDTO.setWraptext(ele.getAttribute("wraptext"));
		else if(headerColumnMap.get("wraptext")!=null && !headerColumnMap.get("wraptext").equals(""))
			xmlAttributeDTO.setWraptext(headerColumnMap.get("wraptext"));

		if(ele.getAttribute("rblabel")!=null && !ele.getAttribute("rblabel").equals(""))
			xmlAttributeDTO.setRblabel(ele.getAttribute("rblabel"));
		if(ele.getAttribute("rbvalue")!=null && !ele.getAttribute("rbvalue").equals(""))
			xmlAttributeDTO.setRbvalue(ele.getAttribute("rbvalue"));
		if(ele.getAttribute("minvalue")!=null && !ele.getAttribute("minvalue").equals(""))
			xmlAttributeDTO.setMinvalue(ele.getAttribute("minvalue"));
		else if(headerColumnMap.get("minvalue")!=null && !headerColumnMap.get("minvalue").equals(""))
			xmlAttributeDTO.setMinvalue(headerColumnMap.get("minvalue"));

		if(ele.getAttribute("maxvalue")!=null && !ele.getAttribute("maxvalue").equals(""))
			xmlAttributeDTO.setMaxvalue(ele.getAttribute("maxvalue"));
		else if(headerColumnMap.get("maxvalue")!=null && !headerColumnMap.get("maxvalue").equals(""))
			xmlAttributeDTO.setMaxvalue(headerColumnMap.get("maxvalue"));

		if(ele.getAttribute("interval")!=null && !ele.getAttribute("interval").equals(""))
			xmlAttributeDTO.setInterval(ele.getAttribute("interval"));
		else if(headerColumnMap.get("interval")!=null && !headerColumnMap.get("interval").equals(""))
			xmlAttributeDTO.setInterval(headerColumnMap.get("interval"));

		if(ele.getAttribute("fontsize")!=null && !ele.getAttribute("fontsize").equals(""))
			xmlAttributeDTO.setFontSize(ele.getAttribute("fontsize"));
		else if(headerColumnMap.get("fontsize")!=null && !headerColumnMap.get("fontsize").equals(""))
			xmlAttributeDTO.setFontSize(headerColumnMap.get("fontsize"));

		if(ele.getAttribute("fontweight")!=null && !ele.getAttribute("fontweight").equals(""))
			xmlAttributeDTO.setFontWeight(ele.getAttribute("fontweight"));
		else if(headerColumnMap.get("fontweight")!=null && !headerColumnMap.get("fontweight").equals(""))
			xmlAttributeDTO.setFontWeight(headerColumnMap.get("fontweight"));

		if(ele.getAttribute("submenu")!=null && !ele.getAttribute("submenu").equals(""))
			xmlAttributeDTO.setSubmenu(ele.getAttribute("submenu"));
		
		if(ele.getAttribute("vertical")!=null && !ele.getAttribute("vertical").equals(""))
			xmlAttributeDTO.setVertical(ele.getAttribute("vertical"));
		
		if(ele.getAttribute("borderstyle")!=null && !ele.getAttribute("borderstyle").equals(""))
			xmlAttributeDTO.setBorderstyle(ele.getAttribute("borderstyle"));
		else if(headerColumnMap.get("borderstyle")!=null && !headerColumnMap.get("borderstyle").equals(""))
			xmlAttributeDTO.setBorderstyle(headerColumnMap.get("borderstyle"));
		
		if(ele.getAttribute("fontcolor")!=null && !ele.getAttribute("fontcolor").equals(""))
			xmlAttributeDTO.setFontcolor(ele.getAttribute("fontcolor"));
		else if(headerColumnMap.get("fontcolor")!=null && !headerColumnMap.get("fontcolor").equals(""))
			xmlAttributeDTO.setFontcolor(headerColumnMap.get("fontcolor"));
		
		if(ele.getAttribute("validation")!=null && !ele.getAttribute("validation").equals(""))
			xmlAttributeDTO.setValidation(ele.getAttribute("validation"));
		
		if(ele.getAttribute("condition")!=null && !ele.getAttribute("condition").equals(""))
			xmlAttributeDTO.setCondition(ele.getAttribute("condition"));
		
		if(ele.getAttribute("lname")!=null && !ele.getAttribute("lname").equals(""))
			xmlAttributeDTO.setLname(ele.getAttribute("lname"));
		
		return xmlAttributeDTO;
	}
	
	
	
	
}
