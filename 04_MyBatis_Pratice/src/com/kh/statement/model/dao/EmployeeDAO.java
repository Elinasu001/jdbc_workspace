package com.kh.statement.model.dao;



import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.statement.model.dto.EmployeeDTO;
import com.kh.statement.model.vo.Employee;

public class EmployeeDAO {
	
	public List<Employee> findAll(SqlSession session){
		return session.selectList("employeeMapper.findAll");
	}
	
	public List<Employee> findByDmtEmployee(SqlSession session, String depTitle){
		return session.selectList("employeeMapper.findByDmtEmployee", depTitle);
	}
	
	public List<Employee> findByJobEmployee(SqlSession session, String jobName){
		return session.selectList("employeeMapper.findByJobEmployee", jobName);
	}
	
	public List<Employee> findAllDetail(SqlSession session, int empId){
		return session.selectList("employeeMapper.findAllDetail", empId);
	}
	
	public List<Employee> getHighSalaryEmployees(SqlSession session){
		return session.selectList("employeeMapper.getHighSalaryEmployees");
	}
	
	public List<Employee> getLowSalaryEmployees(SqlSession session){
		return session.selectList("employeeMapper.getLowSalaryEmployees");
	}
	
	public int save(SqlSession session, Employee employee) {
		return session.insert("employeeMapper.save", employee);
	}
	
	public int update(SqlSession session, EmployeeDTO ed) {
		return session.update("employeeMapper.update", ed);
	}
	
	public int delete(SqlSession session, String empId) {
		return session.delete("employeeMapper.delete", empId);
	}
	
}
