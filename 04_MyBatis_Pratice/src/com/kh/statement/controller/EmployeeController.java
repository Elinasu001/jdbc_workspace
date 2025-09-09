package com.kh.statement.controller;

import java.util.List;

import com.kh.statement.model.dto.EmployeeDTO;
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
	
	public List<Employee> findAllDetail(int empId){
		List<Employee> employees = new EmployeeService().findAllDetail(empId);
		return employees;
	}
	
	public List<Employee> getHighSalaryEmployees(){
		List<Employee> employees = new EmployeeService().getHighSalaryEmployees();
		return employees;
	}
	
	public List<Employee> getLowSalaryEmployees(){
		List<Employee> employees = new EmployeeService().getLowSalaryEmployees();
		return employees;
	}
	
	public int save(String empName, String empNo, String email, String phone, String jobCode, String salLevel, int salary) {
		Employee employee = new Employee(empName, empNo, email, phone, jobCode, salLevel, salary);
		int result = new EmployeeService().save(employee);
		return result;
	}
	
	public int update(String empId, int newSalary, String newJobName, String newDeptTitle) {
		EmployeeDTO ed = new EmployeeDTO(empId, newSalary, newJobName, newDeptTitle);
		int result = new EmployeeService().update(ed);
		return result;
	}

	public int delete(String empId) {
		int result = new EmployeeService().delete(empId);
		return result;
	}
	
	
	
	
}
