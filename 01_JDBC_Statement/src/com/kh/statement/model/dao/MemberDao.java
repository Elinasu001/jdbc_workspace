package com.kh.statement.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	
	public List<Member> findAll() {
		
		// 0) 필요한 변수 선언
		Connection conn = null;	// 연결된 DB의 정보를 담는 객체
		Statement stmt = null;	// SQL문을 실행하고 결과를 받기 위한 객체
		ResultSet rset = null;	// SELECT문을 수행하고 조회 결과값들이 담길 객체
		List<Member> members = new ArrayList();
		
		String sql = """
						SELECT
						       USERNO
						     , USERID
						     , USERPWD
						     , USERNAME
						     , EMAIL
						     , ENROLLDATE
						  FROM
						       MEMBER
						 ORDER
						    BY
						       ENROLLDATE DESC
					 """;
		
		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			
			// ExceptionHandling
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) SQL(SELECT)문 실행 후 결과 반환(ResultSet)
			rset = stmt.executeQuery(sql);
			
			// 6) Mapping
			// 서로다른 형태의 데이터 모델 간의 연결을 정의하는 과정
			// 자바 == 데이터 객체 형태
			// 구조를 맞춰주는 작업
			// 관계형 데이터베이스 == 테이블 형태
			
			// 현재 조회 결과는 ResultSet에 담겨있음
			// 한 행씩 뽑아서 VO 객체의 필드에 담기
			// rset.next()
			// 커서를 한 줄 아래로 옮겨주고 존재한다 true / 존재하지 않는다 false
			
			while(rset.next()) {
				
				// 현재 rset의 커서가 가리키고 있는 행의 데이터를
				// 하나하나씩 뽑아서 MemberVO의 필드에 대입
				Member member = new Member();
				
				// ResultSet 객체로부터 어떤 컬럼의 값을 뽑을건지 메소드를 호출하면서 컬럼명을 명시
				// rset.getInt(컬럼명)	: 정수형 값을 int형으로 매핑할 때
				// rset.getString(컬럼명) : 문자열형 값을 String형으로 매핑할 때
				// rset.getDate(컬럼명)	: 날짜형 값을 java.sql.Date형으로 매핑할 때
				// 컬럼명 : 대소문자를 가리지 않음
				// 컬럼명말고 컬럼의 순번으로도 가능함
				// 권장사항 : 컬럼명으로 작성하고 대문자로 작성
				
				member.setUserNo(rset.getInt("USERNO"));
				member.setUserId(rset.getString("USERID"));
			    member.setUserPwd(rset.getString("USERPWD"));
			    member.setUserName(rset.getString("USERNAME"));
			    member.setEmail(rset.getString("EMAIL"));
				member.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 컬럼명이 오타났을 때 SQLException 발생
				// 테이블 컬럼값을 뽑아내는 것이 아니고
				// ResultSet에서 조회된 결과값을 뽑아내는 것 예) 별칭 넣을 경우 주의
				
				// 항 행에 모든 컬럼의 데이터 값을
				// 각각의 필드에 담아 Member 객체로 옮겨담으면 끝 !!!
				
				// 조회된 Member 들을 싹 다 돌려보내야함
				// 배열 특) 크기 정해야됨
				// 조회 결과가 몇 행일지 특정지을 수 없음
				// 여러 정보를 담아줄 저장소 ==> List (클래스 상단에 적어둠)
				members.add(member);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다쓴 JDBC용 객체 자원반납(생성된 순서의 역순으로) => close()
			try {
				if(rset != null) rset.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			
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
		
		// 8) 매핑된 객체들 결과 반환
		// 조회 결과들을 매핑해놓은 Member객체 들의 주소값을 요소로 가지고 있는
		// List의 주소값을 반환
		return members;
	}
	
	public Member findById(String userId) {
		Member member = null;
		
		
		// 0) 필요한 변수들 먼저 선언
		// JDBC 관련 인터페이스
		// Connection, Statement, ResultSet
		/*
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		*/
		// 이번에는 실행할 SQL문(완성형태로) 
		/*
		 * SELECT
		 *        USERNO
		 *      , USERID
		 *      , USERPWD
		 *      , USERNAME
		 *      , EMAIL
		 *      , ENROLLDATE
		 *   FROM
		 *        MEMBER
		 *  WHERE
		 *        USERID = '사용자가 입력한 ID값'
		 */
		
		String sql = """
						SELECT
						       USERNO
						     , USERID
						     , USERPWD
						     , USERNAME
						     , EMAIL
						     , ENROLLDATE
						  FROM
						       MEMBER
						 WHERE
						       USERID = 
					 """;
		sql += "'" + userId + "'";
		
		
		try {
			//1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");		
		
			// 2) Connection 객체 생성
			// 3) Statement 객체 생성
			// 4) SQL 실행
			// 5) ResultSet 받아오기
			try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234")){
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(sql);
				
				// 6) 조회결과가 담긴 ResultSet객체에서
				// 조회결과가 존재한다면 VO객체의 필드에 옮겨담기
				// ID 가지고 검색(UNIQUE) 한 행만 조회 (즉, 하나의 VO객체를 담는다)
				if(rset.next()) {
					member = new Member(rset.getInt("USERNO")
							          , rset.getString("USERID")
							          , rset.getString("USERPWD")
							          , rset.getString("USERNAME")
							          , rset.getString("EMAIL")
							          , rset.getDate("ENROLLDATE"));
				}
				
				
			}catch (SQLException e){
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// 8) 결과값 반환
		return member;
		
	}
	
	
}
