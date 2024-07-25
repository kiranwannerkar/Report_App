package com.ty.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ty.entity.CitizenPlan;
import com.ty.request.SearchRequest;

public interface ReportService {

	public List<String> getPlanNames();

	public List<String> getPlanStatuses();

// important part of this method i want to get the data from DB based on my search criteria what search criteria come from UI that criteria
//	criteria I will take in object format with that criteria I will find some records in DB and those data I am returning as List of records

	public List<CitizenPlan> search(SearchRequest request);
	

//	If report send successful to email then true then i will give message that u r email send successfully
// 	if it is true that mean it sent successfully if false then i will display error msg
	public boolean exportExcel(HttpServletResponse response) throws Exception;  // exception propogating from impl class
	
//	  
	public boolean exportPdf(HttpServletResponse response) throws Exception;// to send the file to the browser i am taking response object as parameter


}
