package com.billingsystem.report;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.billingsystem.dto.ItemDTO;

public class BillReport {

	public File pdfFile=null;
	public BillReport(Map<String,String> param,List<ItemDTO> al)
	{
//		HashMap<String,String> param = new HashMap<String, String>();
//		param.put("buyerName", "Jatin Garala");
//		param.put("buyerAddress", "Limbuda");
//		param.put("buyerPhone", "9428775544");
//		param.put("invoiceNo", "7");
//		param.put("challanNo", "787");
//		param.put("date", "12/15/2012");
//		param.put("dispatchThrough", "Rixa");
//		param.put("destination", "rajkot");
//		param.put("total1", "100966");
//		param.put("disPercentage", "5%");
//		param.put("disAmount", "4545");
//		param.put("total2", "103066");
//		param.put("vat", "Output Vat @ 4 %");
//		param.put("addlVat", "Output Addl. Vat @ 1 %");
//		param.put("roundOff", "Less: Rount Off");
//		param.put("vatAmount", "564");
//		param.put("addlVatAmount", "166");
//		param.put("roundOffAmount", "0.7765");
//		param.put("totalAmount", "667686");
//		param.put("totalInText", "Rupees Six Thousand Four Hundred Forty Five Only");
//		param.put("vat2", "4%");
//		param.put("addlVat2", "1%");
//		param.put("assValue", "6754");
//		param.put("assValueAddlVat", "6454");
//		param.put("vatAmount2", "245.45");
//		param.put("vatAmountAddl", "3535");
//		param.put("totalVat", "10066");
//		param.put("totVatText", "Rupees Three Hundred Six and Ninety  Paisa Only");
//		param.put("comVatTin", "100435325366");
//		param.put("buyerVatTin", "9675676");
//		
//		ArrayList<ReportTestingDTO> al=new ArrayList<ReportTestingDTO>();
//    	ReportTestingDTO Ib1=new ReportTestingDTO("1","item1","articleType1","snrDetail1","100");
//    	ReportTestingDTO Ib2=new ReportTestingDTO("2","item2","articleType2","snrDetail2","200");
//    	ReportTestingDTO Ib3=new ReportTestingDTO("3","item3","articleType3","snrDetail3","300");
//    	ReportTestingDTO Ib4=new ReportTestingDTO("4","item4","articleType4","snrDetail4","400");
//    	ReportTestingDTO Ib5=new ReportTestingDTO("5","item4","articleType4","snrDetail4","500");
//    	ReportTestingDTO Ib6=new ReportTestingDTO("6","item4","articleType4","snrDetail4","600");
//    	ReportTestingDTO Ib7=new ReportTestingDTO("7","item4","articleType4","snrDetail4","700");
//    	ReportTestingDTO Ib8=new ReportTestingDTO("8","item4","articleType4","snrDetail4","800");
//    	ReportTestingDTO Ib9=new ReportTestingDTO("9","item4","articleType4","snrDetail4","900");
//    	al.add(Ib1);
//    	al.add(Ib2);
//    	al.add(Ib3);
//    	al.add(Ib4);
//    	al.add(Ib5);
//    	al.add(Ib6);
//    	al.add(Ib7);
//    	al.add(Ib8);
//    	al.add(Ib9);
    	runReport(param, al, "report/billreport.jasper");
	}
	public void runReport(Map<String,String> param,List<ItemDTO> al,String reportFileStr) {
	    try{
	    	
				File reportFile = new File(reportFileStr);
		        JasperPrint jasperPrint1 = JasperFillManager.fillReport(reportFile.getPath(), param, new JRBeanCollectionDataSource(al));
		        pdfFile = new File(reportFile.getParent(),"bill"+param.get("invoiceNo")+"("+param.get("date").replace("/", "-")+").pdf");
				pdfFile.createNewFile();
				JasperExportManager.exportReportToPdfFile(jasperPrint1, pdfFile.getPath());
				printPDF();
				
			
	    }
	    catch(Exception ex) {
	           String connectMsg = "Could not create the report " + ex.getMessage() + " " + ex.getLocalizedMessage();
	           System.out.println(connectMsg);
	    }
	}
	
	public void printPDF()
	{
		if (Desktop.isDesktopSupported()) {
            try {
               
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
		
		/*PdfReader reader = null;
	    
	     try {
	         
	        // Specify file that needs to be printed  
	        reader = PdfReader.fileReader("sample_doc.pdf");
	      
	        // Create a PDF document object with the reader
	        PdfDocument d = new PdfDocument(reader);
	      
	        // Create a PDF printer object
	        PdfPrinter printer = new PdfPrinter();

	        // Specify the document that needs to be printed
	        printer.setDocument(d);        

	        // Select a printer
	        printer.setSelectedPrinterName(
	           // Name of first printer 
	           printer.getAvailablePrinterNames()[0]);
	                  
	        // Set margins
	        printer.setPageMargins(
	           // Left, top, right, bottom margins
	           new double[] {1, 0.5, 1, 0.5}, 
	           // Measurement units
	           PdfPrinter.MU_INCHES);                

	        // Specify page size
	        printer.setPageSize(PdfPageSize.A4);
	        // Specify page orientation
	        printer.setOrientation(PdfPrinter.Orientation_LANDSCAPE);
	        // Specify pages that need to be printed
	        printer.setPageRange("1-8");
	        // Specify number of copies
	        printer.setCopies(3);
	        // Specify scaling
	        printer.setPageScale(PdfPrinter.SCALE_REDUCE_TO_PRINTER_MARGINS);
	        // Specify how page of different copies need to be collated
	        printer.setPrintSheetCollate(SheetCollate.COLLATED);
	        // Specify paper bin
	        printer.setPrintMediaTray(MediaTray.SIDE);
	        // Specify printing order
	        printer.setReverse(true);
	        // Specify which sides of paper need to be printed on
	        printer.setPrintSides(Sides.TWO_SIDED_SHORT_EDGE);
	        
	        // Show printer dialog to user
	        printer.showPrintDialog();        
	     }
	     catch (Exception ex1) {        
	        System.out.println(ex1.getMessage());        
	     } finally {
	        if (reader != null) {
	           try {
	              // Release I/O resources
	              reader.dispose();   
	           } catch (Exception ex2) {
	              System.out.println(ex2.getMessage());
	           }
	        }
	     }
	  }*/
	}
}
