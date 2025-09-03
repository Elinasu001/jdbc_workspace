package com.work3.statement.model.dao;

import static com.work3.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.work3.common.JDBCTemplate;
import com.work3.statement.model.dto.TitleDTO;
import com.work3.statement.model.vo.Challenge;

public class ChallengeDao {
	
	
	public int save(Connection conn, Challenge challenge) {
		
		// 필요한 변수 세팅
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = """
						INSERT
						  INTO
						       TB_CHALLENGE
						VALUES
							   (
							   SEQ_CHALLENGE.NEXTVAL
							 , ?
							 , ?
							 , ?
							 , ?
							 , ?
							 , ?
							 , ?
							 , SYSDATE
							   )
					 """;
		
		try {
			
			// PreparedStatment 객체 생성
			pstmt = conn.prepareStatement(sql);
			// 바인딩 : Challenge 객체의 필드값을 SQL 플레이스홀더에 연결
			pstmt.setString(1, challenge.getChallengeId());
			pstmt.setString(2, challenge.getTitle());
			pstmt.setString(3, challenge.getDesc());
			pstmt.setString(4, challenge.getStartDate());
			pstmt.setString(5, challenge.getEndDate());
			pstmt.setInt(6, challenge.getRewardPoint());
			pstmt.setInt(7, challenge.getCreatorUserNo());
			// DB에 완성된 SQL문 실행 결과 (int)로 받기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 자원 반납
			close(pstmt);
		}
		
		// Dao로 결과 반환
		return result;
	}
	
	public List<Challenge> findAll(Connection conn){
		
		// 필요한 변수 세팅
		List<Challenge> challenges = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = """
						SELECT
						       CHALLENGE_NO
						     , CHALLENGE_ID
						     , TITLE
						     , DESCRIPTION
						     , START_DATE
						     , END_DATE
						     , REWARD_POINT
						     , CREATOR_USER_NO
						     , ENROLL_DATE
						  FROM
						       TB_CHALLENGE
						 ORDER
						    BY
						       ENROLL_DATE DESC
				     """;
		
		try {
			
			// prepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// 쿼리 실행
			rset = pstmt.executeQuery();
			
			while(rset.next()) {					// 1) ResultSet 커서를 다음 '행'으로 이동하면서 (있으면 true)
				
				Challenge challenge = new Challenge(// 2) 현재 행(TITLE)의 각 컬럼값(런닝 챌린지)들을 Challenge 객체 필드에 매핑
					rset.getInt("CHALLENGE_NO")
				   ,rset.getString("CHALLENGE_ID")
				   ,rset.getString("TITLE")
				   ,rset.getString("DESCRIPTION")
				   ,rset.getString("START_DATE")
				   ,rset.getString("END_DATE")
				   ,rset.getInt("REWARD_POINT")
				   ,rset.getInt("CREATOR_USER_NO")
				   ,rset.getDate("ENROLL_DATE")
				  );
				
				challenges.add(challenge);			// 3) 매핑한 객체를 리스트(challenges)에 추가(참조 저장)
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// Dao로 결과 반환
		return challenges;
	}
	
	public Challenge findById(Connection conn, String challengeId) {
		
		// 필요한 변수 세팅
		Challenge challenge = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = """
						SELECT
						       CHALLENGE_NO
						     , CHALLENGE_ID
						     , TITLE
						     , DESCRIPTION
						     , START_DATE
						     , END_DATE
						     , REWARD_POINT
						     , CREATOR_USER_NO
						     , ENROLL_DATE
						  FROM
						       TB_CHALLENGE
						 WHERE
						 	   CHALLENGE_ID = ?
				     """;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, challengeId);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				challenge = new Challenge(
					rset.getInt("CHALLENGE_NO")
				   ,rset.getString("CHALLENGE_ID")
				   ,rset.getString("TITLE")
				   ,rset.getString("DESCRIPTION")
				   ,rset.getString("START_DATE")
				   ,rset.getString("END_DATE")
				   ,rset.getInt("REWARD_POINT")
				   ,rset.getInt("CREATOR_USER_NO")
				   ,rset.getDate("ENROLL_DATE")
				);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return challenge;
	}
	
	
	public List<Challenge> findByKeyword(Connection conn, String keyword){
		
		// 필요한 변수 세팅
		List<Challenge> challenges = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = """
						SELECT
						       CHALLENGE_NO
						     , CHALLENGE_ID
						     , TITLE
						     , DESCRIPTION
						     , START_DATE
						     , END_DATE
						     , REWARD_POINT
						     , CREATOR_USER_NO
						     , ENROLL_DATE
					     FROM
					     	   TB_CHALLENGE
					    WHERE
					           TITLE LIKE '%'||?||'%'
				     """;
		
		try {
			
			// prepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// LIKE용 바인딩 : 자바 변수 값을 SQL 플레이스홀더에 연결
			pstmt.setString(1, keyword);
			// 쿼리 실행
			rset = pstmt.executeQuery();
			
			while(rset.next()) {						// 1) ResultSet 커서를 다음 '행'으로 이동 (있으면 true)
				challenges.add(							// 5) 새 객체를 리스트 끝에 추가 (참조 저장) 
				   new Challenge( 						// 4) 아래 값들로 Challenge 객체 "즉시" 생성
				   		 rset.getInt("CHALLENGE_NO") 	// 2) 현재 행의 컬럼값들을 rset.getXxx(...)로 하나씩 꺼내서 Challenge 객체 필드에 매핑
					    ,rset.getString("CHALLENGE_ID")
					    ,rset.getString("TITLE")
					    ,rset.getString("DESCRIPTION")
					    ,rset.getString("START_DATE")
					    ,rset.getString("END_DATE")
					    ,rset.getInt("REWARD_POINT")
					    ,rset.getInt("CREATOR_USER_NO")
					    ,rset.getDate("ENROLL_DATE")  	// 3) 생성자 인자로 전달
							    
					   )
				 );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 자원 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// Dao 결과 반환
		return challenges;
	}
	

	public int update(Connection conn, TitleDTO td) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = """
						UPDATE
						       TB_CHALLENGE
						   SET
						       TITLE = ?
						 WHERE
						       CHALLENGE_ID = ?
						   AND
						       TITLE = ?
				     """;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, td.getNewTitle());
			pstmt.setString(2, td.getChallengeId());
			pstmt.setString(3, td.getTitle());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int delete(Connection conn, Challenge challenge) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = """
						DELETE
						  FROM    
						       TB_CHALLENGE
						 WHERE 
						       CHALLENGE_ID	= ?
						   AND
						       CREATOR_USER_NO = ?
					 """;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, challenge.getChallengeId());
			pstmt.setInt(2, challenge.getCreatorUserNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
}
 