package com.kh.statement.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

public class MemberDao {
	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@115.90.212.20:10000:XE";
	private final String USERNAME = "PSH08";
	private final String PASSWORD = "PSH081234";
	
	/*
	 * DAO(Data Access Object)
	 * 
	 * 지금 시점 DAO 에서는 DataBase 관련된 작업(CRUD)를 전문적으로 담당하는 객체
	 * DAO 안에 있는 모든 메소드를 데이터베이스 관련된 작업으로 구성할 것
	 * 
	 * 
	 * SQL
	 * SELECT / INSERT / UPDATE / DELETE
	 * 
	 * 하나의 메소드는 하나의 SQL문만 실행할 것!
	 * 
	 * Controller를 통해 호출된 기능을 수행 ! -> 02번 프로젝트까지만
	 * DB에 직접 접근한 후 해당 SQL문을 실행한 후 결과 받아오기 (JDBC)
	 * 
	 */
	/*
	 * JDBC 용 객체
	 * - Connection : DB와의 연결정보를 담는 객체(IP주소, PORT번호, 사용자이름, 비밀번호)
	 * - Statement 	: Connection 에 담겨있는 연결정보 DB에 SQL문을 보내서 실행하고 결과도
	 *  			  받아오는 다재다능 객체
	 * - ResultSet 	: 실행할 SQL문이 SELECT문일 경우 조회된 결과가 처음 담겨있는 객체
	 * - PreparedStatement : SQL문을 미리 준비하는 개념 
	 * 						 미완성된 SQL문을 미리 전달하고 실행하기 전 완성형태로 만든 뒤
	 * 						 SQL문을 실행함
	 * 						 미완성된 SQL문에 사용자가 입력한 값들이 들어갈 수 있도록 
	 * 						 공간(위치홀더, ?)을 마련해 두고 실행 시점에 값을 채워 넣는다.
	 * 
	 * - Statement(부모) 와 PreparedStatement(자식)는 부모자식 관계
	 * 
	 * - 차이점
	 * 
	 * 1) Statement는 완성됨 SQL문, PreparedStatement는 미완성된 SQL문
	 * 
	 * 2) 객체 생성 방법
	 * 		 Statement == 커넥션객체.createStatement();
	 * 		 PreparedStatement == 커넥션객체.prepareStatement(sql); <-- 이게 핵심!!!
	 * 
	 * 3) SQL문 실행
	 * 		 Statement == stmt.executeXXX(sql);
	 * 		 PreparedStatement == stmt.executeXXX(); <-- 이게 핵심!!! 미리 보냈으니 안넣고 돌림
	 * 
	 * ? 위치홀더에 실제 값을 Bindng 해준 뒤 실행한다.
	 * pstmt.setString()
	 * pstmt.setInt()
	 * 
	 * 
	 * ---- JDBC 절차
	 * 0) 필요한 변수들 세팅
	 * 1) JDBC Driver 등록 : 해당 DBMS에서 제공하는 클래스를 String 형으로 동적으로 등록
	 * 2) Connection 객체 생성 : DB와의 세션연결 연결할 때 필요한 정보를 인자로 전달(URL, 사용자이름, 비밀번호)
	 * 3_1) PreparedStatement 객체 생성 : Connection 객체 생성(미완성된 SQL문을 생성과 동시에 꼭 전달 !!!)
	 * 3_2) 현재 미완성된 SQL문을 완성형태로 만들어주기
	 * ==> 미완성일 경우에만 해당 / 완성된 경우에는 생략
	 * 4) SQL문 실행 : executeXXX() => SQL을 절대로 인자로 전달하지 않음!!! 절~~~대로!!! 절~~~대로!!!
	 * 				> SELECT : executeQuery()
	 * 				> INSERT/UPDATE/DELETE : executeUpdate()
	 * 
	 * 5) 결과받기 : 
	 * 			   > SELECT : ResultSet(조회된 데이터들이 담겨있음)
	 * 			   > INSERT/UPDATE/DELETE : int(처리된 행의 개수)
	 * 
	 * 6) SELECT : Result에 담겨있는 컬럼값들을 커서를 옮겨가며 한행 씩 접근해서 하나하나 뽑아서
	 * 			   VO객체의 필드에 매핑(옮겨담기) -> VO객체가 여러 개일 경우 -> VO들을 List의 요소로 관리
	 *    INSERT/UPDATE/DELETE : 트랜잭션 처리
	 *    
	 * 7) 자원 반남 : 사용이 다 끝나 JDBC용 객체들을 생성의 역순으로 자원 반납 -> close()
	 * 
	 * 8) 결과 반환 :
	 * 	  > SELECT -> 6에서 만든거
	 *    > INSERT/UPDATE/DELETE -> 처리된 해의 개수
	 * 
	 */
	
