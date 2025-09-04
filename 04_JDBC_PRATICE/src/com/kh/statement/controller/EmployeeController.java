package com.kh.statement.controller;

import java.util.List;

import com.kh.statement.model.service.EmployeeService;
import com.kh.statement.model.vo.Employee;

public class EmployeeController {

	public List<Employee> findAll(){
		
		List<Employee> employees = new EmployeeService().findAll();
		
		return employees;
	}
	
	public Employee findByDmtEmployee(String deptTitle) {
		Employee employee = new EmployeeService().findByDmtEmployee(deptTitle);
		return employee;
	}
	
}
