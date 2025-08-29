package com.work1.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectRun {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = """
					  	SELECT
					  	       EMP_ID
					  	     , EMP_NAME
					  	     , EMP_NO
					  	     , EMAIL
					  	     , PHONE
					  	     , DEPT_CODE
					  	     , JOB_CODE
					  	     , SAL_LEVEL
					  	     , SALARY
					  	     , BONUS
					  	     , HIRE_DATE
					  	  FROM
					  	       EMPLOYEE
					  	 ORDER
					  	    BY
					  	       HIRE_DATE DESC
				     """;
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("DB서버 접속 성공!");
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				
				int empId = rset.getInt("EMP_ID");
				String empName = rset.getString("EMP_NAME");
				String empNo = rset.getString("EMP_NO");
				String email = rset.getString("EMAIL");
				String phone = rset.getString("PHONE");
				String deptCode = rset.getString("DEPT_CODE");
				String jobCode = rset.getString("JOB_CODE");
				String salLevel = rset.getString("SAL_LEVEL");
				int salary = rset.getInt("SALARY");
				double bonus = rset.getDouble("BONUS");
				String hireDate = rset.getString("HIRE_DATE");
				System.out.println("사원번호 : " + empId +
						           "직원명 : " + empName +
						           "주민등록번호 : " + empNo +
						           "이메일" + email +
						           "번호 : " + phone +
						           "부서코드 : " + deptCode +
						           "직급코드 : " + jobCode +
						           "급여등급 : " + salLevel +
						           "급여 : " + salary +
						           "보너스율 : " + bonus +
						           "입사일 : " + hireDate);
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(rset != null && !rset.isClosed()) {
					rset.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

}
