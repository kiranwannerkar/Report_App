package com.ty.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ty.entity.CitizenPlan;
import com.ty.repository.CitizenPlanRepo;
import com.ty.request.SearchRequest;
import com.ty.util.EmailUtils;
import com.ty.util.ExcelGenerator;
import com.ty.util.PdfGenerator;

@Service // Service talk to repository so we have to use autowired
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepo planRepo; // 3 way we can autowired currently we r doing field level
	
	@Autowired
	private ExcelGenerator  excelGenerator;
	
	@Autowired 
	private PdfGenerator pdfGenerator;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public List<String> getPlanNames() {
		return planRepo.getPlansName();
	}

	@Override
	public List<String> getPlanStatuses() {
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		CitizenPlan entity = new CitizenPlan();
//		BeanUtils.copyProperties(request, entity); //Here copy data from binding obj to entity... here data should not be empty trace while debugging
		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}

		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			entity.setPlanStatus(request.getPlanStatus());
		}

		if (null != request.getGender() && !"".equals(request.getGender())) {
			entity.setGender(request.getGender());
		}

		if (null != request.getPlanStartDate() && !"".equals(request.getPlanStartDate())) {
			String planStartDate = request.getPlanStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(planStartDate, formatter);

			entity.setPlanStartDate(date);

		}
		if (null != request.getPlanEndDate() && !"".equals(request.getPlanEndDate() )) {
			 String planEndDate = request.getPlanEndDate() ;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(planEndDate, formatter);

			entity.setPlanEndDate(date);;

		}

		// source is request obj and target entity

		return planRepo.findAll(Example.of(entity)); // with this we can able add the filter dynamically
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception { // I am trying to follow single responsibility function that means one class should
		                                                                        //have only one action
		List<CitizenPlan> findAll = planRepo.findAll();
		  excelGenerator.generate(response,findAll);
		  
		  String subject = "Test mail subject";
		  String body = "<h1>Test mail body</h1>";
		  String to = "kiranwannerkar@gmail.com";
		  
		  File f = new File("Plans.xls"); // "Plan.xls" -> it is file name .....here we r creating file to which file i want to write the data 
		  
		  emailUtils.sendEmail(subject, body, to,f);
		  
		  f.delete(); //it mean from my system file should be deleted  it should go to attachment downloded in browser then it should delete from system
		  return true;
		
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		File f = new File("Plans.pdf");
		List<CitizenPlan> plans = planRepo.findAll(); 
		pdfGenerator.generator(response, plans,f);
		
		String subject = "Test mail subject";
		String body = "<h1>Test mail body</h1>";
		String to = "kiranwannerkar@gmail.com";
		
		emailUtils.sendEmail(subject, body, to,f);
		  
		f.delete(); //it mean from my system file should be deleted  it shouls go to attachment downloded in browser then it shpul delete from server
		
		return true;
	}

}
