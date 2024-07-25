package com.ty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ty.entity.CitizenPlan;

public interface CitizenPlanRepo extends JpaRepository<CitizenPlan, Integer> {
   // To get the unique data in drop down we r going with custom query
	@Query("select distinct (planName) from CitizenPlan") // To get unique plan avlbl in table
	public List<String> getPlansName();
	
	// To get unique status for drop down  => This HQL query
	@Query("select distinct (planStatus) from CitizenPlan") // To get unique plan avlbl in table
	public List<String> getPlanStatus();
	
}