	public int save(Member member) {
		
		// 0) 필요한 변수 선언
		//Connection conn = null;				// DB와의 세션
		//PreparedStatement pstmt = null;		// SQL문 실행 후 결과 받기
		int result = 0;						
		
		// SQL문
		/*
		 * INSERT
		 *   INTO
		 *        MEMBER
		 * VALUES
		 *        (
		 *        SEQ_USERNO.NEXTVAL
		 *      , '사용자가 입력한 아이디'
		 *      , '사용자가 입력한 비밀번호'
		 *      , '사용자가 입력한 이름'
		 *      , '사용자가 입력한 이메일'
		 *      , SYSDATE
		 *        )
		 */
		String sql = """
						INSERT
						  INTO
						       MEMBER
						VALUES
							   (
							   SEQ_USERNO.NEXTVAL
							 , ?
							 , ?
							 , ?
							 , ?
							 , SYSDATE
							   )
				     """;
		
	  try {
	        // 1) JDBC Driver 등록
	        Class.forName(DRIVER);

	        // (try-with-resources)
	        // 2) Connection 객체 생성
	        // 3_1) PreparedStatement 객체 생성
	        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            conn.setAutoCommit(false);
	            //3_2) 현재 미완성된 SQL문을 완성형태로 만들어주기
	            // 위치홀더(?)에 값 바인딩
	            pstmt.setString(1, member.getUserId());
	            pstmt.setString(2, member.getUserPwd());
	            pstmt.setString(3, member.getUserName());
	            pstmt.setString(4, member.getEmail());
	            // 위치 홀더를 올바르게 다 채우지 못했다.
	            // 자료형이 컬럼의 자료형과 맞지않는 값을 Bind
	            
	            
	            // 4) SQL 실행
	            result = pstmt.executeUpdate();

	            // 5) 트랜잭션 처리
	            if (result > 0) {
	                conn.commit();
	            } else {
	                conn.rollback();
	            }
	        }
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // 6) 결과 반환
	    return result;
	}
	
