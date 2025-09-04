package com.kh.statement.model.service;

import static com.kh.common.JDBCTemplate. *;

import java.sql.Connection;
import java.util.List;
import java.util.function.Function;

import com.kh.common.JDBCTemplate;
import com.kh.statement.model.dao.EmployeeDAO;
import com.kh.statement.model.vo.Employee;


public class EmployeeService {
	
	private Connection conn = null;
	
	public EmployeeService() {
		this.conn = getConnection();
	}
	
	
	private <T> T executeQuery(Function<Connection, T> daoFunction){
		
		Connection conn = null;
		T result = null; 
		conn = getConnection();
		result = daoFunction.apply(conn);
		close(conn);
		return result;
	}
	
	public List<Employee> findAll(){
		
		return executeQuery(new EmployeeDAO()::findAll);
	}
	
	
	public List<Employee> findByDmtEmployee(String deptTitle) {
		
		return executeQuery(conn -> new EmployeeDAO().findByDmtEmployee(conn, deptTitle));
	}
	
	public List<Employee> findByJobEmployee(String jobName){
		return executeQuery(conn -> new EmployeeDAO().findByJobEmployee(conn, jobName));
	}
	
	public List<Employee> findAllDetail(String empId){
		return executeQuery(conn -> new EmployeeDAO().findAllDetail(conn, empId));
	}
	
	public int save(Employee employee) {
		
		int result = new EmployeeDAO().save(conn, employee);
		
		if(result > 0) {
			commit(conn);
		}
		close(conn);
		
		return result;
	}

}
