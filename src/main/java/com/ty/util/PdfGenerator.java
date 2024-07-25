package com.ty.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ty.entity.CitizenPlan;
import com.ty.repository.CitizenPlanRepo;

@Component
public class PdfGenerator {
	
	@Autowired
	private CitizenPlanRepo planRepo;
	
	public void generator(HttpServletResponse response,List<CitizenPlan> plans,File f) throws Exception {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream()); // here whtever docs get creating i want attach to response object and send to the browser
		PdfWriter.getInstance(document, new FileOutputStream(f)); // FileOutputStream used to ownload file into my system
		
		document.open();// to write the data document should be open
		
		// Creating font
				// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		Paragraph p = new Paragraph("Citizen Plans Info", fontTiltle);
		
		
		// Aligning the paragraph in document
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);  // whtevr para created i am adding to the document
		
		PdfPTable table = new PdfPTable(6);
		table.setSpacingBefore(5);
		
		
		
		table.addCell("ID");
		table.addCell("Citizen Name");
		table.addCell("Plan Name");
		table.addCell("Plan Status");
		table.addCell("Start Date");
		table.addCell("End Date");   // this heaer data we adding 6 cell
		
		// logic to get the data from database table
		
		for (CitizenPlan plan : plans) {  // here no need of index if 6 cell completed automatically it will go to next row
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());
			table.addCell(plan.getPlanStartDate()+"");
			table.addCell(plan.getPlanEndDate()+"");
			 // in loop we r setting the record value in cell from database
		}
		
		document.add(table); // we r adding data from table to the document
		document.close();
	}
	

}
