package com.kh.statement.model.dao;

// static 붙이면 JDBCTemplate 사용안하고 메소드만 호출해서 사용할 수 있음.
import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.getConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	// 메소드 호출할 때마다
	// xml 파일로부터 Properties 객체로 입력받는 코드를 써야함 중복이다.
	// new MemberDao().xxx
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/member-mapper.xml"));// 도메인, 매핑할 목적 암묵적인 명
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public int save(Connection conn, Member member) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		// 상단 기본생성자로 생성
		/*
		Properties prop = new Properties(); 
		try {
			prop.loadFromXML(new FileInputStream("resources/member-mapper.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		String sql = prop.getProperty("save");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			pstmt.setString(3, member.getUserName());
			pstmt.setString(4, member.getEmail());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public List<Member> findAll(Connection conn){
		
		List<Member> members = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;	
		
		// 상단 기본생성자로 생성
		/*
		Properties prop = new Properties(); 
		try {
			prop.loadFromXML(new FileInputStream("resources/member-mapper.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		String sql = prop.getProperty("findAll");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
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
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return members;
	}
	
	public Member findById(Connection conn, String userId) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("findById");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
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
		ResultSet rset = null; 
		
		String sql = prop.getProperty("findByKeyword");
		
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
		
		String sql = prop.getProperty("update");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pd.getNewPassword());
			pstmt.setString(2, pd.getUserId());
			pstmt.setString(3, pd.getUserPwd());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public int delete(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("delete");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	
	}
}
