package com.kh.statement.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.kh.statement.model.vo.Member;

public class MemberDao {
	/*
	 * [JDBC용 인터페이스]
	 * 
	 * - Connection	: 데이터베이스와의 세션(연결)을 나타내는 인터페이스
	 * -> 데이터베이스와의 통신 채널 연결
	 * -> 트랜잭션 관리
	 * -> SQL문을 실행을 위한 Statement 객체 생성
	 * * 주의사항)
	 *   메모리 누수를 방지하기 위해 항상 close() 자원 반납
	 *   트랜잭션 수동으로 관리한다면 DML 수행 이후에는 반드시 COMMIT/ROLLBACK
	 *   
	 *   
	 * - Statement	: SQL문을 실행하고 결과를 받아오기 위한 인터페이스
	 * 1. Statement : 정적 SQL문(완성된 SQL문) 실행
	 * 2. PreparedStatement : 파라미터화된 SQL문(미완성된 SQL문) 실행
	 * 3. CallableStatement : 저장 프로시저 호출
	 * 
	 * 
	 * - ResultSet	: SELECT문 실행결과를 담는 테이블형태의 데이터 셋
	 * -> 커서(cursor)라는 개념을 이용해서 데이터에 접근
	 * -> 다양한 데이터타입 변환 메소드를 제공
	 * 
	 * 
	 * ★★★★[JDBC 처리 순서] ★★★★
	 * 
	 * 1) JDBC Driver 등록 : DBMS제조사에서 제공하는 클래스를 리플렉션을 이용해 등록
	 * 2) Connection 객체 생성 : 접속하고자하는 DB정보를 전달하면서 Connection 객체 반환
	 * 3) Statement 객체 생성 : Connection 객체를 이용해서 생성
	 * 4) SQL문을 전달하면서 실행 : Statement 객체를 이용해서 SQL문을 실행
	 * 	  > SELECT 	- executeQuery() 호출
	 * 	  > DML 	- executeUpdate() 호출
	 * 5) 결과받기
	 * 	  > SELECT 	-> ResultSet(조회된 데이터들이 테이블형태로 담김)객체로 받기
	 * 	  > DML 	-> int(처리된 행 수)로 받기
	 * 6)
	 *    > SELECT 	-> ResultSet에 담겨있는 데이터를 하나하나씩 뽑아서 VO객체로 담기
	 *    > DML		-> 트랜잭션을 수동으로 처리한다면 COMMIT / ROLLBACK
	 * 7) 자원반납 -> close() -> 생성의 역순으로
	 * 8) 결과값 반환
	 *    > SELECT 	-> 6에서 가공한 형태를 반환
	 *    > DML 	-> int(처리된 행 수)
	 * 
	 */
	
	public int save(Member member) {
		
		// 0) 필요한 변수를 머저 선언 및 null 값으로 초기화
		Connection conn = null; // 접속할 DB서버와의 연결정보를 담는 객체
		Statement stmt = null;	// SQL문 실행 후 결과를 받기 위한 객체
		int result = 0;			// DML 수행 후 결과를 받기 위한 변수
		
		// SQL문 (정적인 형태)
		/*
		 * INSERT
		 *   INTO
		 * 		  MEMBER
		 * VALUES
		 *        (
		 *        SEQ_USERNO.NEXTVAL
		 *      , '사용자가 입력한 아이디 값'
		 *      , '사용자가 입력한 비밀번호 값'
		 *      , '사용자가 입력한 이름 값'
		 *      , '사용자가 입력한 이메일 갑'
		 *      , SYSDATE
		 *        )
		 */
		String sql = "INSERT "
					 + "INTO "
				          + "MEMBER "
				   + "VALUES "
				          + "("
				          + "SEQ_USERNO.NEXTVAL"
				         + ", '" + member.getUserId() + "'"
				         + ", '" + member.getUserPwd() + "'"
				         + ", '" + member.getUserName() + "'"
				         + ", '" + member.getEmail() + "'"
				         + ", SYSDATE"
				           + ")";
		// System.out.println(sql);	    						// INSERT INTO MEMBER VALUES (SEQ_USERNO.NEXTVAL, 'user02', 'pass02', '고길동', 'ko@kh.com', SYSDATE)     
	    // 주의) SQL문을 문법적으로 올바르게 작성하지 못했다면
		// SQLSynataxErrorException이 발생함
		
		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 1. 오타가 날 경우
			// 2. 프로젝트에 라이브러리를 추가하지 않아서 진짜로 클래스를 못찾는 경우
			// -> ClassNotFoundException 발생
			
			// 2) Connection 객체 생성 (DB와 연결 -> URL, 사용자이름, 비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			// 1. URL을 잘못 적었을 수 있음
			// 2. 사용자 계정명 잘못 적었을 수 있음
			// 3. 비밀번호를 잘못 적었을 수 있음
			// 4. 서버가 안 켜져(열려)있을 수 있음
			// 5. 접속 권한을 부여받지 못했을 수 있음
			// -> SQLException 발생
			
			// 로직 처음부터 만드는 거라 AutoCommit 끄기 
			conn.setAutoCommit(false);
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement(); // new Statement(conn); 이런 느낌
			
			// 4, 5) DB에 완성된 SQL문 전달하면서 실행도 하고 결과도 받고
			result = stmt.executeUpdate(sql);
			// INSERT 시 값에 문제가 있을 수 있음.
			// 자료형이 맞지 않음
			// 제약조건에 위배
			// 데이터의 크기가 컬럼의 크기보다 큼
			// -> SQLException 발생
			
			// 6) 트래잭션 처리
			if(result > 0) {
				conn.commit();
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 사용이 모두 끝난 JDBC용 객체 자원반납
			// 생성된 순서의 역순으로 close() 호출
			try {
				if(stmt != null) stmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 8) 결과반환
		return result;
	}
}
