package com.work1.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertRun {

	public static void main(String[] args) {
		
		// 0) 필요한 변수 세팅
		Connection conn = null;
		Statement stmt = null;
		
		int result = 0;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("사원 번호를 입력해주세요 > ");
		int empId = sc.nextInt();
		sc.nextLine();
		
		System.out.print("직원명을 입력해주세요 > ");
		String empName = sc.nextLine();
		
		System.out.print("주민등록번호를 입력해주세요 > ");
		String empNo = sc.nextLine();
		
		System.out.print("메일을 입력해주세요 > ");
		String email = sc.nextLine();
		
		System.out.print("전화번호를 입력해주세요 > ");
		String phone = sc.nextLine();
		
		System.out.print("부서코드를 입력해주세요 > ");
		String deptCode = sc.nextLine();
		
		System.out.print("직급코드를 입력해주세요 >	");
		String jobCode = sc.nextLine();
		
		System.out.print("급여등급을 입력해주세요 > ");
		String salLevel = sc.nextLine();
		
		System.out.print("급여를 입력해주세요 > ");
		int salary = sc.nextInt();
		sc.nextLine();
		
		System.out.print("보너스율을 입력해주세요 > ");
		double bonus = sc.nextInt();
		sc.nextLine();
		
		System.out.print("입사일을 입력해주세요 > ");
		String hireDate = sc.nextLine();
		
		String sql = """
						INSERT
						  INTO
						       EMPLOYEE
						       (
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
						       )
					    VALUES
						  	   (
						  	   SEO_EID.NEXTVAL
						  	 , '홍길동
						  	 , '631010-2653546'  
						  	 , 'kth04@kh.or.kr'
						  	 , '01077607879'
						  	 , 'D9'
						  	 , 'J5'
						  	 , 'S4'
						  	 , 5000000
						  	 , 100
						  	 , SYSDATE
						  	   )
					 """;
		
		sql = "INSERT "
			  + "INTO "
			  	   + "EMPLOYEE "
			+ "VALUES"
				  + "(" 
				  + empId + 
			 ",'" + empName + "'," 
				  + empNo + 
		     ",'" + email + "',"
		      	  + phone + 
		     ",'" + deptCode + "',"
		          + jobCode + 
		     ",'" + salLevel + "',"
		          + salary +
		     "," + bonus + ","
		          + "SYSDATE" +
		          ")";
		
		
		try {
			//1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver 등록 성공!");
			// 2) db서버와의 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("DB서버 접속 성공!");
			
			//3) 새 sql 편집기 열기
			stmt = conn.createStatement();
			System.out.println("Statement 객체 생성!");
			
			// 4) SQL문 실행
			result = stmt.executeUpdate(sql);
			System.out.println("SQL문 실행 성공!");
			
			//5) 트랜잭션처리
			if(result > 0) {
				conn.commit();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("문제1) jdbc에다가 ojdbc파일 추가 안했을 경우 -> 1)행"); 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
