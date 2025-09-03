package com.work3.statement.model.service;

import static com.work3.common.JDBCTemplate. *;

import java.sql.Connection;

import com.work3.common.JDBCTemplate;
import com.work3.statement.model.dao.ChallengeDao;
import com.work3.statement.model.vo.Challenge;

public class ChallengeService {
	
	private Connection conn = null;
	
	public ChallengeService() {
		this.conn = getConnection();
	}
	
	public int save(Challenge challenge) {
		// Connection 객체 생성
		JDBCTemplate.getConnection();
		
		// DAO 호출 시 Connection 객체 전달 + Controller가 넘겨준 사용자 입력한 값이 필드에 담겨있는 Challenge 참조변수를 함께 넘겨준다.
		int result = new ChallengeDao().save(conn, challenge);
		
		// 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		
		// 자원 반납
		JDBCTemplate.close(conn);
		
		return result;
	}
	
}
