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
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.EmployeeService;
import org.camunda.bpm.iss.ejb.ProjectService;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Stateless
@Named
public class GenerateBill{

	/** The resulting PDF file. */
	public static final String RESULT = "abcde.pdf";

	private static Logger LOGGER = Logger.getLogger(InformAccounting.class
			.getName());

	@Inject
	CustomerService customerService;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	EmployeeService employeeService;
	
	public void createPdf(String filename) throws DocumentException,
			IOException {
		// create a database connection
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
		Font f = new Font();
		f.setColor(BaseColor.WHITE);
		
		// Add the second header row twice
		table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

		table.addCell("Invoice No.: ");
		table.addCell("Generated Serial No.");
		table.addCell("  ");
		table.addCell("Invoice Date");
		table.addCell("today");
		
		table.addCell("Project Title: ");
		table.addCell("<Project Title>");
		table.addCell("  ");
		table.addCell("Employee");
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
		PdfPTable table = new PdfPTable(new float[] { 2, 3, 1, 2});
		table.setWidthPercentage(100f);
		
		Font f = new Font();
		f.setColor(BaseColor.WHITE);
		
		// Add the second header row twice
		table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

		table.addCell("Consultant");
		table.addCell("Daily Rate");
		table.addCell("Billed Days");
		table.addCell("Total");				
		
//		for (e: <employeelistsize>) {
//			
//		}

		return table;
	}

	
	public void main(DelegateExecution delegateExecution) throws DocumentException, IOException {
		createPdf(RESULT);
	}
}
