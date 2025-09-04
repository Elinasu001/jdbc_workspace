package com.kh.statement.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.statement.model.vo.Employee;

public class EmployeeDAO {
	
	private Properties prop = new Properties();
	
	public EmployeeDAO() {
		try {
			prop.loadFromXML(new FileInputStream("resources/member-mapper.xml"));// 도메인, 매핑할 목적 암묵적인 명
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	public List<Employee> findAll(Connection conn){
		List<Employee> employees = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("findAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Employee employee = new Employee(rset.getString("EMP_ID")
						                        ,rset.getString("EMP_NAME")
						                        ,rset.getInt("SALARY")
						                        ,rset.getString("DEPT_TITLE")
						                        ,rset.getString("JOB_NAME"));
				employees.add(employee);
						
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return employees;
	}
	
	
	public Employee findByDmtEmployee(Connection conn, String deptTitle) {
		
		Employee employee = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("findByDmtEmployee");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptTitle);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				employee = new Employee(rset.getString("DEPT_CODE")
									   ,rset.getString("DEPT_ID")
									   ,rset.getString("DEPT_TITLE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return employee;
	}
	
	
	
}
