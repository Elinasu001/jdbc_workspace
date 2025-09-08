package com.kh.statement.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.statement.model.dto.EmployeeDTO;
import com.kh.statement.model.vo.Employee;

public class EmployeeDAO {
	
	public List<Employee> findAll(SqlSession session){
		
		return session.selectList("employeeMapper.findAll");
	}
	
	
	public List<Employee> findByDmtEmployee(Connection conn, String deptTitle) {
		
		List<Employee> employees = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("findByDmtEmployee");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptTitle);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				/*
				Employee names = new Employee();        
				names.setEmpName(rset.getString("EMP_NAME"));
	            employees.add(names);
	            */
				employees.add(new Employee(rset.getString("EMP_ID")
						                  ,rset.getString("EMP_NAME")
						                  ,rset.getString("DEPT_TITLE")));
	            
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return employees;
	}
	
	
	public List<Employee> findByJobEmployee(Connection conn, String jobName){
		List<Employee> employees = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("findByJobEmployee");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jobName);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Employee employee = new Employee();
				employee.setJobCode(rset.getString("JOB_CODE"));
				employee.setEmpName(rset.getString("EMP_NAME"));
				employee.setJobName(rset.getString("JOB_NAME"));
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
	
	public List<Employee> findAllDetail(Connection conn, String empId){
		List<Employee> employees = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("findAllDetail");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, empId);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Employee employee = new Employee(rset.getString("EMP_ID")
							                  ,rset.getString("EMP_NAME")
							                  ,rset.getString("EMP_NO")
							                  ,rset.getString("EMAIL")
							                  ,rset.getString("PHONE")
							                  ,rset.getString("DEPT_CODE")
							                  ,rset.getString("JOB_CODE")
							                  ,rset.getString("SAL_LEVEL")
							                  ,rset.getInt("SALARY")
							                  ,rset.getInt("BONUS")
							                  ,rset.getString("MANAGER_ID")
							                  ,rset.getString("HIRE_DATE")
							                  ,rset.getString("ENT_DATE")
							                  ,rset.getString("ENT_YN"));
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
	
	public List<Employee> getHighSalaryEmployees(Connection conn){
		List<Employee> employees = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("getHighSalaryEmployees");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Employee employee = new Employee();
				employee.setEmpName(rset.getString("EMP_NAME"));
				employee.setSalary(rset.getInt("SALARY"));
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
	
	public List<Employee> getLowSalaryEmployees(Connection conn){
		List<Employee> employees = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("getLowSalaryEmployees");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Employee employee = new Employee();
				employee.setEmpName(rset.getString("EMP_NAME"));
				employee.setSalary(rset.getInt("SALARY"));
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
	
	
	public int save(Connection conn, Employee employee) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("save");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, employee.getEmpName());
			pstmt.setString(2, employee.getEmpNo());
			pstmt.setString(3, employee.getEmail());
			pstmt.setString(4, employee.getPhone());
			pstmt.setString(5, employee.getJobCode());
			pstmt.setString(6, employee.getSalLevel());
			pstmt.setInt(7, employee.getSalary());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public int update(Connection conn, EmployeeDTO ed) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("update");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ed.getNewSalary());
			pstmt.setString(2, ed.getNewJobName());
			pstmt.setString(3, ed.getNewDeptTitle());
			pstmt.setString(4, ed.getEmpId());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
}
