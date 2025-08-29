package com.aclass.test.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestRun {

	public static void main(String[] args) {
		//JDBC 맛보기
		/*
		 * 1. 디비버 실행(클라이언트 프로그램 실행) == 재생버튼 누르기
		 * 
		 * 2. 접속하기 누름
		 * 
		 * 3. DBMS 선택
		 * 
		 * 4. ojdbc.jar --> 등록 
		 * 
		 * 5. IP주소, PORT번호, 사용자계정, 비밀번호가지고 연결
		 * 
		 * 6. 새 SQL편집기
		 * 
		 * 7. INSERT문 작성 ==> INSERT INTO 테이블명 VALUEW('값', '값', '값');
		 * 
		 * 8. SQL문을 실행
		 * 
		 * 9. UpdatedRows: 1
		 * 
		 * 10. COMMIT;
		 * 
		 */
		
		// 0) 필요한 변수 세팅
		Connection conn = null; // 2. ~ 4. 역할 수행
		Statement stmt = null;  // 6. ~ 8. 역할 수행
		
		int result = 0; // 9. 실행 받아줄 int 변수 선언
		
		// 사용자에게 값을 입력받아서 DBMS로 전달 => 테이블에 INSERT
		Scanner sc = new Scanner(System.in);
		
		System.out.print("번호를 입력해주세요 > ");
		int num = sc.nextInt();
		sc.nextLine();
		System.out.print("이름을 입력해주세요 > ");
		String name = sc.nextLine();
		
		// 실행할 SQL문(완성된 형태로 만들어주기)  // 7. _ SQL 문자열데이터 완료!
		String sql = "INSERT INTO TB_STUDENT VALUES(1, '홍길동', SYSDATE)";
		sql = "INSERT INTO TB_STUDENT VALUES(" + num + ",'" + name + "', SYSDATE)"; // name에 '' sql sysntax 발생 할 수 있느니 잘 적어주기 '홍길동'
		
		// 여기까지 준비 단계
		
		try {
			// 1) JDBC Driver 등록 -> ojdbc.jar // 3. 4. 완료!
			//Driver 등록은 프로그램 실행 시 딱 1회만 하면됨
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			System.out.println("Driver 등록 성공!");
			
			// 2) DB서버와의 연결(IP주소, PORT번호, 사용자이름, 비밀번호) // 5.  완료!
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			// getConnection 은 connection(conn)반환//url : DBMS 마다 적는 방법이 다름 , 버전마다도 다름.
			System.out.println("DB서버 접속 성공!");
			
			conn.setAutoCommit(false); // auto commit x -> 직접 커밋 해보기
			
			// 3) 새 SQL 편집기 열기  // 6. 완료!
			stmt = conn.createStatement(); // statement 객체 반환 해주는데 변수 선언 미리 해놈 
			System.out.println("Statement 객체 생성!");
			
			// 4) SQL문 실행      // 8. 완료!
			//	 UpdatedRows : N
			//   UpdatedRows : N
			//	 UpdatedRows : N
			//   DML(INSERT, UPDATE, DELETE) => 처리된 행의 개수
			//   executeUpdate(dml문); => int 로 return : row count ==> 받기 위해 result 변수 선언
			result = stmt.executeUpdate(sql);    
							  // 대입한 변수 7번
			System.out.println("SQL문 실행 성공!");
			
			// 5) 트랜잭션처리
			if(result > 0) { // INSERT에 성공했을 경우
				conn.commit();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
