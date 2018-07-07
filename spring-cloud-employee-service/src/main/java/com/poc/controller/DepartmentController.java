package com.poc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.poc.model.Department;

@RestController
public class DepartmentController {
	
	@GetMapping("/departments")
	public List<Department> findAll(@RequestHeader("userId") String userId) {
		System.out.println("userId================="+userId);
		List<Department> departments = new ArrayList<>();
		Department dep = new Department();
		dep.setId(1L);
		dep.setName("IT");
		
		departments.add(dep);
		
		return departments;
		
	}
	
	@GetMapping("/departments/departmentId/{departmentId}")
	public ResponseEntity<Department> findById(@PathVariable String departmentId, @RequestHeader("userId") String userId) {
		System.out.println("userId================="+userId);
		Department dep = new Department();
		dep.setId(Long.valueOf(departmentId));
		dep.setName("SALES");
		return ResponseEntity.ok().body(dep);
	}
	
}
