package com.kh.statement.model.service;

import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.function.Function;

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
	
	
	public Employee findByDmtEmployee(String deptTitle) {
		
		return executeQuery(conn -> new EmployeeDAO().findByDmtEmployee(conn, deptTitle));
	}
	
	
	
}
