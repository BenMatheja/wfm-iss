package org.camunda.bpm.iss.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.ejb.BillIssService;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.EmployeeService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Project;

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
	public static final String RESULT = "abcde.pdf";
	
	/** The ISS Logo. */
	public static final String LOGO = "Logo.jpg";

	private static Logger LOGGER = Logger.getLogger(InformAccounting.class
			.getName());

	@Inject
	CustomerService customerService;
	
	@Inject
	BillIssService billService;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	EmployeeService employeeService;
	
	// Soem vars
	Customer customerEntity;
	Project projectEntity;
	
	public void createPdf(String filename, DelegateExecution delegateExecution) throws DocumentException,
			IOException {
		// prepare data
		// Customer
		  customerEntity = customerService.getCustomer((Long) delegateExecution.getVariable("customerId"));
		  String customerName = customerEntity.getName();
		  String customerAddress = customerEntity.getAddress();
		  
		// Invoice Number
		  Long invoiceNoLo = (customerEntity.getId() + projectEntity.getId());
		  String invoiceNo = invoiceNoLo.toString();
		  
		// Invoice Date
		  Calendar calendar = Calendar.getInstance();			 
		  java.util.Date now = calendar.getTime();
		  Date date = now;
		  
		// Project Title
		  String projectTitle = projectEntity.getTitle();
		  
		// step 1
		Document document = new Document(PageSize.A4);
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// step 3
		document.open();
		// step 4
		List<String> days = new ArrayList<String>();
		days.add("1");
		days.add("2");

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

		Paragraph cusName = new Paragraph("Customer Name", new Font(
				FontFamily.HELVETICA, 12));
		cusName.setAlignment(Element.ALIGN_LEFT);
		Paragraph cusAddress = new Paragraph("Address", new Font(
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
		
		document.add(getFirstTable());
		
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		
		document.add(getSecondTable());
				
		// step 5
		document.close();
	}

	public PdfPTable getFirstTable() throws DocumentException, IOException {
		// Create a table with 5 columns
		PdfPTable table = new PdfPTable(new float[] { 2, 3, 1, 2, 3 });
		table.setWidthPercentage(100f);
		// table.getDefaultCell().setUseAscender(true);
		// table.getDefaultCell().setUseDescender(true);
		// Add the first header row
		Font f = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		f.setColor(BaseColor.BLACK);
		
		
		// Add the second header row twice
		table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		table.getDefaultCell().setBorderColor(BaseColor.WHITE);
		

		table.addCell(new Phrase("Invoice No.: ", f));
		table.addCell("Generated Serial No.");
		table.addCell("  ");
		table.addCell(new Phrase("Invoice Date: ", f));		
		table.addCell("today");
		
		table.addCell(new Phrase("Project Title: ", f));
		table.addCell("<Project Title>");
		table.addCell("  ");
		table.addCell(new Phrase("Employee: ", f));
		table.addCell("<user name>");

		// There are three special rows
		//table.setHeaderRows(1);
		// One of them is a footer
		// table.setFooterRows(1);
		// Now let's loop over the screenings
		// List<Screening> screenings = PojoFactory.getScreenings(day);
		// Movie movie;
		// for (Screening screening : screenings) {
		// movie = screening.getMovie();

		// table.addCell(String.format("%1$tH:%1$tM", screening.getTime()));
		// table.addCell(String.format("%d '", movie.getDuration()));
		// table.addCell(movie.getMovieTitle());
		// table.addCell(String.valueOf(movie.getYear()));
		// cell = new PdfPCell();
		// cell.setUseAscender(true);
		// cell.setUseDescender(true);
		// cell.addElement(PojoToElementFactory.getDirectorList(movie));
		// table.addCell(cell);
		// cell = new PdfPCell();
		// cell.setUseAscender(true);
		// cell.setUseDescender(true);
		// cell.addElement(PojoToElementFactory.getCountryList(movie));
		// table.addCell(cell);
		// }
		return table;
	}
	
	public PdfPTable getSecondTable() throws DocumentException, IOException {
		// Create a table with 5 columns
		PdfPTable table = new PdfPTable(new float[] { 4, 5, 2, 4});
		table.setWidthPercentage(100f);
		
		Font f = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		f.setColor(BaseColor.BLACK);
		
		table.getDefaultCell().setBorderWidth(0.2F);;
						
//		table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
//		table.getDefaultCell().setLeft(PdfPCell.NO_BORDER);
//		table.getDefaultCell().setBorderColorRight(BaseColor.RED);

		table.addCell(new Phrase("Consultant", f));
		table.addCell(new Phrase("Daily Rate", f));
		table.addCell(new Phrase("Billed Days", f));
		table.addCell(new Phrase("Total", f));		
			
		
		//table.setHeaderRows(1);
		
		
		for (int i = 0; i < 5; i++) {
			table.addCell("employeeName");
			table.addCell("hourlyRate");
			table.addCell("# of Days");
			table.addCell("hourly Rate * no of days");
		}
		
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		
		table.addCell(" ");
		table.addCell(" ");
		table.addCell(" ");
		table.addCell(" ");
		
		table.addCell("");
		
		table.addCell(new Phrase("+ Design Price: ", f));
		table.addCell("");
		table.addCell("<designPrice>");
		table.addCell("");
		table.addCell(new Phrase("Total Sum (VAT excl.)",f));
		table.addCell("");
		table.addCell("<Sum>");
		table.addCell("");
		table.addCell(new Phrase ("19.00% VAT", f));
		table.addCell("");
		table.addCell("<Sum*0.19>");
		table.addCell("");
		table.addCell(new Phrase ("Total Sum (VAT incl.)",f));
		table.addCell("");
		table.addCell("<Sum*1.19>");

		return table;
	}

	
	public void main(DelegateExecution delegateExecution) throws DocumentException, IOException {
		createPdf(RESULT, delegateExecution);
	}
}
