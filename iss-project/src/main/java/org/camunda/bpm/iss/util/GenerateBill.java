package org.camunda.bpm.iss.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.ejb.BillIssService;
import org.camunda.bpm.iss.entity.BillIss;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Stateless
@Named
public class GenerateBill{

	/** The resulting PDF file. */
	public static final String RESULT = "invoice.pdf";
	
	/** The ISS Logo. */
	public static final String LOGO = "Logo.jpg";

	private static Logger LOGGER = Logger.getLogger(InformAccounting.class
			.getName());

	
	@Inject
	BillIssService billService;	
	
	// Soem vars
	
	
	public void createPdf(String filename, DelegateExecution delegateExecution, BillIss bill_alternative) throws DocumentException,
			IOException {
		// prepare data
		BillIss bill;
		try {
		bill = billService.getBill((long) delegateExecution.getVariable("billIssId"));
		} catch (Exception e) {
			bill = bill_alternative; 
			LOGGER.info("Alternative was taken");
		}
		LOGGER.info("This is billIss Id from the proces Variables: " + (long) delegateExecution.getVariable("billIssId")); 
		LOGGER.info("This is billIss Id from the DB: " + bill.getId());  
		
		// step 1
		Document document = new Document(PageSize.A4);
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// step 3
		document.open();
		// step 4		

		document.newPage();
		document.add(new Chunk(""));

		Image img = Image.getInstance(LOGO);
		img.setAbsolutePosition(40, 740);
		img.scalePercent(25);
		document.add(img);

		Paragraph adressLine = new Paragraph("                                                    Internet Security Services  |  1600 Amphitheatre Parkway  |  Mountain View, CA 94043", new Font(
				FontFamily.HELVETICA, 9));
		document.add(adressLine);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph cusName = new Paragraph(bill.getCustomerName(), new Font(
				FontFamily.HELVETICA, 12));
		cusName.setAlignment(Element.ALIGN_LEFT);
		Paragraph cusAddress = new Paragraph(bill.getCustomerAddress(), new Font(
				FontFamily.HELVETICA, 12));
		cusName.setAlignment(Element.ALIGN_LEFT);
		
		document.add(cusName);		
		document.add(cusAddress);
		document.add(Chunk.NEWLINE);
		
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		
		Paragraph invoice = new Paragraph("Invoice", new Font(
				FontFamily.HELVETICA, 18));
		invoice.setAlignment(Element.ALIGN_LEFT);
		document.add(invoice);
		document.add(Chunk.NEWLINE);
		
		document.add(getFirstTable(bill));
		
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		
		document.add(getSecondTable(bill));
		
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		
		document.add(getThirdTable(bill));
				
		// step 5
		document.close();
	}

	public PdfPTable getFirstTable(BillIss bill) throws DocumentException, IOException {
		// Create a table with 5 columns
		PdfPTable table = new PdfPTable(new float[] { 2, 3, 1, 2, 3 });
		table.setWidthPercentage(100f);
		// table.getDefaultCell().setUseAscender(true);
		// table.getDefaultCell().setUseDescender(true);
		// Add the first header row
		Font f = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		f.setColor(BaseColor.BLACK);
		
		table.getDefaultCell().setBorderWidth(0.2F);
		
		table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		table.getDefaultCell().setBorderColor(BaseColor.WHITE);
						
				
		table.addCell(new Phrase("Invoice No.: ", f));
		table.addCell(String.valueOf(bill.getId()));
		table.addCell("  ");
		table.addCell(new Phrase("Invoice Date: ", f));		
		table.addCell(String.valueOf(bill.getDate()));
		
		table.addCell(new Phrase("Project Title: ", f));
		table.addCell(bill.getProjectTitle());
		table.addCell("  ");
		table.addCell(new Phrase("Employee: ", f));
		table.addCell(" ");
		
		table.addCell(new Phrase("Contract Title: ", f));
		table.addCell(String.valueOf(bill.getContractTitle()));
		table.addCell("  ");
		table.addCell(new Phrase("Cost Estimation: ", f));		
		table.addCell(String.valueOf(bill.getCostEstimation()));
		
		table.addCell(new Phrase("Project Start: ", f));
		table.addCell(String.valueOf(bill.getProjectStart()));
		table.addCell("  ");
		table.addCell(new Phrase("Project End: ", f));		
		table.addCell(String.valueOf(bill.getProjectEnd()));
				
		
		return table;
	}
	
	public PdfPTable getSecondTable(BillIss bill) throws DocumentException, IOException {
		// Create a table with 5 columns
		PdfPTable table = new PdfPTable(new float[] { 1 });
		table.setWidthPercentage(30f);
		
		Font f = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		f.setColor(BaseColor.BLACK);
		
		table.getDefaultCell().setBorderWidth(0.2F);
		
		table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		table.getDefaultCell().setBorderColor(BaseColor.WHITE);
						
				
		table.addCell(new Phrase("Project Employees: ", f));
		
		for (int i = 0; i < 5; i++) {
			table.addCell("employeeName");			
		}		
		return table;
	}
	
	public PdfPTable getThirdTable(BillIss bill) throws DocumentException, IOException {
		// Create a table with 5 columns
		PdfPTable table = new PdfPTable(new float[] { 1, 1, 1 });
		table.setWidthPercentage(100f);
		
		Font f = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		f.setColor(BaseColor.BLACK);
		
		table.getDefaultCell().setBorderWidth(0.2F);
						
//		table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
//		table.getDefaultCell().setLeft(PdfPCell.NO_BORDER);
//		table.getDefaultCell().setBorderColorRight(BaseColor.RED);

		table.addCell(new Phrase("Item", f));
		table.addCell(new Phrase("Accumulated Hours", f));
		table.addCell(new Phrase("Total", f));		
			
		
		//table.setHeaderRows(1);		
		
		table.addCell("ISS Security Solution");
		table.addCell(String.valueOf(bill.getAccumulatedHours()));
		table.addCell(String.valueOf(bill.getIssTotal()));
		table.addCell("PB Design ");
		table.addCell(" ");
		table.addCell(String.valueOf(bill.getPbTotal()));
		
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		
		table.addCell(" ");
		table.addCell(" ");
		table.addCell(" ");
		table.addCell(" ");
				
				
		table.addCell(new Phrase("Total Sum (VAT excl.)",f));
		table.addCell("");
		table.addCell(String.valueOf(bill.getPriceInCent()/100));
		table.addCell("");
		table.addCell(new Phrase ("19.00% VAT", f));
		table.addCell("");
		table.addCell(String.valueOf((bill.getPriceInCent()/100)*0.19));
		table.addCell("");
		table.addCell(new Phrase ("Total Sum (VAT incl.)",f));
		table.addCell("");
		table.addCell(String.valueOf((bill.getPriceInCent()/100)*1.19));

		return table;
	}

	
	public void main(DelegateExecution delegateExecution, BillIss bill) throws DocumentException, IOException {
		createPdf(RESULT, delegateExecution, bill);
	}
}
