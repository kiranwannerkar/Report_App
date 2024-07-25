package com.ty.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ty.entity.CitizenPlan;
import com.ty.request.SearchRequest;
import com.ty.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;
	
	@GetMapping("/pdf")
	public void pfExport(HttpServletResponse response) throws Exception {//HttpResponse as a parameter : - bcz  I want to send the excel file to the browser in response object that’s why taking response obj as paramter
		response.setContentType("application/pdf");  // in order to download the file we need to set the what is content type and header for that
//		                                                         for excel type we r using "application/octet-stream" and.....for PDF :- "application/pdf"
		response.addHeader("Content-Disposition", "attachment;filename=plans.pdf");  // header will specify in which format the content will come 
		                                             // "file=plans.xls;attachment" :- it mean when u click on excle then excle file will be downloaded in browser 
		service.exportPdf(response);
		
	}
	
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response) throws Exception {//HttpResponse as a parameter : - bcz  I want to send the excel file to the browser in response object that’s why taking response obj as paramter
		response.setContentType("application/octet-stream");  // in order to download the file we need to set the what is content type and header for that
//		                                                         for excel type we r using "application/octet-stream" and.....for PDF :- "application/pdf"
		response.addHeader("Content-Disposition", "attachment;filename=plans.xls");  // header will specify in which format the content will come 
		                                             // "file=plans.xls;attachment" :- it mean when u click on excle then excle file will be downloaded in browser 
		service.exportExcel(response);
		
	}

	@PostMapping("/search")  // SearchReq contain form data
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		
//		System.out.println(request);   ----we r keeping searchrequest object in model attribute
//		when sending the post req my search req obj contain data
		 
		List<CitizenPlan> plans = service.search(search); // from controller i want to send data to UI ..here we retrive 12 records
		model.addAttribute("plans",plans);
		model.addAttribute("search",search); // here i am sending binding object also
		init(model);
		return "index";

	}

	@GetMapping("/") // "/" this mean empty req
	public String indexPage(Model model) { // puprose of model is to send the data to UI
		model.addAttribute("search", new SearchRequest()); //when first method loaded then only i want to set the empty object
//		SearchRequest searchObj = new SearchRequest();
		// I want to send binding obj to UI
		
		// "search" -> this key and searchObj -> this is value

		init(model);  // this init method called in same index page 
		return "index";

		// Note :- wht is use of sending search req to UI? => In want to bind this
		// object to Ui page I want to bind the Form to Search req obj
		
		
//		 ==> Purpose of this method is load the empty page then my form is empty initially bcz i am sending empty binding obj
		// obj is empty so my form load the empty page
	}

	private void init(Model model) {
//		model.addAttribute("search", new SearchRequest()); when search object created in class then whatever variable r there they r initialize with null value
//		bcz of that page is loaded with select option
		
		
		model.addAttribute("names", service.getPlanNames());
		model.addAttribute("statuses", service.getPlanStatuses());
	}
}
