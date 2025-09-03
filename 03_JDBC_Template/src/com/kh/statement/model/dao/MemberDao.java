package com.kh.statement.model.dao;

// static 붙이면 JDBCTemplate 사용안하고 메소드만 호출해서 사용할 수 있음.
import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

public class MemberDao {
	
	public int save(Connection conn, Member member) {
		// 0) 필요한 변수 세팅
		PreparedStatement pstmt = null;
		int result = 0;
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
		// 1) Driver 등록
		// 2) Connection 객체 생성
		// 0~2) JDBCTemplate 반영
		
		try {
			// 3_1) PreparedStatment 객체 생성 (SQL문 미리보내기)
			pstmt = conn.prepareStatement(sql);
			// 3_2) 미완성된 SQL문일 경우 묶어줄 값전달하기 - 바인딩!
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			pstmt.setString(3, member.getUserName());
			pstmt.setString(4, member.getEmail());
			
			// 4, 5) DB에 완성된 SQL문을 실행할 결과 (int)받기 (정수로 처리된 행의 개수가 들어옴)
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6) 트랜잭션 처리 MemberService에서
			// 7_1) 할 일이 다 끝난 PreparedStatement 객체만 반납
			close(pstmt);
			// 7_2) Connection 자원반납 MemberService에서
		}
		
		// 8) 결과반환 => MemberService에 반환
		return result;
	}
	
	public List<Member> findAll(Connection conn){
		// 0) 필요한 변수 선언 먼저
		// PreparedStatement, ResultSet, sql, List
		List<Member> members = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;	// 절대 null일 수 없음
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
			// 3_1) prepareStatement 객체 생성(sql문을 인자로 전달하기)
			pstmt = conn.prepareStatement(sql);
			
			// 4, 5) SQL(SELECT)을 실행 후 결과 (ResultSet) 받기
			rset = pstmt.executeQuery();
			
			// 6) 조회결과 여부 판단 후 
			//    컬럼값을 객체 필드에 매핑!
			while(rset.next()) {
				Member member = new Member(rset.getInt("USERNO")
						       			  ,rset.getString("USERID")
						       			  ,rset.getString("USERPWD")
						       			  ,rset.getString("USERNAME")
						       			  ,rset.getString("EMAIL")
						       			  ,rset.getDate("ENROLLDATE"));
				members.add(member);
			}
			
		} catch (SQLException e ) {
			e.printStackTrace();
		} finally {
			// 7) 사용이 다 끝난 JDBC용 객체 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과반환
		return members;
	}
	
	public Member findById(Connection conn, String userId) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
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
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) { // 매핑!
				member = new Member(rset.getInt("USERNO")
						   		   ,rset.getString("USERID")
						   		   ,rset.getString("USERPWD")
						   		   ,rset.getString("USERNAME")
						   		   ,rset.getString("EMAIL")
						   		   ,rset.getDate("ENROLLDATE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return member;
	}
	
	public List<Member> findByKeyword(Connection conn, String keyword){
		List<Member> members = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null; // 셀렉문 실행할
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
					 """;
		
		try {
			pstmt = conn.prepareStatement(sql);
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return members;
	}
	
	public int update(Connection conn, PasswordDTO pd) {
		int result = 0;
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
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pd.getNewPassword());
			pstmt.setString(2, pd.getUserId());
			pstmt.setString(2, pd.getUserPwd());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public int delete(Connection conn, Member member) {
		// 0)
		int result = 0;
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
		// 1 ~ 2) 앞에서 다해왔음
		
		try {
			// 3_1)
			pstmt = conn.prepareStatement(sql);
			
			// 3_2)
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			// 4, 5)
			result = pstmt.executeUpdate();
			//6) Service로 돌아가서 진행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7)
			JDBCTemplate.close(pstmt);
		}
		
		// 8)
		return result;
	
	}
}