	public List<Member> findAll(){
		// 0) 필요한 변수 세팅
		//Connection conn = null;
		//PreparedStatement pstmt = null;
		//ResultSet rset = null;
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
			// 1) JDBC Driver등록
			Class.forName(DRIVER);
			
			// try-with-resources로 Connection, PreparedStatement, ResultSet 자동 close
	        // 2) Connection 객체 생성 (DB와 연결 -> URL, 사용자이름, 비밀번호)
			// 3) PreparedStatement 객체 생성
			// 4, 5) SQL(SELECT)문을 실행 후 결과도 받아오기
			// 7) 사용이 모두 끝난 JDBC용 객체 반납(생성된 순서의 역순으로)
			try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rset = pstmt.executeQuery()) {

	            // 6) 결과값 매핑
				// 조회결과가 존재하는가를 먼저 판단한 뒤 
				// 존재할 경우 한 행씩 접근해서 컬럼의 값을 뽑아서 VO필드에다가 매핑(시원하게!)
	            while (rset.next()) {
	            	/*
	                Member member = new Member(
	                    rset.getInt("USERNO"),
	                    rset.getString("USERID"),
	                    rset.getString("USERPWD"),
	                    rset.getString("USERNAME"),
	                    rset.getString("EMAIL"),
	                    rset.getDate("ENROLLDATE")
	                );
	                */
	            	Member member = new Member();
	                member.setUserNo(rset.getInt("USERNO"));
	                member.setUserId(rset.getString("USERID"));
	                member.setUserPwd(rset.getString("USERPWD"));
	                member.setEmail(rset.getString("EMAIL"));
	                member.setEnrollDate(rset.getDate("ENROLLDATE"));
	                members.add(member);
	            }
	        }
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// 8) 컨트롤러에게 결과 반환
		return members;
	}
	/*
	 * PreparedStatement가 Statement보다 좋은이유
	 * 
	 * 1. 구문분석 및 컴파일 최적화
	 * 
	 * stmt.excuteUpdate(sql);
	 * pstmt.excuteUpdate();
	 * 
	 * Statement는 매 번 SQL문을 파싱하고 실행하지만
	 * PreparedStatement는 SQL쿼리를 최초 1회만 파싱하고 실행계획을 캐싱(메모리에 올림)한다.
	 * 
	 * 재사용적인 측면에서 훨씬 효율적이다.
	 * 
	 * 2. DB서버에 대한 트래픽 감소
	 * 
	 * 쿼리 자체는 한 번만 전송하고 이후에는 바인딩할 값만 전송하기 때문에 효율적이다.
	 * 
	 * 동일쿼리를 반복 실행할 때, 높은 트래픽이 몰리는 애플리케이션일 때 더욱더 효율적이다.
	 * 
	 * DB작업 -> 계획 세울 때 리소스를 많이 잡아먹음
	 * 
	 * 3. SQL Injection 방지
	 * 
	 * SELECT
	 *        EMAIL
	 *   FROM
	 *        MEMBER
	 *  WHERE
	 *        USERID = '" + m.getUserId() + "'"
	 *    AND 
	 *        USERPWD = '" + m.getUserPwd()	+ "'"
	 * 
	 * 사용자의 입력값 == ' OR '1'='1 일 경우(SQL 인젝션 공격할 경우),
	 * Statement는 이걸 막을 수가 없음
	 * 
	 * PreparedStatement는 인젝션 방지가 됨 ==> 보안적인 측면에도 훨씬 좋음
	 *  
	 * 
	 */
	
	public Member findById(String userId) {
		Member member = null;
		
		// 0) 필요한 변수들 선언
		//Connection conn = null;
		//PreparedStatement pstmt = null;
		//ResultSet rset = null;
		
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
						       USERID = ?
					 """;
		
		try {
			// 1) JDBC Driver등록
			Class.forName(DRIVER);
			
			// 2) Connection 객체 생성
			// 3_1) PreparedStatement 객체 생성
			try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)){
				
				// 3_2) 값채우기
				pstmt.setString(1, userId);
				
				// 4, 5) SQL문 실행 및 결과받기 (null은 있을 수 없음!!)
				try(ResultSet rset = pstmt.executeQuery()){
					// 6) rset에 값있나없나 판단 후 있다 VO필드에 매핑
					if(rset.next()) {
						member = new Member(rset.getInt("USERNO")
										   ,rset.getString("USERID")
										   ,rset.getString("USERPWD")
										   ,rset.getString("USERNAME")
										   ,rset.getString("EMAIL")
										   ,rset.getDate("ENROLLDATE"));
					}
				}
				
			}
			
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		// finally 할경우 conn, pstmt, conn 자원 반납해주기
		
		// 8) 결과 반환
		return member;
	}
	
	// 이름 키워드 검색
	public List<Member> findByKeyword(String keyword){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> members = new ArrayList();
		
		// 실행할 SQL문
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
						       USERNAME LIKE '%'||?||'%'
						 ORDER
						    BY
						       ENROLLDATE DESC
					 """;
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(1, keyword);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				members.add(new Member(rset.getInt("USERNO")
									  ,rset.getString("USERID")
									  ,rset.getString("USERPWD")
									  ,rset.getString("USERNAME")
									  ,rset.getString("EMAIL")
									  ,rset.getDate("ENROLLDATE")));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(rset != null) rset.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null) pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return members;
	}
	
	// PasswordDTO : 객체간 이동 하고 싶을 경우 
	public int update(PasswordDTO pd) {
		// update 할 일 : 전달받은 값을 가지고 값이 존재하는 행을 찾아서 정보를 갱신해줌.
		// 얘가 맡은 일 : SQL문 실행하고 결과받아오기
		
		// 0) 
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = """
				  		UPDATE
				  		       MEMBER
				  		   SET
				  		       USERPWD = ?
				  		 WHERE
				  		       USERID = ?
				  		   AND
				  		       USERPWD = ?
					 """;
		// 실행순서 앞에서 부터 
		
		try {
			// 1)
			Class.forName(DRIVER);
			// 2)
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			conn.setAutoCommit(false);
			// 3_1) 
			pstmt = conn.prepareStatement(sql);
			// 3_2) // 실행순서 앞에서 부터 
			pstmt.setString(1, pd.getNewPassword());
			pstmt.setString(2, pd.getUserId());
			pstmt.setString(3, pd.getUserPwd());
			
			// 4, 5)
			result = pstmt.executeUpdate();
			
			// 6) 
			if(result > 0) {
				conn.commit();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7)
			try {
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
			try {
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 8)
		return result;
	}
	
	
	public int delete(Member member) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = """
						DELETE
						  FROM
						       MEMBER
						 WHERE
						       USERID = ?
						   AND
						       USERPWD = ?
				     """;
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			result = pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	
	
	
	
	
	
}
