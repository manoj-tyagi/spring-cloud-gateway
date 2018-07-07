package com.poc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.poc.model.Employee;

@RestController
public class EmployeeController {
	
	@GetMapping("/employees")
	public List<Employee> findAll() {
		List<Employee> employees = new ArrayList<>();
		Employee emp = new Employee();
		emp.setId(1L);
		emp.setName("Manoj");
		emp.setAge(25);
		
		employees.add(emp);
		
		return employees;
		
	}
	
	@GetMapping("/employees/employeeId/{employeeId}")
	public ResponseEntity<Employee> findById(@PathVariable String employeeId) {
		Employee emp = new Employee();
		emp.setId(Long.valueOf(employeeId));
		
		return ResponseEntity.ok().body(emp);
	}
	
	@GetMapping("/employees/employeeId/{employeeId}/name/{name}")
	public ResponseEntity<Employee> findByIdAndName(@PathVariable String employeeId, @PathVariable String name) {
		Employee emp = new Employee();
		emp.setId(Long.valueOf(employeeId));
		emp.setName(name);
		
		return ResponseEntity.ok().body(emp);
	}

}
