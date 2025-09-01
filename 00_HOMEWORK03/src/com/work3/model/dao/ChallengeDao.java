package com.work3.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	
}
