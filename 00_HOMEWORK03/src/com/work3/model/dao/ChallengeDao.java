package com.work3.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.statement.model.vo.Member;
import com.work3.model.vo.Challenge;

public class ChallengeDao {
	
	public int save(Challenge challenge) {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		String sql = "INSERT INTO TB_CHALLENGE ("
		           + "CHALLENGE_NO, CHALLENGE_ID, TITLE, DESCRIPTION, "
		           + "START_DATE, END_DATE, REWARD_POINT, CREATOR_USER_NO, ENROLL_DATE"
		           + ") VALUES ("
		           + "SEQ_CHALLENGE.NEXTVAL, "
		           + "'" + challenge.getChallengeId() + "', "
		           + "'" + challenge.getTitle() + "', "
		           + "'" + challenge.getDesc() + "', "
		           + "TO_DATE('" + challenge.getStartDate() + "', 'YYYY-MM-DD'), "
		           + "TO_DATE('" + challenge.getEndDate() + "', 'YYYY-MM-DD'), "
		           + challenge.getRewardPoint() + ", "
		           + challenge.getCreatorUserNo() + ", "
		           + "SYSDATE"
		           + ")";
		
		
		System.out.println("DEBUG challenge = "
			    + challenge.getChallengeId() + ", "
			    + challenge.getTitle() + ", "
			    + challenge.getDesc() + ", "
			    + challenge.getStartDate() + " ~ " + challenge.getEndDate() + ", "
			    + challenge.getRewardPoint() + ", "
			    + "creatorUserNo=" + challenge.getCreatorUserNo());

		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver 등록!");
			// 2) Connection 객체 생성 (DB와 연결 -> URL, 사용자이름, 비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("Connection 객체 생성!");
			// 로직 처음부터 만드는 거라 AutoCommit 끄기 
			conn.setAutoCommit(false);
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement(); // new Statement(conn); 이런 느낌
			System.out.println("Statement 객체 생성!");
			
			// 4, 5) DB에 완성된 SQL문 전달하면서 실행도 하고 결과도 받고
			result = stmt.executeUpdate(sql);
			System.out.println("SQL문 실행");
			
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
	
	public List<Challenge> findAll(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		List<Challenge> challenges = new ArrayList();
		
		String sql =
			      "SELECT "
					  + " CHALLENGE_NO "
					 + ", CHALLENGE_ID "
					 + ", TITLE "
					 + ", DESCRIPTION "
					 + ", START_DATE "
					 + ", END_DATE "
					 + ", REWARD_POINT "
					 + ", CREATOR_USER_NO "
					 + ", ENROLL_DATE "
			      + "FROM "
			      	   + "TB_CHALLENGE "
				 + "ORDER "
				    + "BY "
				       + "ENROLL_DATE DESC";
			
		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");			
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) SQL(SELECT)문 실행 후 결과 반환(ResultSet)
			rset = stmt.executeQuery(sql);
			
			// 6) Mapping
			while(rset.next()) {
				
				Challenge challenge = new Challenge();
				challenge.setChallengeId(rset.getString("CHALLENGE_ID"));
				challenge.setTitle(rset.getString("TITLE"));
				challenge.setDesc(rset.getString("DESCRIPTION"));
				challenge.setStartDate(rset.getString("START_DATE"));
				challenge.setEndDate(rset.getString("END_DATE"));
				challenge.setRewardPoint(rset.getInt("REWARD_POINT"));
				challenge.setCreatorUserNo(rset.getInt("CREATOR_USER_NO"));
				challenges.add(challenge);
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
		return challenges;
	}
	
	public List<Challenge> findByKeyword(String keyword){
		List<Challenge> challenges = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT "
		           + " CHALLENGE_NO"
		           + ", CHALLENGE_ID"
		           + ", TITLE"
		           + ", DESCRIPTION"
		           + ", START_DATE"
		           + ", END_DATE"
		           + ", REWARD_POINT"
		           + ", CREATOR_USER_NO"
		           + ", ENROLL_DATE "
		           + "FROM TB_CHALLENGE "
		           + "WHERE TITLE LIKE '%" + keyword + "%' "
		           + "ORDER BY ENROLL_DATE DESC";
		 try {
	    	  // 1) JDBC Drvier 등록
	    	  Class.forName("oracle.jdbc.driver.OracleDriver");	
	    	  // 2) connection 객체 생성
	    	  conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
	    	  // 3) Statement 객체 생성
	    	  stmt = conn.createStatement();
	    	  // 4, 5) SQL(SELECT)문을 실행 후 결과 받아오기
	    	  rset = stmt.executeQuery(sql);
	    	  
	    	  // 6) ResultSet 객체에서 각 행에 접근하면서 
	    	  // 조회 결과가 있다면 컬럼의 값을 뽑아서 VO객체에 필드에 대입한 뒤
	    	  // List의 요소로 추가함
	    	  while(rset.next()) {
	    		    challenges.add(new Challenge(
	    		          rset.getInt("CHALLENGE_NO"),         // 숫자 PK
	    		          rset.getString("CHALLENGE_ID"),      // 외부 ID
	    		          rset.getString("TITLE"),
	    		          rset.getString("DESCRIPTION"),
	    		          rset.getString("START_DATE"),
	    		          rset.getString("END_DATE"),
	    		          rset.getInt("REWARD_POINT"),
	    		          rset.getInt("CREATOR_USER_NO"),
	    		          rset.getDate("ENROLL_DATE")
	    		    ));
	    		}
	    	  
	      }catch(ClassNotFoundException e	) {
	    	  e.printStackTrace();
	      }catch(SQLException e) {
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
			
			
			// 8) 결과 반환
		return challenges;
		
	}
	
}
 