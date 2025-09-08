package com.kh.statement.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.common.Template;
import com.kh.statement.model.dao.MemberDao;
import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

public class MemberService {
	private MemberDao memberDao = new MemberDao();
	
	public int save(Member member) {
		/*
		 * Connection conn = JDBCTemplate.getConnection();
		 *  
		 * int result = new MmeberDao().save(conn, member);
		 * 
		 * if(result > 0){
		 * 		JDBCTemplate.close(conn);
		 * }
		 * 
		 * return result;
		 */
		
		SqlSession session = Template.getSqlSession();
		
		int result = memberDao.save(session, member);
		
		if(result > 0) { // 만약, MANAGED로 작업했으면 이 과정도 삭제됨.
			session.commit();
		}
		
		session.close();
		
		return result;
	}
	
	public List<Member> findAll(){
		
		SqlSession session = Template.getSqlSession();
		
		List<Member> members = memberDao.findAll(session);
		
		session.close();
		
		return members;
	}
	
	public Member findById(String userId) {
		
		SqlSession session = Template.getSqlSession();
		
		Member member = memberDao.findById(session, userId);
		
		session.close();
		
		return member;
	}
	
	public List<Member> findByKeyword(String keyword){
		
		SqlSession session = Template.getSqlSession();
		
		List<Member> members = memberDao.findByKeyword(session, keyword);
		
		session.close();
		
		return members;
	}
	
	
	public int update(PasswordDTO pd) {
		
		SqlSession session = Template.getSqlSession();
		
		int result = memberDao.update(session, pd);
		
		if(result > 0) {
			session.commit();
		}
		
		session.close();
		
		return result;
	}
	
	public int delete(Member member) {
		
		SqlSession session = Template.getSqlSession();
		
		int result = memberDao.delete(session, member);
		
		if(result > 0) {
			session.commit();
		}
		
		session.close();
		
		return result;
	}
}
