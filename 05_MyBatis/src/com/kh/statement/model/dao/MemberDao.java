package com.kh.statement.model.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

public class MemberDao {
	
	public int save(SqlSession session, Member member) {
		/*
		 * int result = 0;
		 * PreparedStatement stmt = null;
		 * String sql = prop.getProperty("save");
		 * try{
		 * 		pstmt = conn.prepareStatement(sql);
		 * 		pstmt.setString(1, member.getUserId());
		 * 		pstmt.setString(2, member.getUserPwd());
		 * ... 
		 *      result = pstmt.executeUpdate();
		 * } catch(IOEXception e){
		 * 		e.printStackTrace();
		 * } finally{
		 * 		JDBCTemplate.close(pstmt);
		 * }
		 * 		return result;
		 * }
		 * 
		 *
		 * Sqlsession이 제공하는 메소드를 통해 SQL문을 찾아서 실행하고 결과도 받아볼 수 있다.
		 * sqlSession.SQL문메소드("매퍼파일의 namespace.해당SQL문의id속성값")
		 */
		
		//int result = session.insert("memberMapper.save", member);
		//return result;
		// 변수명 재사용 안하니 아래와 같이 작성
		//session.insert // insert 메소드 호출 할 때 파라미터(매개변수)하나밖에 못 넘기는 이유는 mybatis에서는 object parameter 하나밖에 못넘기기 때문이다.
		return session.insert("memberMapper.save", member);
	}

	public List<Member> findAll(SqlSession session){
		//List<Member> member = session.selectList("memberMapper.findAll");
		//return member;
		// 조회결과가 존재하지 않는다면 빈 리스트 반환
		return session.selectList("memberMapper.findAll");
	}
	
	public Member findById(SqlSession session, String userId) {
		//Member member =  session.selectOne("memberMapper.findById", userId);
		// 조회결과가 존재하지 않다면 null 반환
		return session.selectOne("memberMapper.findById", userId);
	}
	
	
	public List<Member> findByKeyword(SqlSession session, String keyword){
		
		return session.selectList("memberMapper.findByKeyword", keyword); // 2번 sql 실행할 때 keyword 넘겨주야 함으로
	
	}
	
	public int update(SqlSession session, PasswordDTO pd) {
		
		return session.update("memberMapper.update", pd);
		
	}
	
	public int delete(SqlSession session, Member member) {
		
		return session.delete("memberMapper.delete", member);
		
	}
	
	
}
