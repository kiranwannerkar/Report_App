package com.ty.request;

import lombok.Data;

// Data  come form UI that variable we have to mention here 
@Data
public class SearchRequest {

	private String planName;
	private String planStatus;
	private String gender;
	private String planStartDate;
	private String planEndDate; 
}
