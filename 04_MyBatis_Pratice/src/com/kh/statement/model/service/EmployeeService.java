package com.kh.statement.model.service;

import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;

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
		
		return executeQuery(conn -> new EmployeeDAO().findByDmtEmployee(conn, deptTitle));
	}
	
	public List<Employee> findByJobEmployee(String jobName){
		return executeQuery(conn -> new EmployeeDAO().findByJobEmployee(conn, jobName));
	}
	
	public List<Employee> findAllDetail(String empId){
		return executeQuery(conn -> new EmployeeDAO().findAllDetail(conn, empId));
	}
	
	public List<Employee> getHighSalaryEmployees(){
		return executeQuery(conn -> new EmployeeDAO().getHighSalaryEmployees(conn));
	}
	
	public List<Employee> getLowSalaryEmployees(){
		return executeQuery(conn -> new EmployeeDAO().getLowSalaryEmployees(conn));
	}
	
	public int save(Employee employee) {
		
		int result = new EmployeeDAO().save(conn, employee);
		
		if(result > 0) {
			commit(conn);
		}
		close(conn);
		
		return result;
	}
	
	public int update(EmployeeDTO ed) {
		
		int result = new EmployeeDAO().update(conn, ed);
		
		if(result > 0) {
			commit(conn);
		}
		
		close(conn);
		
		return result;
		
	}
	

}
