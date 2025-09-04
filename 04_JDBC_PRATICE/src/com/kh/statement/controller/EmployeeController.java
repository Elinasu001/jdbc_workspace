package com.kh.statement.controller;

import java.util.List;

import com.kh.statement.model.dao.EmployeeDAO;
import com.kh.statement.model.service.EmployeeService;
import com.kh.statement.model.vo.Employee;

public class EmployeeController {

	public List<Employee> findAll(){
		
		List<Employee> employees = new EmployeeService().findAll();
		
		return employees;
	}
	
	public List<Employee> findByDmtEmployee(String deptTitle) {
		List<Employee> employees = new EmployeeService().findByDmtEmployee(deptTitle);
		return employees;
	}
	
	public List<Employee> findByJobEmployee(String jobName){
		List<Employee> employees = new EmployeeService().findByJobEmployee(jobName);
		return employees;
	}
	
}
