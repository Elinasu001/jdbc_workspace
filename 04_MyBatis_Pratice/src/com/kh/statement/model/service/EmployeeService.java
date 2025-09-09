package com.kh.statement.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.common.Template;
import com.kh.statement.model.dao.EmployeeDAO;
import com.kh.statement.model.dto.EmployeeDTO;
import com.kh.statement.model.vo.Employee;


public class EmployeeService {
	
	private EmployeeDAO employeeDao = new EmployeeDAO();
	
	
	public List<Employee> findAll(){
		
		SqlSession session = Template.getSqlSession();
		
		List<Employee> employees = employeeDao.findAll(session);
		
		session.close();
		
		return employees;
	}
	
	
	public List<Employee> findByDmtEmployee(String deptTitle) {
		
		SqlSession session = Template.getSqlSession();
		
		List<Employee> employees = employeeDao.findByDmtEmployee(session, deptTitle);
		
		session.close();
		
		return employees;
	}
	
	public List<Employee> findByJobEmployee(String jobName){
		
		SqlSession session = Template.getSqlSession();
		
		List<Employee> employees = employeeDao.findByJobEmployee(session, jobName);
		
		session.close();
		
		return employees;
	}
	
	public List<Employee> findAllDetail(int empId){
		
		SqlSession session = Template.getSqlSession();
		
		List<Employee> employees = employeeDao.findAllDetail(session, empId);
		
		session.close();
		
		return employees;
	}
	
	public List<Employee> getHighSalaryEmployees(){
		
		SqlSession session = Template.getSqlSession();
		
		List<Employee> employees = employeeDao.getHighSalaryEmployees(session);
		
		session.close();
		
		return employees;
	}
	
	public List<Employee> getLowSalaryEmployees(){
		
		SqlSession session = Template.getSqlSession();
		
		List<Employee> employees = employeeDao.getLowSalaryEmployees(session);
		
		session.close();
		
		return employees;
	}
	
	public int save(Employee employee) {
		
		SqlSession session = Template.getSqlSession();
		
		int result = employeeDao.save(session, employee);
		
		if(result > 0) {
			session.commit();
		}
		
		session.close();
		
		return result;
	}
	
	public int update(EmployeeDTO ed) {
		
		SqlSession session = Template.getSqlSession();
		
		int result = employeeDao.update(session, ed);
		
		if(result > 0) {
			session.commit();
		}
		
		session.close();
		
		return result;
	}
	
	public int delete(String empId) {
		SqlSession session = Template.getSqlSession();
		
		int result = employeeDao.delete(session, empId);
		
		if(result > 0) {
			session.commit();
		}
		
		session.close();
		
		return result;
	}

}
