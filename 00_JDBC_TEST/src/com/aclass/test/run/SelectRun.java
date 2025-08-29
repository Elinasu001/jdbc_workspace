package com.aclass.test.run;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectRun {

	public static void main(String[] args) {
		// 현재 작업중인 DB서버에서 각자 자신이 가지고 있는 TB_STUDENT 테이블의 모든 행 조회 Console에 출력
		// SELECT 문
		// ResultSet(조회된 행들의 집합)
		// => ResultSet 타입의 데이터를 받아서 뽑기
		
		// 0) 필요한 변수들 먼저 세팅
		Connection conn = null; // DB 접속 담당
		Statement stmt = null;  // SQL 실행 담당
		ResultSet rset = null;	// 실행 받아줄 SELECT문 ( INSERT/UPDATE/DELETE랑 다름! )
		// 0) 끝!
		
		// 요청할 SQL문도 미리 완성형태로 준비해두기
		/*
		String sql = "SELECT "
				          + "STUDENT_ID"
				          + ", STUDENT_NAME"
				          + ", ENROLL_DATE "
				     + "FROM"
				       	  + "TB_STUDENT "
				     + "ORDER "
				       	+ "BY "
				           + "ENROLL_DATE DESC";
		*/
		
		String sql = """
						SELECT
						       STUDENT_ID
						     , STUDENT_NAME
						     , ENROLL_DATE
						  FROM
						       TB_STUDENT
						 ORDER
						    BY
						       ENROLL_DATE DESC
					 """;
		
		// 준비 끝!
		
		
		try {
			// 1. JDBC Driver 등록
			// 리플렉션(Reflection)을 이용한 드라이버 클래스 로딩
			// 실행시점에 필요한 데이터베이스 드라이버를 동적으로 로드
			// 코드의 변경 없이 데이터베이스를 연결할 수 있도록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("DB서버 접속 성공!");
			
			// 3. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4. SQL문을 실행 후 결과 받기 ResultSet
			// 실행할 SQL문이 SELECT문인 경우
			// stmt.executeQuery(sql) : ResultSet
			rset = stmt.executeQuery(sql);
			// System.out.println(rset);
			
			// ResultSet 반환됨
			// 커서를 조작 (아래로 내릴 예정)
			// next() : 커서를 조작
			while(rset.next()) { // 몇번 반복 할 지 모를 경우 for문 말고 while문 사용 
				// rset.next() boolean type
				// 커서를 조작했을 떄 행이 존재한다면 true / 행이 존재하지 않는다 false
				int studentId = rset.getInt("STUDENT_ID");
				String studentName = rset.getString("STUDENT_NAME");
				Date enrollDate = rset.getDate("ENROLL_DATE");
				System.out.println("번호 : " + studentId + ", 이름 : " + studentName + ", 등록일 : " + enrollDate);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("ojdbc 추가했나요?");
			System.out.println("oracle.jdbc.driver.OracleDriver 오타 없나요?");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("jdbc:oracle:thin:@115.90.212.20:10000:XE 오타 없나요?");
			System.out.println("사용자 계정명 / 비밀번호가 올바른가요?");
			System.out.println("SQL문 잘썼나요??");
		} finally {
			
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

