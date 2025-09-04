package com.kh.statement.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.statement.model.dao.MemberDao;
import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

public class MemberService {
	private Connection conn = null; 
	
	
	public MemberService() {
		this.conn = getConnection();
	}
	
	public int save(Member member) {
		
		int result = new MemberDao().save(conn, member);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} 
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public List<Member> findAll(){
		
		List<Member> members = new MemberDao().findAll(conn);
		
		close(conn);
		
		return members;
	
	}
	
	public Member findById(String userId) {
		
		Member member = new MemberDao().findById(conn, userId);
		
		
		close(conn);
		
		return member;
	}
	
	public List<Member> findByKeyword(String keyword){
		
		List<Member> members = new MemberDao().findByKeyword(conn, keyword);
		
		close(conn);
		
		return members;
	}
	
	public int update(PasswordDTO pd) {
		
		if(pd.getNewPassword().length() > 20) {
			return 0;
		}
		
		Member member = new MemberDao().findById(conn, pd.getUserId());
		
		if(member == null) {
			
			return 0;
		}
		int result = new MemberDao().update(conn, pd);
		if(result > 0) {
			commit(conn);
		}
		close(conn);
		
		return result;
	}
	
	public int delete(Member member) {
		
		int result = new MemberDao().delete(conn, member);
		
		if(result > 0) {
			commit(conn);
		}
		
		close(conn);
		
		return result;
	}
	
}
