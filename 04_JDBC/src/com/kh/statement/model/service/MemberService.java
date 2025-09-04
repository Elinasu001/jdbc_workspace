package com.kh.statement.model.service;

import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.function.Function;

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
	// 서비스단에서는 돌려야 될게 많아서 나중에는 못하지만 일단 jdbc단에서 중복 제거를 이런 식으로 가능하여 진행해 봄
	// 람다 와 함수형 인터페이스를 사용 예정
	// 함수형 : 앞에 타입을 받아 뒤쪽 제네릭을 반환
	// Function<Connection, T>
	// 각각 메소드 호출 외부에서는 호출이 안되게 할 예정
	// 반환 타입이 서로 달라 외부에서 제네릭을 달아 사용할 수 있게 한다.
	// executeQuery / executeUpdate 두 분류 부터 시작~!
	// connection은 template에 해놨으니 삭제!
	private <T> T executeQuery(Function<Connection, T> daoFunction){
		
		Connection conn = null;
		T result = null; // 반환 타입이 각각 다르기 때문에 T로 설정
		conn = getConnection();
		result = daoFunction.apply(conn);
		close(conn);
		return result;
	}
	
	public List<Member> findAll(){
		/*
		List<Member> members = new MemberDao().findAll(conn);
		close(conn);
		return members;
		*/
		return executeQuery(new MemberDao()::findAll);
	}
	
	public Member findById(String userId) {
		/*
		Member member = new MemberDao().findById(conn, userId);
		close(conn);
		return member;
		*/
		return executeQuery(conn -> new MemberDao().findById(conn, userId));
	}
	
	public List<Member> findByKeyword(String keyword){
		/*
		List<Member> members = new MemberDao().findByKeyword(conn, keyword);
		close(conn);
		return members;
		*/
		return executeQuery(conn -> new MemberDao().findByKeyword(conn, keyword));
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
