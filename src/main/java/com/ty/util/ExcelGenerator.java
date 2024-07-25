package com.ty.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ty.entity.CitizenPlan;
import com.ty.repository.CitizenPlanRepo;

@Component
public class ExcelGenerator {
	
	@Autowired
	private CitizenPlanRepo planRepo;
	
	public void generate(HttpServletResponse response,List<CitizenPlan> records) throws Exception {
	
//	Workbook workbook = new XSSFWorkbook();  here extension u have to write is .xlsx
	Workbook workbook = new HSSFWorkbook();  // WorkBook is interface and HSSFWorkbook it is implementation class of WorkBook ....now my documnet created
	Sheet sheet = workbook.createSheet("palns_data");
	Row headerRow = sheet.createRow(0);
	
	headerRow.createCell(0).setCellValue("ID");
	headerRow.createCell(1).setCellValue("Citizen Name");
	headerRow.createCell(2).setCellValue("Plan Name");
	headerRow.createCell(3).setCellValue("Plan Status");
	headerRow.createCell(4).setCellValue("Plan Start Date");
	headerRow.createCell(5).setCellValue("Plan End Date");
	headerRow.createCell(6).setCellValue("Benefit Amt");
	
//	I am going to take loop bcz list of records are available
	
	int dataRowIndex = 1;  // for record coming in the loop row index should be incremented
	for (CitizenPlan plan : records) {
		Row dataRow=sheet.createRow(dataRowIndex); // index start with 1 bcz already header row start from 0 index
		dataRow.createCell(0).setCellValue(plan.getCitizenId());
		dataRow.createCell(1).setCellValue(plan.getCitizenName());
		dataRow.createCell(2).setCellValue(plan.getPlanName());
		dataRow.createCell(3).setCellValue(plan.getPlanStatus());
		
		if(null!=plan.getPlanStartDate()) {
			dataRow.createCell(4).setCellValue(plan.getPlanStartDate()+"");
		} else {
			dataRow.createCell(4).setCellValue("N/A");
		}
		
		if(null!= plan.getPlanEndDate()) {
		dataRow.createCell(5).setCellValue(plan.getPlanEndDate()+"");
		} else {
			dataRow.createCell(5).setCellValue("N/A");
		}
		
		if(null!= plan.getBenefitAmt()) {
		dataRow.createCell(6).setCellValue(plan.getBenefitAmt());
		} else {
			dataRow.createCell(6).setCellValue("N/A");
		}
		
		dataRowIndex++;
	}
	// now i want to write this data to the file ....!!!!! Instead of giving File output stream we r going for response output stream
	
	/*
	 * FileOutputStream fos = new FileOutputStream(new File("plan.xls"));
	 * workbook.write(fos); // here we writing workbook data into the file by using  file output stream
	 * workbook.close(); // finally we closing the workbook
	 */		
	FileOutputStream fos = new FileOutputStream(new File("Plans.xls"));
	workbook.write(fos); // whtevr data avlbl in workbook write in file fos also
	fos.close();
	
	// ********whatever file is created in system that file have to attached to the email   
	
	// !!!! I am trying to write excel file in fos anf servlet output stream
	
	// ============ Difference between FileOutputStream and ServletOutputStream 
	//=============FileOutputStream - it Create file in current folder 
     // ServletOutputStream - it will send to the browser
	
	ServletOutputStream outputStream = response.getOutputStream();
	workbook.write(outputStream);   // here we r sendind the response to the browser
	workbook.close(); 
//	return true;
	
}


}
